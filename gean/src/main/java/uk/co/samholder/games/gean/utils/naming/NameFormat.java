/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.samholder.games.gean.utils.naming;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author sam
 */
public class NameFormat {

    public static List<String> namesToList(String names) {
        List<String> parts = new ArrayList<>();
        Collections.addAll(parts, names.split(" "));
        return parts;
    }

    public static String camelCase(List<String> input, boolean startCaps) {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (String part : input) {
            boolean useCaps = true;
            if (first) {
                first = false;
                if (!startCaps) {
                    useCaps = false;
                }
            }

            String firstCharacter = part.substring(0, 1);
            if (useCaps) {
                firstCharacter = firstCharacter.toUpperCase();
            } else {
                firstCharacter = firstCharacter.toLowerCase();
            }
            builder.append(firstCharacter);
            builder.append(part.substring(1).toLowerCase());
        }
        return builder.toString();
    }

}
