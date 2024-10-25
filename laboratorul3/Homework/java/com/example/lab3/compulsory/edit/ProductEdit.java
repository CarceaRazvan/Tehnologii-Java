package com.example.lab3.compulsory.edit;

import com.example.lab3.compulsory.model.Product;
import com.example.lab3.compulsory.service.ProductService;
import com.example.lab3.compulsory.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.ViewScoped;
import org.primefaces.model.DualListModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ManagedBean
@ViewScoped
public class ProductEdit implements Serializable {

    private ProductService productService = new ProductService();
    private UserService userService = new UserService();
    private List<Product> products; // All products from the service
    private List<Product> productsUser; // All products from the service
    private DualListModel<Product> dualListModel; // For the pick list
    private Long userId = null;

    @PostConstruct
    public void init() {
        List<Product> sourceProducts = new ArrayList<>();
        List<Product> targetProducts = getProducts(); // or however you want to initialize it
        dualListModel = new DualListModel<>(targetProducts, sourceProducts);
    }

    public ProductEdit() {
        products = productService.getAllProducts();
        dualListModel = new DualListModel<>(new ArrayList<>(), new ArrayList<>());
    }


    public List<Product> getProducts() {
        if (products == null) {
            products = productService.getAllProducts();
        }
        return products;
    }

    public DualListModel<Product> getDualListModel() {
        return dualListModel;
    }

    public void setDualListModel(DualListModel<Product> dualListModel) {
        this.dualListModel = dualListModel; // Ensure you set the model correctly
    }

    // Method to save selected products
    public void saveSelectedProducts() {

        System.out.println("SAVE");
        System.out.println(dualListModel.getTarget().size());

        userService.updateProductsUser(userId, dualListModel.getTarget());

    }

    public void openDialogProducts(Long id) {

        userId = id;

        System.out.println("DIalog product" + id);

        productsUser = userService.getProductsByUser(id);

        if (productsUser == null) {
            productsUser = new ArrayList<>();
        }

        List<Product> allProducts = productService.getAllProducts();
        allProducts.removeAll(productsUser);


        for (Product product : productsUser) {

            allProducts.removeIf(productAll -> Objects.equals(product.getName(), productAll.getName()));

        }


        setDualListModel(new DualListModel<>(allProducts, productsUser));
    }


    public List<Product> getProductsUser() {
        return productsUser;
    }

    public void setProductsUser(List<Product> productsUser) {
        this.productsUser = productsUser;
    }
}
