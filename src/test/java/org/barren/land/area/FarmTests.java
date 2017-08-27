package org.barren.land.area;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class FarmTests {
    private final Rectangle smallTestArea = new Rectangle(0, 0, 4, 4);
    private final List<Strip> allStrips = smallTestArea.getStrips();

    @Test
    public void example1_works(){
        String input = "{\"0 292 399 307\"}";
        Farm farm = Farm.fromStdinInput(input);
        String actual = farm.getAreaSizesAsString();
        Assert.assertEquals("116800 116800", actual);
    }

    @Test
    public void example2_works(){
        String input = "{\"48 192 351 207\", \"48 392 351 407\", \"120 52 135 547\", \"260 52 275 547\"}";
        Farm farm = Farm.fromStdinInput(input);
        String actual = farm.getAreaSizesAsString();
        Assert.assertEquals("22816 192608", actual);
    }

    @Test
    public void getFertileStrips_keepsAsIsIfNoBarrenLand(){
        Map<Integer, List<Strip>> expected = allStrips.stream().
                collect(Collectors.groupingBy(Strip::getX));
        HashMap<Integer, List<Strip>> noBarrenLand = new HashMap<>();
        Map<Integer, List<Strip>> actual = Farm.getFertileStripsByX(allStrips, noBarrenLand);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getFertileStrips_holeInTheMiddle(){
        HashMap<Integer, List<Strip>> barrenLand = new HashMap<>();
        barrenLand.put(1, Collections.singletonList(new Strip(1, 1, 3)));
        barrenLand.put(2, Collections.singletonList(new Strip(2, 1, 3)));
        Map<Integer, List<Strip>> expected = new HashMap<>();
        expected.put(0, Collections.singletonList(new Strip(0, 0, 4)));
        expected.put(1, Arrays.asList(new Strip(1, 0, 1), new Strip(1, 3, 4)));
        expected.put(2, Arrays.asList(new Strip(2, 0, 1), new Strip(2, 3, 4)));
        expected.put(3, Collections.singletonList(new Strip(3, 0, 4)));
        Map<Integer, List<Strip>> actual = Farm.getFertileStripsByX(allStrips, barrenLand);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getFertileStrips_WithoutTwoCorners(){
        HashMap<Integer, List<Strip>> barrenLand = new HashMap<>();
        barrenLand.put(0, Collections.singletonList(new Strip(0, 0, 2)));
        barrenLand.put(1, Collections.singletonList(new Strip(1, 0, 2)));
        barrenLand.put(2, Collections.singletonList(new Strip(2, 2, 4)));
        barrenLand.put(3, Collections.singletonList(new Strip(3, 2, 4)));
        Map<Integer, List<Strip>> expected = new HashMap<>();
        expected.put(0, Collections.singletonList(new Strip(0, 2, 4)));
        expected.put(1, Collections.singletonList(new Strip(1, 2, 4)));
        expected.put(2, Collections.singletonList(new Strip(2, 0, 2)));
        expected.put(3, Collections.singletonList(new Strip(3, 0, 2)));
        Map<Integer, List<Strip>> actual = Farm.getFertileStripsByX(allStrips, barrenLand);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getFertileStrips_cutInHalf(){
        HashMap<Integer, List<Strip>> barrenLand = new HashMap<>();
        barrenLand.put(1, Collections.singletonList(new Strip(1, 0, 4)));
        barrenLand.put(2, Collections.singletonList(new Strip(2, 2, 4)));
        Map<Integer, List<Strip>> expected = new HashMap<>();
        expected.put(0, Collections.singletonList(new Strip(0, 0, 4)));
        expected.put(2, Collections.singletonList(new Strip(2, 0, 2)));
        expected.put(3, Collections.singletonList(new Strip(3, 0, 4)));
        Map<Integer, List<Strip>> actual = Farm.getFertileStripsByX(allStrips, barrenLand);
        Assert.assertEquals(expected, actual);
    }
}
