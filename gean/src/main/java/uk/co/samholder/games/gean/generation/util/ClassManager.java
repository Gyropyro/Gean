/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.games.gean.generation.util;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author sam
 */
public class ClassManager {

    private final JCodeModel codeModel;
    private Map<String, JClass> cache;

    public ClassManager(JCodeModel codeModel) {
        this.codeModel = codeModel;
        cache = new HashMap<>();
    }

    /**
     * Gets a direct class reference. Useful for static invokation.
     *
     * @param fullyQualifiedClassName The fully qualified class name.
     * @return JClass object.
     */
    public JClass getClassDirect(String fullyQualifiedClassName) {
        if (!cache.containsKey(fullyQualifiedClassName)) {
            JClass cls = codeModel.directClass(fullyQualifiedClassName);
            cache.put(fullyQualifiedClassName, cls);
            return cls;
        }
        return cache.get(fullyQualifiedClassName);
    }

}
