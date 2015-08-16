/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.games.gean.dto;

import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JDocComment;
import com.sun.codemodel.JFieldVar;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.co.samholder.games.gean.dto.comparison.EqualsGenerator;
import uk.co.samholder.games.gean.dto.comparison.HashCodeGenerator;
import uk.co.samholder.games.gean.dto.setget.GetterGenerator;
import uk.co.samholder.games.gean.dto.setget.SetterGenerator;
import uk.co.samholder.games.gean.generation.util.ClassManager;
import uk.co.samholder.games.gean.in.DataClassSpecification;
import uk.co.samholder.games.gean.in.DataFieldSpecification;
import uk.co.samholder.games.gean.utils.naming.NameFormat;

/**
 *
 * @author sam
 */
public class DataClassGenerator {

    private static final Logger LOG = Logger.getLogger(DataClassGenerator.class.getName());

    private final File outputDirectory;
    private final JCodeModel codeModel;

    public DataClassGenerator(File outputDirectory) {
        this.outputDirectory = outputDirectory;
        codeModel = new JCodeModel();
    }

    public void generate(DataClassSpecification classSpec) throws IOException {
        LOG.log(Level.INFO, "Generating data transfer object class for {0}", classSpec.getClassName());
        ClassManager classManager = new ClassManager(codeModel);
        // Calculate the class name.
        List<String> classNameParts = NameFormat.namesToList(classSpec.getClassName());
        String camelCaseClassNameUpper = NameFormat.camelCase(classNameParts, true);
        // Create the class.
        JDefinedClass cls = null;
        try {
            cls = codeModel._class(classSpec.getSourcePackage() + "." + camelCaseClassNameUpper);
        } catch (JClassAlreadyExistsException ex) {
            Logger.getLogger(DataClassGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Generate the class javadoc.
        JDocComment doc = cls.javadoc();
        doc.add(classSpec.getDescription());
        // Create a method.
        for (DataFieldSpecification fieldSpec : classSpec.getFields()) {
            // Generate the field.
            FieldGenerator fieldGenerator = new FieldGenerator(fieldSpec);
            fieldGenerator.generate(cls);
            JFieldVar fieldVar = fieldGenerator.getFieldVar();
            // Generate the setters method.
            SetterGenerator setterGenerator = new SetterGenerator(classSpec, fieldSpec, fieldVar);
            setterGenerator.generate(cls);
            // Generate the getter method.
            GetterGenerator getterGenerator = new GetterGenerator(classSpec, fieldSpec, fieldVar);
            getterGenerator.generate(cls);

        }
        // Generate equals method.
        EqualsGenerator equalsGenerator = new EqualsGenerator(classSpec, classManager);
        equalsGenerator.generate(cls);
        // Generate hashcode method.
        HashCodeGenerator hashCodeGenerator = new HashCodeGenerator(classSpec, classManager, codeModel);
        hashCodeGenerator.generate(cls);
        // Create the source.
        outputDirectory.mkdirs();
        codeModel.build(outputDirectory);
    }

}
