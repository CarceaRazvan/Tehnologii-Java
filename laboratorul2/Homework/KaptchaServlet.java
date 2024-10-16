package org.example.laboratorul2.homework;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;

@WebServlet("/captcha")
public class KaptchaServlet extends HttpServlet {
    private Producer captchaProducer;

    @Override
    public void init() throws ServletException {
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "no");
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        properties.setProperty("kaptcha.textproducer.char.space", "5");

        Config config = new Config(properties);
        captchaProducer = config.getProducerImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {
        String captchaText = captchaProducer.createText();
        request.getSession().setAttribute("captcha", captchaText);

        BufferedImage captchaImage = captchaProducer.createImage(captchaText);

        response.setContentType("image/png");
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setHeader("Pragma", "no-cache");

        ImageIO.write(captchaImage, "png", response.getOutputStream());
    }
}