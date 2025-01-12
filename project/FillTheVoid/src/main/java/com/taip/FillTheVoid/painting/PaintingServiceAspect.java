package com.taip.FillTheVoid.painting;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//@Aspect
//@Component
public class PaintingServiceAspect {

    private final Logger logger = LoggerFactory.getLogger(PaintingServiceAspect.class);

    @Pointcut("execution(* com.taip.FillTheVoid.painting.PaintingService.*(..))")
    public void paintingServiceMethods() {}

    @Before("execution(* com.taip.FillTheVoid.painting.PaintingService.addPainting(..)) && args(userEmail, galleryName, paintingName, description, author, imageType, image)")
    public void logAddPaintingDetails(String userEmail, String galleryName, String paintingName, String description, String author, String imageType, byte[] image) {
        logger.info("AOP: Apel la metoda addPainting");
        logger.info("AOP: Utilizator: " + userEmail);
        logger.info("AOP: Galerie: " + galleryName);
        logger.info("AOP: Nume pictură: " + paintingName);
        logger.info("AOP: Descriere: " + description);
        logger.info("AOP: Autor: " + author);
        logger.info("AOP: Tip imagine: " + imageType);
        if (image != null) {
            logger.info("AOP: Mărime imagine: " + image.length + " bytes");
        } else {
            logger.warn("AOP: Imaginea este null");
        }
    }

    @AfterReturning(pointcut = "paintingServiceMethods()", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        logger.info("AOP: Metoda " + joinPoint.getSignature().getName() + " a fost executată.");
        if (result != null) {
            logger.info("AOP: Rezultatul metodei: " + result);
        }
    }

    @AfterThrowing(pointcut = "paintingServiceMethods()", throwing = "exception")
    public void logExceptions(JoinPoint joinPoint, Throwable exception) {
        logger.error("AOP: O excepție a fost aruncată în metoda " + joinPoint.getSignature().getName());
        logger.error("AOP: Detalii excepție: ", exception);
    }
}