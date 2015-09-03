/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.games.gean.dto;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;
import uk.co.samholder.games.gean.GenerationContext;
import uk.co.samholder.games.gean.data.DataClassSpecification;
import uk.co.samholder.games.gean.data.DataFieldSpecification;
import uk.co.samholder.games.gean.generation.ClassFeatureGenerator;

/**
 *
 * @author sam
 */
public class ConstructorGenerator implements ClassFeatureGenerator {

    private DataClassSpecification classSpecification;

    public ConstructorGenerator(DataClassSpecification classSpecification) {
        this.classSpecification = classSpecification;
    }

    @Override
    public void generate(JDefinedClass cls, GenerationContext context) {
        JMethod constructor = cls.constructor(JMod.PUBLIC);
        JBlock body = constructor.body();
        for (DataFieldSpecification field : classSpecification.getFields()) {
            if (field.getFlags().contains("in constructor")) {
                JVar var = constructor.param(field.getType(context), field.getFieldName());
                body.assign(JExpr._this().ref(field.getFieldName()), var);
            }
        }
    }

}
