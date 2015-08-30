/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.games.gean.in;

import com.sun.codemodel.JType;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import uk.co.samholder.games.gean.GenerationContext;

/**
 *
 * @author sam
 */
public class DataFieldSpecification {

    public static DataFieldSpecification fromMap(Map obj) {
        // Read the required fields.
        DataFieldSpecification field = new DataFieldSpecification();
        field.setFieldName(obj.get("fieldName").toString());
        field.setFieldType(obj.get("fieldType").toString());
        // Read the flags.
        if (obj.containsKey("flags")) {
            List<String> list = (List<String>) obj.get("flags");
            field.setFlags(new HashSet<>(list));
        } else {
            field.setFlags(new HashSet<>());
        }
        return field;
    }

    private String fieldName;
    private String fieldType;
    private Set<String> flags;

    public Set<String> getFlags() {
        return flags;
    }

    public void setFlags(Set<String> flags) {
        this.flags = flags;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public JType getType(GenerationContext context) {
        boolean isList = flags.contains("list");
        JType type = context.getTypeManager().getType(getFieldType(), isList);
        if (isList) {
            return context.getCodeModel().ref(List.class).narrow(type);
        }
        return type;
    }

}
