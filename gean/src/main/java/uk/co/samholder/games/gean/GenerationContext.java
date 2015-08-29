/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.games.gean;

import com.sun.codemodel.JCodeModel;

/**
 *
 * @author sam
 */
public class GenerationContext {

    private JCodeModel codeModel;
    private GeanTypeManager typeManager;

    public GenerationContext(JCodeModel codeModel, GeanTypeManager typeManager) {
        this.codeModel = codeModel;
        this.typeManager = typeManager;
    }

    public JCodeModel getCodeModel() {
        return codeModel;
    }

    public GeanTypeManager getTypeManager() {
        return typeManager;
    }

}
