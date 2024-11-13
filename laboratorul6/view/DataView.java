package com.example.lab6.view;

import com.example.lab6.repository.DataRepository;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

public abstract class DataView<T, ID extends Serializable> implements Serializable {

    // Injected generic repository
    @Inject
    protected DataRepository<T, ID> repository;

    // Getters and Setters for items and selectedItem
    // List of entities for display in the view
    @Getter
    protected List<T> items;

    // Currently selected item for view or edit
    @Setter
    @Getter
    protected T selectedItem;

    @PostConstruct
    public void init() {
        loadItems();
    }

    public void loadItems() {
        items = repository.findAll();
    }

    public void saveSelectedItem() {
        if (selectedItem != null) {
            repository.persist(selectedItem);
            loadItems();  // Refresh items after saving
        }
    }

    public void deleteItem(ID id) {
        T item = repository.findById(id);
        if (item != null) {
            repository.remove(item);
            loadItems();  // Refresh items after deletion
        }
    }

    public T findItemById(ID id) {
        return repository.findById(id);
    }

}