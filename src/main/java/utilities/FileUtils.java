package utilities;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    public static Path getFilePathFromClasspath(String folderName, String fileName) {
        try {
            URL url = FileUtils.class.getClassLoader().getResource(folderName + "/" + fileName);
            if (url == null) {
                throw new RuntimeException("File not found in classpath: " + folderName + "/" + fileName);
            }
            return Paths.get(url.toURI());
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while accessing file: " + folderName + "/" + fileName, e);
        }
    }

    public static String getFileAbsolutePath(String folderName, String fileName) {
        Path path = getFilePathFromClasspath(folderName, fileName);
        return path.toAbsolutePath().toString();
    }

    public static File getFileFromClasspath(String folderName, String fileName) {
        Path path = getFilePathFromClasspath(folderName, fileName);
        return path.toFile();
    }

}
