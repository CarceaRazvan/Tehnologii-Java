package com.taip.FillTheVoid.painting;

import com.taip.FillTheVoid.user.Owner.Owner;
import com.taip.FillTheVoid.user.User;
import com.taip.FillTheVoid.user.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class PaintingServiceMonitor {

    private final Logger logger = LoggerFactory.getLogger(PaintingServiceMonitor.class);

    private final PaintingRepository paintingRepository;
    private final UserService userService;

    // Define default values
    private static final String DEFAULT_DESCRIPTION = "A visually appealing image showcasing the specified subject " +
            "with clarity and detail.";
    private static final String DEFAULT_AUTHOR = "Unknown";


    public PaintingServiceMonitor(PaintingRepository paintingRepository, UserService userService) {
        this.paintingRepository = paintingRepository;
        this.userService = userService;
    }

    // Pointcut for methods in PaintingService
    @Pointcut("execution(* com.taip.FillTheVoid.painting.PaintingService.*(..))")
    public void paintingServiceMethods() {}

    // Observation mechanism: Log method details
    @Before("execution(* com.taip.FillTheVoid.painting.PaintingService.addPainting(..)) && args(userEmail, galleryName, paintingName, description, author, imageType, image)")
    public void logAddPaintingDetails(String userEmail, String galleryName, String paintingName, String description, String author, String imageType, byte[] image) {
        logger.info("MOP: Apel la metoda addPainting");
        logger.info("MOP: Utilizator: " + userEmail);
        logger.info("MOP: Galerie: " + galleryName);
        logger.info("MOP: Nume pictură: " + paintingName);
        logger.info("MOP: Descriere: " + (description != null ? description : "null"));
        logger.info("MOP: Autor: " + (author != null ? author : "null"));
    }

    // Verification and correction mechanism: Validate and correct
    @Around("execution(* com.taip.FillTheVoid.painting.PaintingService.addPainting(..)) && args(userEmail, galleryName, paintingName, description, author, imageType, image)")
    public Object validateAndCorrect(ProceedingJoinPoint joinPoint, String userEmail, String galleryName, String paintingName, String description, String author, String imageType, byte[] image) throws Throwable {

        if (description == null || description.isBlank()) {
            logger.warn("MOP: Descrierea este null sau goală. Se va folosi valoarea implicită.");
            description = DEFAULT_DESCRIPTION;
        }

        if (author == null || author.isBlank()) {
            logger.warn("MOP: Autorul este null sau gol. Se va folosi valoarea implicită.");
            author = DEFAULT_AUTHOR;
        }

        // Validation unique painting name
        User user = userService.getUserByEmail(userEmail);
        Owner owner = (Owner) user;
        String uniquePaintingName = generateUniqueName(paintingName, owner);

        // Log the unique name
        if (!uniquePaintingName.equals(paintingName)) {
            logger.info("MOP: Numele picturii a fost modificat din " + paintingName + " în " + uniquePaintingName);
        }

        // Proceed with the modified argument
        Object[] args = new Object[]{userEmail, galleryName, uniquePaintingName, description, author, imageType, image};
        return joinPoint.proceed(args);
    }

    @AfterReturning(pointcut = "paintingServiceMethods()", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        logger.info("MOP: Metoda " + joinPoint.getSignature().getName() + " a fost executată.");
        if (result != null) {
            logger.info("MOP: Rezultatul metodei: " + result);
        }
    }

    @AfterThrowing(pointcut = "paintingServiceMethods()", throwing = "exception")
    public void logExceptions(JoinPoint joinPoint, Throwable exception) {
        logger.error("MOP: O excepție a fost aruncată în metoda " + joinPoint.getSignature().getName());
        logger.error("MOP: Detalii excepție: ", exception);
    }

    private String generateUniqueName(String baseName, Owner owner) {
        String uniqueName = baseName;
        int counter = 1;

        while (paintingRepository.findByNameAndOwnerWithLock(uniqueName, owner).isPresent()) {
            uniqueName = baseName + "(" + counter + ")";
            counter++;
        }

        return uniqueName;
    }

}


