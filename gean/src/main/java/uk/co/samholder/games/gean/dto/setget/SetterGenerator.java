/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.games.gean.dto.setget;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JDocComment;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;
import java.util.List;
import uk.co.samholder.games.gean.GenerationContext;
import uk.co.samholder.games.gean.data.DataClassSpecification;
import uk.co.samholder.games.gean.data.DataFieldSpecification;
import uk.co.samholder.games.gean.generation.ClassFeatureGenerator;
import uk.co.samholder.games.gean.utils.naming.NameFormat;

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
    public void generate(JDefinedClass cls, GenerationContext context) {
        List<String> parts = NameFormat.namesToList(fieldSpec.getFieldName());
        String camelCaseFieldName = NameFormat.camelCase(parts, false);
        String camelCaseFieldNameUpper = NameFormat.camelCase(parts, true);

        // Get the type.
        JType type = fieldSpec.getType(context);

        // Generate the method.
        JMethod setter = cls.method(JMod.PUBLIC, void.class, "set" + camelCaseFieldNameUpper);
        JVar setVar = setter.param(type, camelCaseFieldName);
        JBlock setterBody = setter.body();

        // Add null check to body.
        if (fieldSpec.getFlags().contains("not null")) {
            JType illegalNullException = context.getCodeModel()._ref(IllegalArgumentException.class);
            JExpression exceptionExpression = JExpr._new(illegalNullException).arg(JExpr.lit(String.format("Null value not allowed for %s", fieldSpec.getFieldName())));
            setterBody._if(fieldVar.eq(JExpr._null()))._then()._throw(exceptionExpression);
        }

        // Add assignment to body.
        setterBody.assign(JExpr._this().ref(fieldVar), setVar);

        // Generate the javadocs.
        JDocComment setterComment = setter.javadoc();
        setterComment.append("Sets the " + classSpec.getClassName() + "'s " + fieldSpec.getFieldName() + ".");
        setterComment.addParam(setVar).add("the " + fieldSpec.getFieldName() + ".");
    }

}
