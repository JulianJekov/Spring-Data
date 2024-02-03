import entities.Address;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class P06_AddingNewAddressAndUpdatingEmployee {

    public static void main(String[] args) {

        final EntityManager entityManager = Utils.createEntityManager();
        entityManager.getTransaction().begin();

        final Address newAddress = new Address();
        newAddress.setText("Vitoshka 15");

        entityManager.persist(newAddress);

        final String employeeLastName = new Scanner(System.in).nextLine();
        entityManager.createQuery(
                        "update Employee e set e.address = :new_address where e.lastName = :last_name")
                .setParameter("new_address", newAddress)
                .setParameter("last_name", employeeLastName)
                .executeUpdate();

        entityManager.getTransaction().commit();
        entityManager.close();

    }
}
