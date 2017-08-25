package org.barren.land.parser;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InputParser {
    public List<List<Integer>> parseInput(String input){
        assertCondition(input.startsWith("{"), "input must start with {, is: " + input);
        assertCondition(input.endsWith("}"), "input must end with }, is: " + input);
        input = input.replace("{", "");
        input = input.replace("}", "");
        String[] tokens = StringUtils.splitByWholeSeparator(input, ", ");
        return Arrays.stream(tokens).map(this::parseArrayAsString).collect(Collectors.toList());
    }

    List<Integer> parseArrayAsString(String intArrayAsString){
        assertCondition(intArrayAsString.startsWith("\""), "input must start with \", is: " + intArrayAsString);
        assertCondition(intArrayAsString.endsWith("\""), "input must end with \", is: " + intArrayAsString);
        intArrayAsString = intArrayAsString.substring(1, intArrayAsString.length()-1);
        String[] integers = intArrayAsString.split(" ");
        assertCondition(integers.length==4, "Must have exactly four integers: \"" + intArrayAsString + "\"");
        return Arrays.stream(integers).map(this::getInteger).collect(Collectors.toList());
    }

    Integer getInteger(String integerAsString){
        try{
            return Integer.valueOf(integerAsString);
        }catch (Exception e){
            throw new IllegalArgumentException("Not an integer: " + integerAsString);
        }
    }

    static void assertCondition(boolean condition, String message){
        if(!condition){
            throw new RuntimeException(message);
        }
    }
}
