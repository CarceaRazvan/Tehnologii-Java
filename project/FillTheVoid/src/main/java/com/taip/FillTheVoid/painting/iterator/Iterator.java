package com.taip.FillTheVoid.painting.iterator;

import com.taip.FillTheVoid.painting.Painting;

public interface Iterator <T> {

    boolean hasNext();
    Painting getNext();
    void reset();
}
