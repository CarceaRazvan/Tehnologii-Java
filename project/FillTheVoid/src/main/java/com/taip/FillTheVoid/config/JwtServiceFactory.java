package com.taip.FillTheVoid.config;

import com.taip.FillTheVoid.config.proxy.JwtService;

public abstract class JwtServiceFactory {

    public JwtService instantiateJwtService() {

        JwtService jwtService = createJwtService();
        return jwtService;
    }

    public abstract JwtService createJwtService();
}
