package com.taip.FillTheVoid.painting;

public interface PaintingProjection {
    String getPaintingName();
    String getDescription();
    String getAuthor();
    String getImageType();
    byte[] getImage();
}