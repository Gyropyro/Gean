/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.games.gean;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import uk.co.samholder.games.gean.dto.DataClassGenerator;
import uk.co.samholder.games.gean.in.DataClassSpecification;
import uk.co.samholder.games.gean.in.DataClassSpecificationList;
import uk.co.samholder.games.gean.in.SpecificationReaderJSON;

/**
 *
 * @author sam
 */
public class Gean {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        File outputDir = null;
        List<File> inputFiles = new ArrayList<>();
        boolean first = true;
        for (String arg : args) {
            if (first) {
                outputDir = new File(arg);
                first = false;
            } else {
                inputFiles.add(new File(arg));
            }
        }
        new Gean().processFiles(inputFiles, outputDir);
    }

    public void processFiles(List<File> inputFiles, File outputDir) throws FileNotFoundException, IOException {
        SpecificationReaderJSON reader = new SpecificationReaderJSON();
        DataClassSpecificationList list = reader.readFiles(inputFiles);
        System.out.println(new Gson().toJson(list));

        DataClassGenerator classGenerator = new DataClassGenerator(outputDir);
        for (DataClassSpecification spec : list) {
            classGenerator.generate(spec);
        }
    }

}
