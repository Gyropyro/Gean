/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.games.gean.in;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sam
 */
public class SpecificationReaderYAML {

    public SpecificationReaderYAML() {
    }

    private void read(File file, Specification specification) throws YamlException, FileNotFoundException {
        final YamlReader reader = new YamlReader(new FileReader(file));
        Object object;

        while ((object = reader.read()) != null) {
            if (object instanceof Map) {
                Map map = (Map) object;
                if (map.containsKey("className")) {
                    // Case: a class specification.
                    specification.getClassSpecs().add(DataClassSpecification.fromMap(map));
                }
            } else {
                // Make it fail?
            }
        }

    }

    public Specification read(List<File> files) throws IOException {
        Specification specification = new Specification();
        for (File file : files) {
            try {
                read(file, specification);
            } catch (YamlException | FileNotFoundException ex) {
                throw new IOException("Error reading specification file!", ex);
            }
        }

        return specification;
    }

}
