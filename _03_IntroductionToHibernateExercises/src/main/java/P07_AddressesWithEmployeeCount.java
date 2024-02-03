import entities.Address;

import javax.persistence.EntityManager;

public class P07_AddressesWithEmployeeCount {
    public static void main(String[] args) {

        final EntityManager entityManager = Utils.createEntityManager();

        entityManager.getTransaction().begin();

        entityManager.createQuery(
                        "select a from Address as a order by a.employees.size desc", Address.class)
                .setMaxResults(10).getResultList().forEach(address -> {
                    System.out.printf("%s, %s - %d employees%n",
                            address.getText(), address.getTown().getName(), address.getEmployees().size());
                });

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}

