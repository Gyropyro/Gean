/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.games.gean.in;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sam
 */
public class Specification {

    List<DataClassSpecification> classSpecs = new ArrayList<>();

    public List<DataClassSpecification> getClassSpecs() {
        return classSpecs;
    }

    public void setClassSpecs(List<DataClassSpecification> classSpecs) {
        this.classSpecs = classSpecs;
    }

}
