/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.games.gean.in;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author sam
 */
public class DataFieldSpecification {

    private static final String[] nonFlagFields = {"fieldName", "fieldType"};

    public static DataFieldSpecification fromMap(Map<String, String> obj) {
        // Read the required fields.
        DataFieldSpecification field = new DataFieldSpecification();
        field.setFieldName(obj.get("fieldName").toString());
        field.setFieldType(obj.get("fieldType").toString());
        // Read the flags.
        Set<String> flags = new HashSet<>();
        for (String key : obj.keySet()) {
            boolean flag = true;
            for (String property : nonFlagFields) {
                if (key.equals(property)) {
                    flag = false;
                }
            }
            if (flag) {
                flags.add(key);
            }
        }
        field.setFlags(flags);
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

}
