/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.games.gean.utils.typing;

/**
 *
 * @author sam
 */
public class TypeUtils {

    public static final Class<?> getBasicType(String typeString) {
        switch (typeString.toLowerCase()) {
            case "boolean":
            case "bool":
                return boolean.class;
            case "byte":
                return byte.class;
            case "short":
                return short.class;
            case "int":
            case "integer":
                return int.class;
            case "long":
                return long.class;
            case "string":
            case "str":
                return String.class;
        }
        return null;
    }

    public static Class<?> getObjectType(String typeString) {
        switch (typeString.toLowerCase()) {
            case "boolean":
            case "bool":
                return Boolean.class;
            case "byte":
                return Byte.class;
            case "short":
                return Short.class;
            case "int":
            case "integer":
                return Integer.class;
            case "long":
                return Long.class;
            case "string":
            case "str":
                return String.class;
        }
        return null;
    }

}
