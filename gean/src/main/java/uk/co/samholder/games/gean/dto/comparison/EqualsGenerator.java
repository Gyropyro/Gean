/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.games.gean.dto.comparison;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;
import java.util.List;
import uk.co.samholder.games.gean.GenerationContext;
import uk.co.samholder.games.gean.generation.ClassFeatureGenerator;
import uk.co.samholder.games.gean.in.DataClassSpecification;
import uk.co.samholder.games.gean.in.DataFieldSpecification;
import uk.co.samholder.games.gean.utils.naming.NameFormat;

/**
 *
 * @author sam
 */
public class EqualsGenerator implements ClassFeatureGenerator {

    private final DataClassSpecification classSpec;

    public EqualsGenerator(DataClassSpecification classSpec) {
        this.classSpec = classSpec;
    }

    @Override
    public void generate(JDefinedClass cls, GenerationContext context) {
        if (classSpec.getFields().isEmpty()) {
            return; // No equals needed.
        }

        // Import the objects class, for comparison.
        JClass objects = context.getTypeManager().getClassDirect("java.util.Objects");

        // Create the equals method.
        JMethod equalsMethod = cls.method(JMod.PUBLIC, boolean.class, "equals");
        JVar param = equalsMethod.param(Object.class, "object");
        equalsMethod.annotate(Override.class);
        JBlock body = equalsMethod.body();

        // Check if the object is null.
        body._if(param.eq(JExpr._null()))._then()._return(JExpr.FALSE);

        // Check the type of the parameter.
        body._if(JExpr.invoke("getClass").ne(param.invoke("getClass")))._then()._return(JExpr.FALSE);

        // Make a cast.
        JVar castOther = body.decl(cls, "other", JExpr.cast(cls, param));

        // Do check for each field.
        for (DataFieldSpecification fieldSpec : classSpec.getFields()) {
            List<String> parts = NameFormat.namesToList(fieldSpec.getFieldName());
            String camelCaseFieldName = NameFormat.camelCase(parts, false);
            // Get the type of the field.
            String typeString = fieldSpec.getFieldType();
            JType typeClass = fieldSpec.getType(context);
            // Get the two expressions.
            JExpression thisField = JExpr.refthis(camelCaseFieldName);
            JExpression othersField = castOther.ref(camelCaseFieldName);
            // Perform the relevant check.
            JExpression equalsCheck;
            if (typeClass.isPrimitive()) {
                equalsCheck = thisField.ne(othersField);
            } else {
                equalsCheck = objects.staticInvoke("equals").arg(thisField).arg(othersField).not();
            }
            // Add the if statement, returning false if there is a mismatch.
            body._if(equalsCheck)._then()._return(JExpr.FALSE);
        }

        // Everything else passed, so return true.
        body._return(JExpr.TRUE);
    }

}
