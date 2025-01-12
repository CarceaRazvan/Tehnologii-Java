package com.taip.FillTheVoid.painting.iterator;

import com.taip.FillTheVoid.painting.Painting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ByDateIterator<T> implements Iterator<T>{

    private final List<Painting> paintings;
    private int currentPosition = 0;

    public ByDateIterator(List<Painting> paintings) {

        this.paintings = new ArrayList<>(paintings);
        Collections.sort(this.paintings, Comparator.comparing(Painting::getCreatedAt));
    }

    @Override
    public boolean hasNext() {
        return currentPosition < paintings.size();
    }

    @Override
    public Painting getNext() {
        if (hasNext()) {
            return paintings.get(currentPosition++);
        }
        return null;
    }

    @Override
    public void reset() {
        currentPosition = 0;
    }
}
