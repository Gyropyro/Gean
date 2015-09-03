/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.games.gean.dto.factory;

import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JInvocation;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;
import java.util.ArrayList;
import java.util.List;
import uk.co.samholder.games.gean.GenerationContext;
import uk.co.samholder.games.gean.data.DataClassSpecification;
import uk.co.samholder.games.gean.data.DataFieldSpecification;
import uk.co.samholder.games.gean.generation.ClassFeatureGenerator;
import uk.co.samholder.games.gean.logging.Logger;
import uk.co.samholder.games.gean.utils.naming.NameFormat;

/**
 *
 * @author sam
 */
public class BuilderGenerator implements ClassFeatureGenerator {

    DataClassSpecification classSpec;

    public BuilderGenerator(DataClassSpecification classSpec) {
        this.classSpec = classSpec;
    }

    private String getBuildFieldName(String upperCamelCase) {
        return "build" + upperCamelCase;
    }

    @Override
    public void generate(JDefinedClass cls, GenerationContext context) {
        // Create the factory class.
        JDefinedClass factoryCls = null;
        try {
            factoryCls = context.getCodeModel()._class(classSpec.getSourcePackage() + "." + classSpec.getClassName() + "Factory");
        } catch (JClassAlreadyExistsException ex) {
            ex.printStackTrace();
        }
        // Generate package constructor.
        JMethod packageConstructor = cls.constructor(JMod.NONE);
        JBlock constructorBody = packageConstructor.body();
        // Generate build method.
        JMethod buildMethod = factoryCls.method(JMod.PUBLIC, cls, "build");
        JBlock build = buildMethod.body();
        List<JFieldVar> buildArgs = new ArrayList<>();
        // Generate the with methods.
        for (DataFieldSpecification field : classSpec.getFields()) {
            List<String> parts = NameFormat.namesToList(field.getFieldName());
            String camelCaseFieldName = NameFormat.camelCase(parts, false);
            String upperCamelCaseFieldName = NameFormat.camelCase(parts, true);

            // Generate builder method and field.
            JFieldVar fieldVar = factoryCls.field(JMod.PRIVATE, field.getType(context), getBuildFieldName(upperCamelCaseFieldName));
            fieldVar.assign(JExpr._null());
            JMethod withMethod = factoryCls.method(JMod.PUBLIC, factoryCls, String.format("with%s", upperCamelCaseFieldName));
            JVar parameter = withMethod.param(field.getType(context), camelCaseFieldName);
            JBlock body = withMethod.body();
            body.assign(fieldVar, parameter);
            body._return(JExpr._this());

            // Add to package constructor.
            JVar constructorVar = packageConstructor.param(field.getType(context), camelCaseFieldName);
            constructorBody.assign(JExpr._this().ref(camelCaseFieldName), constructorVar);

            // Add to build.
            buildArgs.add(fieldVar);
            // Not null check.
            if (field.getFlags().contains("not null")) {
                JType illegalNullException = context.getCodeModel()._ref(IllegalStateException.class);
                build._if(fieldVar.eq(JExpr._null())).
                        _then().
                        _throw(JExpr._new(illegalNullException).
                                arg(String.format("Null value not allowed for %s", field.getFieldName())));
            }

        }
        // Add build constructor call.
        JInvocation constructorInvoke = JExpr._new(cls);
        for (JFieldVar var : buildArgs) {
            constructorInvoke.arg(var);
        }
        build._return(constructorInvoke);
        Logger.log("Created builder");
    }

}
