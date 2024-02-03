import entities.Town;

import javax.persistence.EntityManager;
import java.util.List;

public class P02_ChangeCasing {
    public static void main(String[] args) {
        final EntityManager entityManager = Utils.createEntityManager();
        entityManager.getTransaction().begin();

        final List<Town> resultList = entityManager.createQuery("select t from Town t", Town.class).getResultList();

        for (Town town : resultList) {
            final String townName = town.getName();
            if(townName.length() <= 5) {
                town.setName(townName.toUpperCase());
            }
            entityManager.persist(town);
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
