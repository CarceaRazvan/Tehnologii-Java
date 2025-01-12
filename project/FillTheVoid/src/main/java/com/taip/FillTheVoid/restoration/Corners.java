package com.taip.FillTheVoid.restoration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Corners {

    private Coordinate leftUp;
    private Coordinate rightUp;
    private Coordinate leftDown;
    private Coordinate rightDown;
}