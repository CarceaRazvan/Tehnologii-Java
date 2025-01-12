package com.taip.FillTheVoid.painting.system;

public class Region {
    private Double upperLeftCorner;
    private Double bottomRightCorner;

    public Region(Double upperLeftCorner, Double bottomRightCorner) {
        this.upperLeftCorner = upperLeftCorner;
        this.bottomRightCorner = bottomRightCorner;
    }

    public Double getUpperLeftCorner() {
        return upperLeftCorner;
    }

    public void setUpperLeftCorner(Double upperLeftCorner) {
        this.upperLeftCorner = upperLeftCorner;
    }

    public Double getBottomRightCorner() {
        return bottomRightCorner;
    }

    public void setBottomRightCorner(Double bottomRightCorner) {
        this.bottomRightCorner = bottomRightCorner;
    }

    public Region select(Double upperLeftCorner, Double bottomRightCorner) {
        // TODO
        this.upperLeftCorner = upperLeftCorner;
        this.bottomRightCorner = bottomRightCorner;
        return new Region(upperLeftCorner, bottomRightCorner);
    }

    @Override
    public String toString() {
        return "Region [upperLeftCorner=" + upperLeftCorner + ", bottomRightCorner=" + bottomRightCorner + "]";
    }
}
