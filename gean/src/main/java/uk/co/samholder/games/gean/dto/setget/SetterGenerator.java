/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.games.gean.dto.setget;

import uk.co.samholder.games.gean.generation.ClassFeatureGenerator;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JDocComment;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldRef;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;
import java.util.List;
import uk.co.samholder.games.gean.in.DataFieldSpecification;
import uk.co.samholder.games.gean.in.DataClassSpecification;
import uk.co.samholder.games.gean.utils.naming.NameFormat;
import uk.co.samholder.games.gean.utils.typing.TypeUtils;

/**
 *
 * @author sam
 */
public class SetterGenerator implements ClassFeatureGenerator {

    private final DataClassSpecification classSpec;
    private final DataFieldSpecification fieldSpec;
    private final JFieldVar fieldVar;

    public SetterGenerator(DataClassSpecification classSpec, DataFieldSpecification fieldSpec, JFieldVar fieldVar) {
        this.classSpec = classSpec;
        this.fieldSpec = fieldSpec;
        this.fieldVar = fieldVar;
    }

    @Override
    public void generate(JDefinedClass cls) {
        List<String> parts = NameFormat.namesToList(fieldSpec.getFieldName());
        String camelCaseFieldName = NameFormat.camelCase(parts, false);
        String camelCaseFieldNameUpper = NameFormat.camelCase(parts, true);

        // Get the field type.
        String typeString = fieldSpec.getFieldType();
        Class<?> typeClass = TypeUtils.getBasicType(typeString);

        // Generate the method.
        JMethod setter = cls.method(JMod.PUBLIC, void.class, "set" + camelCaseFieldNameUpper);
        JVar setVar = setter.param(typeClass, camelCaseFieldName);
        JBlock setterBody = setter.body();
        setterBody.assign(JExpr._this().ref(fieldVar), setVar);

        // Generate the javadocs.
        JDocComment setterComment = setter.javadoc();
        setterComment.append("Sets the " + classSpec.getClassName() + "'s " + fieldSpec.getFieldName() + ".");
        setterComment.addParam(setVar).add("the " + fieldSpec.getFieldName() + ".");
    }

}