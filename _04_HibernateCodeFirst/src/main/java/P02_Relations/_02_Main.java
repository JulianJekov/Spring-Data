package P02_Relations;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class _02_Main {
    public static void main(String[] args) {
        EntityManager entityManager = Persistence.createEntityManagerFactory("relations").createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
