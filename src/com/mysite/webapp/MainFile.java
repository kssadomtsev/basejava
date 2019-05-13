package com.mysite.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }
        File dir = new File("./src/com/mysite/webapp");
        readCatalog(dir, "");
/*      File dir = new File("./src/com/mysite/webapp");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }*/

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }
    }

    private static void readCatalog(File folder, String indent) {
        System.out.println(indent + "--" + folder.getAbsolutePath() + " :");
        File[] list = folder.listFiles();
        if (list != null) {
            for (File file : list) {
                if (file.isDirectory()) {
                    readCatalog(file, indent + "--");
                } else {
                    System.out.println(indent + "--" + file.getName());
                }
            }
        }
    }
}
