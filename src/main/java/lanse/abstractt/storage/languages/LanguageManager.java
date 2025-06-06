package lanse.abstractt.storage.languages;

import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LanguageManager {

    public static Icon getIconFromPath(String path) {
        File file = new File(path);
        String extension;

        if (file.isDirectory()) {
            extension = "folder";
        } else {
            extension = getExtension(path);
        }

        String basePath = "/images/LanguageIcons/";

        if (extension.equals("folder")) {
            try {
                return new ImageIcon(LanguageManager.class.getResource(basePath + "DefaultDirectory.png"));
            } catch (Exception e) {
                try {
                    return new ImageIcon(LanguageManager.class.getResource(basePath + "DefaultFile.png"));
                } catch (Exception ignored) {}
            }
        }
        try {
            return new ImageIcon(LanguageManager.class.getResource(basePath + extension + ".png"));
        } catch (Exception e) {
            try {
                return new ImageIcon(LanguageManager.class.getResource(basePath + "DefaultFile.png"));
            } catch (Exception ignored) {}
        }
        return null;
    }

    //TODO - this should also work if the path is already an extension.
    public static String getExtension(String path) {
        String fileName = path.replace('\\', '/'); //idk if this works outside of windows
        fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
        int dotIndex = fileName.lastIndexOf('.');

        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        } else {
            return ""; // No extension found
        }
    }

    //TODO - this is still somehow wrong. Everything is returning red, or white
    public static Color getLanguageColorFromPath(String path) {
        String extension;

        // Allow either a full path or just the extension
        if (path.startsWith(".")) {
            extension = path.toLowerCase();
        } else {
            File file = new File(path);
            extension = file.isDirectory() ? "folder" : "." + getExtension(path);
        }

        String basePath = "/LanguageDefinitions/" + extension + ".json";
        try {
            InputStream stream = LanguageManager.class.getResourceAsStream(basePath);
            if (stream == null) return Color.RED;

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }

            JSONObject obj = new JSONObject(json.toString());
            String hexColor = obj.getString("color");

            return Color.decode(hexColor);
        } catch (Exception e) {
            e.printStackTrace();
            return Color.BLACK;
        }
    }

}
