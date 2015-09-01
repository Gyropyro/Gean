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
import uk.co.samholder.games.gean.data.DataClassSpecification;
import uk.co.samholder.games.gean.data.DataFieldSpecification;
import uk.co.samholder.games.gean.utils.naming.NameFormat;

/**
 *
 * @author sam
 */
public class HashCodeGenerator implements ClassFeatureGenerator {

    private final DataClassSpecification classSpec;

    public HashCodeGenerator(DataClassSpecification classSpec) {
        this.classSpec = classSpec;
    }

    @Override
    public void generate(JDefinedClass cls, GenerationContext context) {
        if (classSpec.getFields().isEmpty()) {
            return; // No equals needed.
        }

        // Import the objects class, for hash coding.
        JClass objects = context.getTypeManager().getClassDirect("java.util.Objects");

        // Create the equals method.
        JMethod hashMethod = cls.method(JMod.PUBLIC, int.class, "hashCode");
        hashMethod.annotate(Override.class);
        JBlock body = hashMethod.body();

        // Check if the object is null.
        JVar hash = body.decl(JType.parse(context.getCodeModel(), "int"), "hash", JExpr.lit(3));

        // Do check for each field.
        for (DataFieldSpecification fieldSpec : classSpec.getFields()) {
            List<String> parts = NameFormat.namesToList(fieldSpec.getFieldName());
            String camelCaseFieldName = NameFormat.camelCase(parts, false);
            // Get the field value.
            JExpression thisField = JExpr.refthis(camelCaseFieldName);
            // Accumulate the hash code.
            JExpression fieldHashCode = objects.staticInvoke("hashCode").arg(thisField);

            body.assign(hash, JExpr.lit(79).mul(hash).plus(fieldHashCode));
        }

        // Return the processed hash value.
        body._return(hash);
    }
}
