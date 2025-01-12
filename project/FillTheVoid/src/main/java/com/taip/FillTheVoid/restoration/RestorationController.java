package com.taip.FillTheVoid.restoration;


import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/restoration")
@RequiredArgsConstructor
public class RestorationController {

    private final RestorationService restorationService;


    @PostMapping(path = "/restore", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @RequestBody(content = @Content(encoding = @Encoding(name = "corners", contentType = "application/json")))

    public ResponseEntity<Resource> restorePainting(
            @RequestParam("selectedModel") String selectedModel,
            @RequestPart("corners") CornersList cornersList,
            @RequestPart("image") MultipartFile image) throws Exception {

        RestoredImage restoredImage = restorationService.getRestoredImage(selectedModel, cornersList, image.getContentType(), image.getBytes());

        var imageBytes = new ByteArrayResource(restoredImage.getImage());

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, restoredImage.getImageType())
                .cacheControl(CacheControl.noStore().cachePrivate().mustRevalidate())
                .body(imageBytes);
    }

}
