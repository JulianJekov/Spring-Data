import entities.Address;
import entities.Town;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class P13_RemoveTowns {
    public static void main(String[] args) {

        final EntityManager entityManager = Utils.createEntityManager();
        entityManager.getTransaction().begin();

        final String townName = new Scanner(System.in).nextLine();

        final Town townToDelete = entityManager.createQuery(
                        "select t from Town as t where t.name = :town_name", Town.class)
                .setParameter("town_name", townName)
                .getSingleResult();

        final List<Address> addresses = entityManager.createQuery("select a from Address as a where a.town.id = :town_id", Address.class)
                .setParameter("town_id", townToDelete.getId()).getResultList();

        addresses.forEach(a -> a.getEmployees()
                .forEach(e -> e.setAddress(null)));

        addresses.forEach(entityManager::remove);
        entityManager.remove(townToDelete);

        final int countDeletedAddresses = addresses.size();

        System.out.printf("%d address%s in %s deleted",
                countDeletedAddresses,
                countDeletedAddresses == 1 ? "" : "es",
                townName);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
