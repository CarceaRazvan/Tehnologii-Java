package com.example.lab3.compulsory.view;

import com.example.lab3.compulsory.model.Product;
import com.example.lab3.compulsory.service.ProductService;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.view.ViewScoped;

import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "productView")
@ViewScoped
public class ProductView implements Serializable {

    private ProductService productService = new ProductService();
    private List<Product> products;

    public List<Product> getProducts() {
        if (products == null) {
            products = productService.getAllProducts();
        }
        return products;
    }

    public String viewProducts() {
        return "viewProducts"; // outcome to navigate to users.xhtml
    }

}
