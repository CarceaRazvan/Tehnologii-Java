package com.taip.FillTheVoid.restoration;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Service
@AllArgsConstructor
public class RestorationService {

    public RestoredImage getRestoredImage(String selectedModel, CornersList cornersList, String imageType, byte[] image) {

        RestoredImage newRestoredImage = restorePythonCode(selectedModel, cornersList, imageType, image);

        return newRestoredImage;
    }

    public RestoredImage restorePythonCode(String selectedModel, CornersList cornersList, String imageType, byte[] image) {

        System.out.println("Here use pythonic code to restore and obtain new restoredImage");
        System.out.println(cornersList);

        saveAsPng(image);

//        run scipt

        List<String> allLinesRestoration = runScriptRestoration(selectedModel, cornersList);
        System.out.println(allLinesRestoration);



        // TODO restoredImage should be changed (in this example is returned same image)
        byte[] restoredImage = readImage("src/main/java/com/taip/FillTheVoid/restoration/restored_image.png");


        RestoredImage newRestoredImage = new RestoredImage(imageType, restoredImage);


        return newRestoredImage;
    }

    public void saveAsPng(byte[] image) {
        BufferedImage bufferedImage = null;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(image);
            bufferedImage = ImageIO.read(byteArrayInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Specify the output file path where you want to save the PNG
        String outputFilePath = "src/main/java/com/taip/FillTheVoid/restoration/restored_image.png";

        // Save the BufferedImage to a file as PNG
        try {
            if (bufferedImage != null) {
                File outputFile = new File(outputFilePath);
                ImageIO.write(bufferedImage, "PNG", outputFile);
                System.out.println("Image saved as PNG at: " + outputFilePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] readImage(String filePath) {
        Path path = Paths.get(filePath);
        byte[] imageBytes = null;
        try {
            imageBytes = Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imageBytes;
    }

    public List<String> runScriptRestoration(String selectedModel, CornersList cornersList) {

        String pythonExecutable;

        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            pythonExecutable = "myenv/Scripts/python.exe";
        } else {
            pythonExecutable = "myenv/bin/python";
        }

        String pythonScriptPath = "src/main/java/com/taip/FillTheVoid/restoration/restoration.py";

        String jsonCorners = convertCornersToJson(cornersList);

        System.out.println(jsonCorners);

        String[] command = new String[]{pythonExecutable, pythonScriptPath, selectedModel, jsonCorners};

        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            Process process = pb.start();

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String s = null;
            List<String> allLines = new ArrayList<>();

            while ((s = stdInput.readLine()) != null) {
                allLines.add(s);

            }

            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }

            return allLines;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static String convertCornersToJson(CornersList cornersList) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(cornersList);

            if (System.getProperty("os.name").toLowerCase().contains("win")) {

                jsonString = jsonString.replace("\"", "\\\"");
                return "\"" + jsonString + "\"";
            } else {
                return objectMapper.writeValueAsString(cornersList);
            }


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
