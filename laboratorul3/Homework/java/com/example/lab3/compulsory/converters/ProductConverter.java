package com.example.lab3.compulsory.converters;

import com.example.lab3.compulsory.model.Product;
import com.example.lab3.compulsory.service.ProductService;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(forClass = Product.class)
public class ProductConverter implements Converter<Product> {

    @Override
    public Product getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        Long id = Long.valueOf(value);
        ProductService productService = new ProductService();
        return productService.getProductById(id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Product product) {
        if (product == null || product.getId() == null) {
            return "";
        }
        return product.getId().toString();
    }
}