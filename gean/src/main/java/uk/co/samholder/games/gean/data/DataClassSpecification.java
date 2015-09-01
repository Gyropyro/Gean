/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.games.gean.data;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author sam
 */
public class DataClassSpecification {

    public static DataClassSpecification fromMap(Map map) {
        DataClassSpecification spec = new DataClassSpecification();
        spec.setClassName(map.get("className").toString());
        if (map.containsKey("description")) {
            spec.setDescription(map.get("description").toString());
        }
        if (map.containsKey("sourcePackage")) {
            spec.setSourcePackage(map.get("sourcePackage").toString());
        }
        if (map.containsKey("flags")) {
            Set set = new HashSet();
            set.addAll((List<String>) map.get("flags"));
            spec.setFlags(set);
        }
        // Add fields.
        spec.setFields(new HashSet<>());
        if (map.containsKey("fields")) {
            List<Map> list = (List<Map>) map.get("fields");
            for (Map obj : list) {
                DataFieldSpecification field = DataFieldSpecification.fromMap(obj);
                spec.getFields().add(field);
            }
        }
        return spec;
    }

    private String className;
    private String description;
    private String sourcePackage;
    private Set<DataFieldSpecification> fields;
    private Set<String> flags;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public Set<String> getFlags() {
        return flags;
    }

    public void setFlags(Set<String> flags) {
        this.flags = flags;
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
