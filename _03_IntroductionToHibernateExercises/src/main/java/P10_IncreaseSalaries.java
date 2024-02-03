import entities.Employee;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class P10_IncreaseSalaries {
    public static void main(String[] args) {
        final EntityManager entityManager = Utils.createEntityManager();

        entityManager.getTransaction().begin();

        final List<Employee> resultList = entityManager.createQuery(
                "select e from Employee as e where e.department.name in ('Engineering', 'Tool Design', 'Marketing', 'Information Services')"
                , Employee.class).getResultList();

        for (Employee employee : resultList) {
            BigDecimal salary = employee.getSalary();
            employee.setSalary(salary.multiply(BigDecimal.valueOf(1.12)));
        }

        resultList.forEach(e -> {
            System.out.printf("%s %s ($%.2f)%n",
                    e.getFirstName(),
                    e.getLastName(),
                    e.getSalary());
        });


        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
