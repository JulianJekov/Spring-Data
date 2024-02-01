import entities.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAMain {
    public static void main(String[] args) {
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("school");
//
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        entityManager.getTransaction().begin();
//
//        Student student = new Student("Teo");
//
//        entityManager.persist(student);
//
//        entityManager.getTransaction().commit();

//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("school");
//        EntityManager em = emf.createEntityManager();
//        em.getTransaction().begin();
//        Student student = em.find(Student.class, 1);
//        System.out.println(student.getId() + " " + student.getName());
//        em.getTransaction().commit();

//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("school");
//        EntityManager em = emf.createEntityManager();
//        em.getTransaction().begin();
//        Student student = em.find(Student.class, 1);
//        em.remove(student);
//        em.getTransaction().commit();


        EntityManagerFactory emf = Persistence.createEntityManagerFactory("school");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(new Student("Pesho"));
        em.getTransaction().commit();
    }
}
