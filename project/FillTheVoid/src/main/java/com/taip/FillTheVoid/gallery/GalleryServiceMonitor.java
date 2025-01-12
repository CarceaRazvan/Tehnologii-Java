package com.taip.FillTheVoid.gallery;

import com.taip.FillTheVoid.user.Owner.Owner;
import com.taip.FillTheVoid.user.User;
import com.taip.FillTheVoid.user.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GalleryServiceMonitor {


    private final Logger logger = LoggerFactory.getLogger(GalleryServiceMonitor.class);

    private final GalleryRepository galleryRepository;
    private final UserService userService;

    private static final String DEFAULT_DESCRIPTION = "A captivating collection of paintings reflecting the artist's vision.";

    public GalleryServiceMonitor(GalleryRepository galleryRepository, UserService userService) {
        this.galleryRepository = galleryRepository;
        this.userService = userService;
    }

    // Pointcut for methods in GalleryService
    @Pointcut("execution(* com.taip.FillTheVoid.gallery.GalleryService.*(..))")
    public void galleryServiceMethods() {}

    @Before("execution(* com.taip.FillTheVoid.gallery.GalleryService.addGallery(..)) && args(userEmail, galleryName, description)")
    public void logAddGalleryDetails(String userEmail, String galleryName, String description) {
        logger.info("MOP: Apel la metoda addGallery");
        logger.info("MOP: Utilizator: " + userEmail);
        logger.info("MOP: Galerie: " + galleryName);
        logger.info("MOP: Descriere: " + (description != null ? description : "null"));
    }


    @Around("execution(* com.taip.FillTheVoid.gallery.GalleryService.addGallery(..)) && args(userEmail, galleryName, description)")
    public Object validateAndCorrectGallery(ProceedingJoinPoint joinPoint, String userEmail, String galleryName, String description) throws Throwable {


        if (description == null || description.isBlank()) {
            logger.warn("MOP: Descrierea gallery este null sau goală. Se va folosi valoarea implicită.");
            description = DEFAULT_DESCRIPTION;
        }

        User user = userService.getUserByEmail(userEmail);
        Owner owner = (Owner) user;

        String uniqueGalleryName = generateUniqueName(galleryName, owner);

        if (!uniqueGalleryName.equals(galleryName)) {
            logger.info("MOP: Numele galeriei a fost modificat din " + galleryName + " în " + uniqueGalleryName);
        }

        Object[] args = new Object[]{userEmail, uniqueGalleryName, description};

        return joinPoint.proceed(args);


    }

    @AfterReturning(pointcut = "galleryServiceMethods()", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        logger.info("MOP: Metoda " + joinPoint.getSignature().getName() + " a fost executată.");
        if (result != null) {
            logger.info("MOP: Rezultatul metodei: " + result);
        }
    }

    @AfterThrowing(pointcut = "galleryServiceMethods()", throwing = "exception")
    public void logExceptions(JoinPoint joinPoint, Throwable exception) {
        logger.error("MOP: O excepție a fost aruncată în metoda " + joinPoint.getSignature().getName());
        logger.error("MOP: Detalii excepție: ", exception);
    }

    private String generateUniqueName(String baseName, Owner owner) {
        String uniqueName = baseName;
        int counter = 1;

        while (galleryRepository.findByNameAndOwnerWithLock(uniqueName, owner).isPresent()) {
            uniqueName = baseName + "(" + counter + ")";
            counter++;
        }

        return uniqueName;
    }


}
