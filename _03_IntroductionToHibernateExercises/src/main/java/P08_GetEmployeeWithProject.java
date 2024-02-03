import entities.Employee;
import entities.Project;

import javax.persistence.EntityManager;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class P08_GetEmployeeWithProject {
    public static void main(String[] args) {

        final EntityManager entityManager = Utils.createEntityManager();
        entityManager.getTransaction().begin();

        final int employeeId = new Scanner(System.in).nextInt();

        final Employee employee = entityManager.createQuery(
                        "select e from Employee as e where e.id = :employee_id", Employee.class)
                .setParameter("employee_id", employeeId)
                .getSingleResult();

        final String sortedProjects = employee.getProjects()
                .stream()
                .sorted(Comparator.comparing(Project::getName))
                .map(Project::getName)
                .collect(Collectors.joining("\n\t"));

        System.out.printf("%s %s - %s%n\t%s",
                employee.getFirstName(),
                employee.getLastName(),
                employee.getJobTitle(),
                sortedProjects);

        entityManager.getTransaction().commit();
        entityManager.close();

    }

}

