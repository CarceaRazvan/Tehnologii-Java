package com.taip.FillTheVoid.restoration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestoredImage {

    private String imageType;
    private byte[] image;
}
