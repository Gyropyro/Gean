/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.games.gean;

import com.sun.codemodel.JCodeModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import uk.co.samholder.games.gean.dto.DataClassGenerator;
import uk.co.samholder.games.gean.in.DataClassSpecification;
import uk.co.samholder.games.gean.in.Specification;
import uk.co.samholder.games.gean.in.SpecificationReaderYAML;
import uk.co.samholder.games.gean.logging.Logger;

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
        Logger.log("Reading data from files...");
        SpecificationReaderYAML reader = new SpecificationReaderYAML();
        Specification globalSpecification = reader.read(inputFiles);

        Logger.log("Read " + globalSpecification.getClassSpecs().size() + " classes.");
        Logger.log("Initialising generation...");
        final JCodeModel codeModel = new JCodeModel();
        final GenerationContext context = new GenerationContext(codeModel, new GeanTypeManager(codeModel));
        // Create the class generator.
        DataClassGenerator classGenerator = new DataClassGenerator();

        Logger.log("Generating types...");
        // First pass, generate all type information.
        for (DataClassSpecification spec : globalSpecification.getClassSpecs()) {
            classGenerator.generateType(spec, context);
        }
        Logger.log("Generating classes...");
        // Second pass, generate each class.
        for (DataClassSpecification spec : globalSpecification.getClassSpecs()) {
            classGenerator.generate(spec, context);
        }
        // Do debugging stuff.
        context.getTypeManager().outputReport();
        // Create the source.
        outputDir.mkdirs();
        codeModel.build(outputDir);
    }

}
