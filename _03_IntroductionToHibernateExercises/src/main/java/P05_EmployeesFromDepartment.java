import entities.Employee;

import javax.persistence.EntityManager;
import java.util.List;

public class P05_EmployeesFromDepartment {
    public static void main(String[] args) {
        final EntityManager entityManager = Utils.createEntityManager();
        entityManager.getTransaction().begin();

        final List<Employee> resultList = entityManager.createQuery(
                "select e from Employee as e" +
                        " where e.department.name = 'Research and Development'" +
                        " order by e.salary, e.id", Employee.class).getResultList();

        for (Employee e : resultList) {
            System.out.printf("%s %s from %s - $%.2f%n",
                    e.getFirstName(),
                    e.getLastName(),
                    e.getDepartment().getName(),
                    e.getSalary());
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
