import javax.persistence.EntityManager;
import java.util.Scanner;

public class P03_ContainsEmployee {
    public static void main(String[] args) {
        final EntityManager entityManager = Utils.createEntityManager();
        entityManager.getTransaction().begin();

        final String[] fullName = new Scanner(System.in).nextLine().split(" ");
        final String firstName = fullName[0];
        final String lastName = fullName[1];

        final Long countEmployee = entityManager.createQuery(
                        "select count(e) from Employee as e where e.firstName = :first_name and e.lastName = :last_name",
                        Long.class)
                .setParameter("first_name", firstName)
                .setParameter("last_name", lastName)
                .getSingleResult();

        System.out.println(countEmployee > 0 ? "Yes" : "No");

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
