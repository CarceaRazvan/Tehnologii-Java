package model;

import com.example.lab3.lab5compulsory.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class ProductTest {

    @Test
    public void testJPA() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ExamplePU");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Product product = new Product();
        product.setName("Bar");
        em.persist(product);

        product = (Product)em.createQuery(
                        "select e from Product e where e.name='Bar'")
                .getSingleResult();
        product.setName("Baz");
        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}