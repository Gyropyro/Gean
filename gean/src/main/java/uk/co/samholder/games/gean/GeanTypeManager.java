/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.games.gean;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JType;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author sam
 */
public class GeanTypeManager {

    private JCodeModel codeModel;
    private Map<String, JType> customTypeMap = new HashMap<>();
    private Map<String, JClass> classCache = new HashMap<>();

    public GeanTypeManager(JCodeModel codeModel) {
        this.codeModel = codeModel;
    }

    public void putType(String typeString, JType type) {
        if (customTypeMap.containsKey(typeString)) {
            throw new IllegalStateException("Type set twice in type manager!");
        }
        customTypeMap.put(typeString, type);
    }

    public JType getType(String typeString, boolean forceObject, boolean list) {
        // Check the type cache.
        if (customTypeMap.containsKey(typeString)) {
            return customTypeMap.get(typeString);
        }
        // Try for a basic type.
        JType basicType = getBasicType(typeString);
        if (basicType != null) {
            customTypeMap.put(typeString, basicType);
            return basicType;
        }
        return null;
    }

    public JType getBasicType(String typeString) {
        try {
            switch (typeString.toLowerCase()) {
                case "boolean":
                case "bool":
                    return codeModel.parseType("boolean");
                case "byte":
                    return codeModel.parseType("byte");
                case "short":
                    return codeModel.parseType("short");
                case "int":
                case "integer":
                    return codeModel.parseType("int");
                case "long":
                    return codeModel.parseType("long");
                case "string":
                case "str":
                    return codeModel.parseType("String");
            }
        } catch (ClassNotFoundException ex) {
        }
        return null;
    }

    public JType getObjectType(String typeString) {
        try {
            switch (typeString.toLowerCase()) {
                case "boolean":
                case "bool":
                    return codeModel.parseType("Boolean");
                case "byte":
                    return codeModel.parseType("Byte");
                case "short":
                    return codeModel.parseType("Short");
                case "int":
                case "integer":
                    return codeModel.parseType("Integer");
                case "long":
                    return codeModel.parseType("Long");
                case "string":
                case "str":
                    return codeModel.parseType("String");
            }
        } catch (ClassNotFoundException ex) {
        }
        return null;
    }

    /**
     * Gets a direct class reference. Useful for static invokation.
     *
     * @param fullyQualifiedClassName The fully qualified class name.
     * @return JClass object.
     */
    public JClass getClassDirect(String fullyQualifiedClassName) {
        if (!classCache.containsKey(fullyQualifiedClassName)) {
            JClass cls = codeModel.directClass(fullyQualifiedClassName);
            classCache.put(fullyQualifiedClassName, cls);
            return cls;
        }
        return classCache.get(fullyQualifiedClassName);
    }
}
