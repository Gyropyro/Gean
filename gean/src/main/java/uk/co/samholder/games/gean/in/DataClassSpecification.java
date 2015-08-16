/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.games.gean.in;

import java.util.Objects;
import java.util.Set;

/**
 *
 * @author sam
 */
public class DataClassSpecification {

    private String className;
    private String description;
    private String sourcePackage;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    private Set<DataFieldSpecification> fields;

    public Set<DataFieldSpecification> getFields() {
        return fields;
    }

    public void setFields(Set<DataFieldSpecification> fields) {
        this.fields = fields;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSourcePackage() {
        return sourcePackage;
    }

    public void setSourcePackage(String sourcePackage) {
        this.sourcePackage = sourcePackage;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.className);
        hash = 79 * hash + Objects.hashCode(this.description);
        hash = 79 * hash + Objects.hashCode(this.fields);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DataClassSpecification other = (DataClassSpecification) obj;
        if (!Objects.equals(this.className, other.className)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.fields, other.fields)) {
            return false;
        }
        return true;
    }
}
