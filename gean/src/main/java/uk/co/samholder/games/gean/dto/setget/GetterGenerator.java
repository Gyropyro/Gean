/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.games.gean.dto.setget;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JDocComment;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import java.util.List;
import uk.co.samholder.games.gean.generation.ClassFeatureGenerator;
import uk.co.samholder.games.gean.in.DataClassSpecification;
import uk.co.samholder.games.gean.in.DataFieldSpecification;
import uk.co.samholder.games.gean.utils.naming.NameFormat;

/**
 *
 * @author sam
 */
public class GetterGenerator implements ClassFeatureGenerator {

    private final DataClassSpecification classSpec;
    private final DataFieldSpecification fieldSpec;
    private final JFieldVar fieldVar;
    private final JCodeModel codeModel;

    public GetterGenerator(DataClassSpecification classSpec, DataFieldSpecification fieldSpec, JFieldVar field, JCodeModel codeModel) {
        this.classSpec = classSpec;
        this.fieldSpec = fieldSpec;
        this.fieldVar = field;
        this.codeModel = codeModel;
    }

    @Override
    public void generate(JDefinedClass cls) {
        List<String> parts = NameFormat.namesToList(fieldSpec.getFieldName());
        String camelCaseFieldNameUpper = NameFormat.camelCase(parts, true);
        // Get the type.
        JType type = fieldSpec.getType(codeModel);

        // Create the getter method.
        JMethod getter = cls.method(JMod.PUBLIC, type, "get" + camelCaseFieldNameUpper);
        JBlock getterBody = getter.body();
        getterBody._return(fieldVar);

        // Create the setter method.
        JDocComment getterComment = getter.javadoc();
        getterComment.append("Gets the " + classSpec.getClassName() + "'s " + fieldSpec.getFieldName() + ".");
        getterComment.addReturn().add(fieldSpec.getFieldName() + ".");
    }

}
