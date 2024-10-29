package com.example.lab3.compulsory.edit;

import com.example.lab3.compulsory.model.Product;
import com.example.lab3.compulsory.model.User;
import com.example.lab3.compulsory.service.ProductLogsService;
import com.example.lab3.compulsory.service.ProductService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Objects;

@ManagedBean
@ViewScoped
public class DataEdit implements Serializable {

    private Product dialogProduct;
    private ProductService productService = new ProductService();
    private ProductLogsService productLogsService = new ProductLogsService();

    private String lastModifiedUser;

    @PostConstruct
    public void init() {
        dialogProduct = new Product();
    }
    public Product getDialogProduct() {
        return dialogProduct;
    }

    public void setDialogProduct(Product dialogProduct) {
        this.dialogProduct = dialogProduct;
    }

    public void openDialog() {
        dialogProduct = new Product();
    }

    // Metoda de salvare
    public void saveProduct() {

        Product product = productService.getProductById(dialogProduct.getId());

        if (product != null) {
            productService.updateProduct(dialogProduct);


            try {
                this.lastModifiedUser = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                this.lastModifiedUser = "Unknown Host";
            }

            String addName;
            String addDescription;
            String addCategory;

            if (!Objects.equals(dialogProduct.getName(), "")) {
                addName = ", Name: " + product.getName() + " ----> " + dialogProduct.getName();
            }
            else addName = "";

            if (!Objects.equals(dialogProduct.getDescription(), "")) {
                addDescription = ", Description: " + product.getName() + " ----> " + dialogProduct.getDescription();
            }
            else addDescription = "";

            if (!Objects.equals(dialogProduct.getCategory(), "")) {
                addCategory = ", Category: " + product.getCategory() + " ----> " + dialogProduct.getCategory();
            }
            else addCategory = "";


            productLogsService.insertLog(lastModifiedUser, "Id product: " + product.getId()+ addName + addDescription + addCategory);

            lastModifiedUser = productLogsService.getLastModification();

        }

    }

    public void onClose() {
        System.out.println("Dialog closed"  + dialogProduct.getName());
    }

    public String getLastModifiedUser() {
        try {
            return productLogsService.getLastModification();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error retrieving last modified user.";
        }
    }

}
