/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poemgenerator.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dennis
 */
public class FileUtil {
    
    
    /**
     * Read lines from file in classpath.
     * @param url Url source file.
     * @return List of lines
     */
    public final static List<String> readLinesFromClasspath(String url){
        List<String> lines = null;
        try {
            ClassLoader classLoader = FileUtil.class.getClassLoader();
            InputStream is = classLoader.getResourceAsStream(url.substring("classpath:".length()));
            byte[] bytes = getBytesFromInputStream(is);
            Path path = Paths.get(Files.createTempFile("tmp", "File").toUri());
            Files.write(path, bytes);
            lines = readLines(path.toFile());
        } catch (IOException ex) {
            Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lines;
    }
    
    /**
     * Read lines from url file.
     * @param url Url source file.
     * @return List of lines
     */
    public final static List<String> readLines(String url){
        if (url!=null && url.startsWith("classpath:")){
            return readLinesFromClasspath(url);
        }
        return readLines(new File(url));
    }
    
    /**
     * Read a file and return you content as a list of lines.
     * @param f File
     * @return List of lines.
     */
    public final static List<String> readLines(File f){
        List<String> loadedLines = null;
        String readLine;
        try {
            BufferedReader b = new BufferedReader(new FileReader(f));
            System.out.println(String.format("Reading file %s", f.getAbsolutePath()));
            loadedLines = new ArrayList<>();
            while ((readLine = b.readLine()) != null) {
                loadedLines.add(readLine);
            }

        } catch (IOException e) {
            System.out.println(String.format("No se pudo cargar el archivo [%s] - %s", f.getName(), e.getMessage()));
            e.printStackTrace();
        }
        
        return loadedLines;
    }

    /**
     * Get bytes from an InputStream.
     * @param is Source.
     * @return Bytes taken.
     */
    private static byte[] getBytesFromInputStream(InputStream is) {
        DataInputStream dis = null;
        byte[] data = null;
        try {
            data = new byte[(int) is.available()];
            dis = new DataInputStream(is);
            dis.readFully(data);            
        } catch (IOException ex) {
            Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (dis!=null)
                try {
                    dis.close();
            } catch (IOException ex) {
                Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return data;
    }
    
}
