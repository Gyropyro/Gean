/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.games.gean.dto;

import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JDocComment;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JType;
import java.io.IOException;
import java.util.List;
import uk.co.samholder.games.gean.GenerationContext;
import uk.co.samholder.games.gean.data.DataClassSpecification;
import uk.co.samholder.games.gean.data.DataFieldSpecification;
import uk.co.samholder.games.gean.dto.comparison.EqualsGenerator;
import uk.co.samholder.games.gean.dto.comparison.HashCodeGenerator;
import uk.co.samholder.games.gean.dto.setget.GetterGenerator;
import uk.co.samholder.games.gean.dto.setget.SetterGenerator;
import uk.co.samholder.games.gean.logging.Logger;
import uk.co.samholder.games.gean.utils.naming.NameFormat;

/**
 *
 * @author sam
 */
public class DataClassGenerator {

    public void generateType(DataClassSpecification classSpec, GenerationContext context) throws IOException {
        JType type = context.getCodeModel().ref(classSpec.getSourcePackage() + "." + classSpec.getClassName());
        Logger.log("Generated type for " + classSpec.getClassName() + "  ->  " + type.fullName());
        context.getTypeManager().putType(classSpec.getClassName(), type);
    }

    public void generate(DataClassSpecification classSpec, GenerationContext context) throws IOException {
        // Calculate the class name.
        List<String> classNameParts = NameFormat.namesToList(classSpec.getClassName());
        String camelCaseClassNameUpper = NameFormat.camelCase(classNameParts, true);
        // Create the class.
        JDefinedClass cls = null;
        try {
            cls = context.getCodeModel()._class(classSpec.getSourcePackage() + "." + camelCaseClassNameUpper);
        } catch (JClassAlreadyExistsException ex) {
            ex.printStackTrace();
        }
        // Generate the class javadoc.
        JDocComment doc = cls.javadoc();
        doc.add(classSpec.getDescription());
        // Create a method.
        for (DataFieldSpecification fieldSpec : classSpec.getFields()) {
            // Generate the field.
            FieldGenerator fieldGenerator = new FieldGenerator(fieldSpec);
            fieldGenerator.generate(cls, context);
            JFieldVar fieldVar = fieldGenerator.getFieldVar();
            // Generate the setters method.
            SetterGenerator setterGenerator = new SetterGenerator(classSpec, fieldSpec, fieldVar);
            setterGenerator.generate(cls, context);
            // Generate the getter method.
            GetterGenerator getterGenerator = new GetterGenerator(classSpec, fieldSpec, fieldVar);
            getterGenerator.generate(cls, context);
        }
        // Generate constructor.
        if (classSpec.getFlags().contains("use constructor")) {
            ConstructorGenerator constructorGenerator = new ConstructorGenerator(classSpec);
            constructorGenerator.generate(cls, context);
        }
        // Generate equals method.
        EqualsGenerator equalsGenerator = new EqualsGenerator(classSpec);
        equalsGenerator.generate(cls, context);
        // Generate hashcode method.
        HashCodeGenerator hashCodeGenerator = new HashCodeGenerator(classSpec);
        hashCodeGenerator.generate(cls, context);
    }

}
