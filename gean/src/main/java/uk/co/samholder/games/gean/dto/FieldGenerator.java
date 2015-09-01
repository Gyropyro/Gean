/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.games.gean.dto;

import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import java.util.List;
import uk.co.samholder.games.gean.GenerationContext;
import uk.co.samholder.games.gean.generation.ClassFeatureGenerator;
import uk.co.samholder.games.gean.data.DataFieldSpecification;
import uk.co.samholder.games.gean.utils.naming.NameFormat;

/**
 *
 * @author sam
 */
public class FieldGenerator implements ClassFeatureGenerator {

    private final DataFieldSpecification fieldSpec;
    private JFieldVar fieldVar;

    public FieldGenerator(DataFieldSpecification fieldSpec) {
        this.fieldSpec = fieldSpec;
    }

    @Override
    public void generate(JDefinedClass cls, GenerationContext context) {
        List<String> parts = NameFormat.namesToList(fieldSpec.getFieldName());
        String camelCaseFieldName = NameFormat.camelCase(parts, false);
        // Get the type.
        JType type = fieldSpec.getType(context);
        fieldVar = cls.field(JMod.PRIVATE, type, camelCaseFieldName);
    }

    /**
     * Gets the created field variable, after generate has been invoked.
     *
     * @return field variable representation.
     */
    public JFieldVar getFieldVar() {
        return fieldVar;
    }

}
