import entities.Town;

import javax.persistence.EntityManager;
import java.util.List;

public class P12_EmployeesMaximumSalaries {
    public static void main(String[] args) {

        final EntityManager entityManager = Utils.createEntityManager();
        entityManager.getTransaction().begin();

        final List<Object[]> resultList = entityManager.createQuery(
                        "select department.name, max(salary) from Employee e group by department.name" +
                                " having max(salary) not  between 30000 and 70000", Object[].class)
                .getResultList();

        for (Object[] object : resultList) {
            System.out.println(object[0] + " " + object[1]);
        }


        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
