import entities.Project;

import javax.persistence.EntityManager;
import java.util.List;

public class P09_FindLatest10Projects {
    public static void main(String[] args) {

        final EntityManager entityManager = Utils.createEntityManager();

        entityManager.getTransaction().begin();

        final List<Project> resultList = entityManager.createQuery(
                        "select p from Project as p order by p.startDate desc, p.name", Project.class)
                .setMaxResults(10).getResultList();

        for (Project project : resultList) {
            System.out.printf("Project name: %s%n\t" +
                    "Project Description: %s%n\t" +
                    "Project Start Date:%s%n\t" +
                    "Project End Date: %s%n",
                    project.getName(),
                    project.getDescription(),
                    project.getStartDate(),
                    project.getEndDate());
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}

