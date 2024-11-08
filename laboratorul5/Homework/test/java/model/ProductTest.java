package model;

import com.example.lab3.lab5compulsory.model.Product;
import com.example.lab3.lab5compulsory.repository.ProductDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;

public class ProductTest {

    private static EntityManagerFactory emf;

    @BeforeAll
    public static void setUp() {
        emf = Persistence.createEntityManagerFactory("ExamplePU");
    }

    @BeforeEach
    public void initTestData() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Product").executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    @AfterAll
    public static void tearDown() {
        if (emf != null) {
            emf.close();
        }
    }

    @Test
    public void testCreateProduct() {
        Product product = new Product();
        product.setName("Test Product");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(product);
        em.getTransaction().commit();

        Assertions.assertNotNull(product.getId(), "Product ID should be generated after persisting.");
        em.close();
    }

    @Test
    public void testFindAllProducts() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Product product1 = new Product();
        product1.setName("Product 1");
        em.persist(product1);

        Product product2 = new Product();
        product2.setName("Product 2");
        em.persist(product2);

        em.getTransaction().commit();

        List<Product> products = em.createQuery("SELECT p FROM Product p", Product.class).getResultList();

        Assertions.assertNotNull(products, "Product list should not be null.");
        Assertions.assertEquals(2, products.size(), "There should be exactly 2 products in the list.");
        em.close();
    }

    @Test
    public void testGetProductById() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Product product = new Product();
        product.setName("Sample Product");
        em.persist(product);

        em.getTransaction().commit();

        Product retrievedProduct = em.find(Product.class, product.getId());

        Assertions.assertNotNull(retrievedProduct, "Product should be retrieved successfully.");
        Assertions.assertEquals(product.getName(), retrievedProduct.getName(), "Product name should match.");
        em.close();
    }

    @Test
    public void testDeleteProduct() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Product product = new Product();
        product.setName("Product to Delete");
        em.persist(product);

        em.getTransaction().commit();

        em.getTransaction().begin();
        Product productToDelete = em.find(Product.class, product.getId());
        if (productToDelete != null) {
            em.remove(productToDelete);
        }
        em.getTransaction().commit();

        Product deletedProduct = em.find(Product.class, product.getId());

        Assertions.assertNull(deletedProduct, "Product should be null after deletion.");
        em.close();
    }
}