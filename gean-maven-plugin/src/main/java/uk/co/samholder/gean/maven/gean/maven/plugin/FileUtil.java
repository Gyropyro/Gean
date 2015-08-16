/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.gean.maven.gean.maven.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sam
 */
public class FileUtil {

    public static List<File> getDirectoryFilesRecursive(File directory) {
        List<File> list = new ArrayList<>();
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                list.addAll(getDirectoryFilesRecursive(file));
            } else {
                list.add(file);
            }
        }
        return list;
    }

}
