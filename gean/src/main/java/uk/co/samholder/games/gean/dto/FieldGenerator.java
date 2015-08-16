/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.games.gean.dto;

import uk.co.samholder.games.gean.generation.ClassFeatureGenerator;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMod;
import java.util.List;
import uk.co.samholder.games.gean.in.DataFieldSpecification;
import uk.co.samholder.games.gean.utils.naming.NameFormat;
import uk.co.samholder.games.gean.utils.typing.TypeUtils;

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
    public void generate(JDefinedClass cls) {
        List<String> parts = NameFormat.namesToList(fieldSpec.getFieldName());
        String camelCaseFieldName = NameFormat.camelCase(parts, false);
        // Get the field type.
        String typeString = fieldSpec.getFieldType();
        Class<?> typeClass = TypeUtils.getBasicType(typeString);

        fieldVar = cls.field(JMod.PRIVATE, typeClass, camelCaseFieldName);
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
