package com.taip.FillTheVoid.painting.system;

import java.util.Arrays;

public class RestorationSystem {
    private byte[] image;

    public RestorationSystem(byte[] image) {
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void restore() {
        if (image != null && image.length > 0) {
            System.out.println("Restoring the image...");
        } else {
            System.out.println("No image to restore.");
        }
    }

    @Override
    public String toString() {
        return "RestorationSystem [image=" + Arrays.toString(image) + "]";
    }
}