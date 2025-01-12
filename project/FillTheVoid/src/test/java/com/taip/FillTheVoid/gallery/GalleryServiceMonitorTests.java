package com.taip.FillTheVoid.gallery;

import com.taip.FillTheVoid.user.Owner.Owner;
import com.taip.FillTheVoid.user.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Test;
import com.taip.FillTheVoid.user.UserService;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

@Aspect
public class GalleryServiceMonitorTests {


    @Mock
    private GalleryRepository galleryRepository;

    @Mock
    private UserService userService;

    @Mock
    private Logger logger;

    @InjectMocks
    private GalleryServiceMonitor galleryServiceMonitor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(galleryServiceMonitor, "logger", logger);
    }

    @Test
    void testLogAddGalleryDetails() {
        // Call the method manually
        galleryServiceMonitor.logAddGalleryDetails("test@example.com", "Gallery Name", "Test Description");

        // Verify logger was invoked
        verify(logger).info("MOP: Apel la metoda addGallery");
        verify(logger).info("MOP: Utilizator: test@example.com");
        verify(logger).info("MOP: Galerie: Gallery Name");
        verify(logger).info("MOP: Descriere: Test Description");
    }

    @Test
    void testValidateAndCorrectGallery() throws Throwable {
        // Mock dependencies
        User mockUser = mock(Owner.class);
        when(userService.getUserByEmail("test@example.com")).thenReturn(mockUser);

        // Simulate a non-unique gallery name
        when(galleryRepository.findByNameAndOwner("Gallery Name", (Owner) mockUser)).thenReturn(java.util.Optional.of(new Gallery()));
        when(galleryRepository.findByNameAndOwner("Gallery Name(1)", (Owner) mockUser)).thenReturn(java.util.Optional.empty());

        // Simulate the joinPoint behavior
        ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        when(joinPoint.proceed(any())).thenReturn("Mocked Proceed Result");

        // Call the method
        Object result = galleryServiceMonitor.validateAndCorrectGallery(joinPoint, "test@example.com", "Gallery Name", "");

        // Assertions and verifications
        verify(logger).warn("MOP: Descrierea gallery este null sau goală. Se va folosi valoarea implicită.");
        verify(logger).info("MOP: Numele galeriei a fost modificat din Gallery Name în Gallery Name(1)");
        verify(joinPoint).proceed(any());
    }

    @Test
    void testLogAfterMethod() {
        // Mock JoinPoint and Signature
        JoinPoint mockJoinPoint = mock(JoinPoint.class);
        org.aspectj.lang.Signature mockSignature = mock(org.aspectj.lang.Signature.class);

        // Define behavior for mocks
        when(mockJoinPoint.getSignature()).thenReturn(mockSignature);
        when(mockSignature.getName()).thenReturn("testMethod");

        // Call the method
        galleryServiceMonitor.logAfterMethod(mockJoinPoint, "Test Result");

        // Verify logger was invoked with correct messages
        verify(logger).info("MOP: Metoda testMethod a fost executată.");
        verify(logger).info("MOP: Rezultatul metodei: Test Result");
    }

    @Test
    void testLogExceptions() {
        // Mock JoinPoint and Signature
        JoinPoint mockJoinPoint = mock(JoinPoint.class);
        org.aspectj.lang.Signature mockSignature = mock(org.aspectj.lang.Signature.class);

        // Define behavior for mocks
        when(mockJoinPoint.getSignature()).thenReturn(mockSignature);
        when(mockSignature.getName()).thenReturn("testMethod");

        // Create a mock exception
        Exception mockException = new RuntimeException("Test exception");

        // Call the method
        galleryServiceMonitor.logExceptions(mockJoinPoint, mockException);

        // Verify logger was invoked with correct messages
        verify(logger).error("MOP: O excepție a fost aruncată în metoda testMethod");
        verify(logger).error("MOP: Detalii excepție: ", mockException);
    }

    @Test
    void testGenerateUniqueName() {
        // Mock dependencies
        Owner mockOwner = mock(Owner.class);
        when(galleryRepository.findByNameAndOwner("Gallery Name", mockOwner)).thenReturn(java.util.Optional.of(new Gallery()));
        when(galleryRepository.findByNameAndOwner("Gallery Name(1)", mockOwner)).thenReturn(java.util.Optional.empty());

        // Call the method
        String uniqueName = ReflectionTestUtils.invokeMethod(
                galleryServiceMonitor,
                "generateUniqueName",
                "Gallery Name",
                mockOwner
        );

        // Verify the result
        assert uniqueName.equals("Gallery Name(1)");
    }

}
