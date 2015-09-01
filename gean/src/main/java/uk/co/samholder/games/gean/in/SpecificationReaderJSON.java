/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.games.gean.in;

import uk.co.samholder.games.gean.data.DataClassSpecificationList;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

/**
 *
 * @author sam
 */
public class SpecificationReaderJSON {

    private final Gson gson;

    public SpecificationReaderJSON() {
        this.gson = new Gson();
    }

    public DataClassSpecificationList readFiles(List<File> files) throws FileNotFoundException {
        DataClassSpecificationList list = new DataClassSpecificationList();
        for (File file : files) {
            list.addAll(gson.fromJson(new FileReader(file), DataClassSpecificationList.class));
        }
        return list;
    }
}
