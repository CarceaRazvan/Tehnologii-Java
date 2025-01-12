package com.taip.FillTheVoid.config;
import com.taip.FillTheVoid.config.proxy.JwtService;
import com.taip.FillTheVoid.config.proxy.JwtServiceProxy;
import com.taip.FillTheVoid.config.proxy.RealJwtService;

public class AuthJwtServiceFactory extends JwtServiceFactory{

    RealJwtService realJwtService = new RealJwtService();

    @Override
    public JwtService createJwtService() {

        return new JwtServiceProxy(realJwtService);
    }
}
