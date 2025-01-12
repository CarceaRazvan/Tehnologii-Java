package com.taip.FillTheVoid.painting.iterator;

import com.taip.FillTheVoid.painting.Painting;

import java.util.ArrayList;
import java.util.List;

public class ConcreteCollection implements IterableCollection<Painting> {
    private final List<Painting> paintings;

    public ConcreteCollection() {
        this.paintings = new ArrayList<>();
    }

    public void addPainting(Painting painting) {
        paintings.add(painting);
    }

    public void removePainting(Painting painting) {
        paintings.remove(painting);
    }

    @Override
    public Iterator<Painting> createIterator() {
        return new ByDateIterator(paintings);
    }
}