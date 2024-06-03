package de.tgl.Java2XML.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for file operations such as reading file contents and generating directories.
 */
public class FileReader {
    private static final Logger LOGGER = Logger.getLogger(FileReader.class.getName());

    /**
     * Reads the contents of a file and returns it as a String.
     *
     * @param path     the path to the file
     * @param encoding the character encoding to use
     * @return the contents of the file as a String
     * @throws IOException if an I/O error occurs reading from the file
     */
    public static String readFile(String path, Charset encoding) throws IOException {
        Path filePath = Paths.get(path);
        byte[] encoded = Files.readAllBytes(filePath);
        return new String(encoded, encoding);
    }

    /**
     * Generates a directory if it does not already exist.
     *
     * @param directory the name of the directory to generate
     */
    public static void generateDirectory(String directory) {
        Path dirPath = Paths.get(directory);
        if (Files.notExists(dirPath)) {
            try {
                Files.createDirectories(dirPath);
                LOGGER.log(Level.INFO, "Directory created: {0}", directory);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Failed to create directory: {0}", directory);
            }
        } else {
            LOGGER.log(Level.INFO, "Directory already exists: {0}", directory);
        }
    }
}
