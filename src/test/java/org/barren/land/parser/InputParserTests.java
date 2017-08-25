package org.barren.land.parser;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InputParserTests {
    private final InputParser sut = new InputParser();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseArrayAsString_detectsMissingLeftDoubleQuote(){
        String malformedInput = "1 2 3 4\"";
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("input must start with \", is: " + malformedInput);
        sut.parseArrayAsString(malformedInput);
    }

    @Test
    public void parseArrayAsString_detectsMissingRightDoubleQuote(){
        String malformedInput = "\"1 2 3 4";
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("input must end with \", is: " + malformedInput);
        sut.parseArrayAsString(malformedInput);
    }

    @Test
    public void parseArrayAsString_detectsWrongNumberOfTokens(){
        String inputWithWrongNumberOfIntegers = "\"1 2 3 4 5\"";
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Must have exactly four integers: " + inputWithWrongNumberOfIntegers);
        sut.parseArrayAsString(inputWithWrongNumberOfIntegers);
    }

    @Test
    public void parseArrayAsString_works(){
        String inputWithWrongNumberOfIntegers = "\"1 2 3 4\"";
        List<Integer> actual = sut.parseArrayAsString(inputWithWrongNumberOfIntegers);
        List<Integer> expected = Arrays.asList(1, 2, 3, 4);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void parseInput_detectsMissingLeftCurlyBrace(){
        String malformedInput = "}";
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("input must start with {, is: " + malformedInput);
        sut.parseInput(malformedInput);
    }

    @Test
    public void parseInput_detectsMissingRightCurlyBrace(){
        String malformedInput = "{";
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("input must end with }, is: " + malformedInput);
        sut.parseInput(malformedInput);
    }

    @Test
    public void parseInput_handlesEmptyInput(){
        String input = "{}";
        List<List<Integer>> actual = sut.parseInput(input);
        Assert.assertEquals(0, actual.size());
    }

    @Test
    public void parseInput_handlesOneList(){
        String input = "{\"12 34 56 7\"}";
        List<List<Integer>> actual = sut.parseInput(input);
        List<List<Integer>> expected = Collections.singletonList(Arrays.asList(12, 34, 56, 7));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void parseInput_handlesMultipleLists(){
        String input = "{\"12 34 56 7\", \"2 345 67 89\"}";
        List<List<Integer>> actual = sut.parseInput(input);
        List<List<Integer>> expected = Arrays.asList(
                Arrays.asList(12, 34, 56, 7),
                Arrays.asList(2, 345, 67, 89)
        );
        Assert.assertEquals(expected, actual);
    }
}
