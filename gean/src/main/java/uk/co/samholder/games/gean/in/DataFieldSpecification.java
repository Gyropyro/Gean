/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.games.gean.in;

import java.util.Map;

/**
 *
 * @author sam
 */
public class DataFieldSpecification {

    public static DataFieldSpecification fromMap(Map obj) {
        DataFieldSpecification field = new DataFieldSpecification();
        field.setFieldName(obj.get("fieldName").toString());
        field.setFieldType(obj.get("fieldType").toString());
        return field;
    }

    private String fieldName;
    private String fieldType;

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
