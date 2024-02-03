import entities.Employee;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class P11_FindEmployeesByFirstName {
    public static void main(String[] args) {
        final EntityManager entityManager = Utils.createEntityManager();
        entityManager.getTransaction().begin();

        final String pattern = new Scanner(System.in).nextLine();
        entityManager.createQuery(
                        "select e from Employee as e where e.firstName like concat(:pattern, '%') order by e.lastName", Employee.class)
                .setParameter("pattern", pattern)
                .getResultList()
                .forEach(e -> System.out.printf("%s %s - %s - ($%.2f)%n",
                        e.getFirstName(),
                        e.getLastName(),
                        e.getJobTitle(),
                        e.getSalary()));

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
