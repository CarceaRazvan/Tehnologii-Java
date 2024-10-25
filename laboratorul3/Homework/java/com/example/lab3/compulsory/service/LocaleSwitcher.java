package com.example.lab3.compulsory.service;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.context.FacesContext;

import java.io.Serializable;
import java.util.Locale;

@ManagedBean(name = "localeSwitcher")
@SessionScoped
public class LocaleSwitcher implements Serializable {

    private static final long serialVersionUID = 1L;

    // Method to change the locale
    public void changeLocale(String locale) {
        FacesContext context = FacesContext.getCurrentInstance();
        Locale newLocale = new Locale(locale);
        context.getViewRoot().setLocale(newLocale);
    }

    // Getter for currentLocale
    public String getCurrentLocale() {
        return FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
    }

    // Set the current locale 
    public void setCurrentLocale(String currentLocale) {
        changeLocale(currentLocale);
    }
}
