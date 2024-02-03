import javax.persistence.EntityManager;
import java.util.List;

public class P04_EmployeesWithSalaryOver50000 {
    public static void main(String[] args) {
        final EntityManager entityManager = Utils.createEntityManager();
        entityManager.getTransaction().begin();

        final List<String> resultList = entityManager.createQuery(
                "select e.firstName from Employee as e where e.salary > 50000", String.class).getResultList();

        resultList.forEach(System.out::println);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
