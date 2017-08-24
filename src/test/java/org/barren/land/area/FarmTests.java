package org.barren.land.area;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class FarmTests {
    private final Rectangle smallTestArea = new Rectangle(0, 0, 4, 4);
    private final List<Strip> allStrips = smallTestArea.getStrips();

    @Test
    public void handlesSampleInput1(){
        Rectangle sampleFarm = new Rectangle(0, 0, 400, 600);
        List<Rectangle> barrenLand = Collections.singletonList(
                new Rectangle(0, 292, 400, 308));
        Farm farm = new Farm(sampleFarm, barrenLand);
        List<Integer> actual = farm.getAreaSizes();
        List<Integer> expected = Arrays.asList(116800, 116800);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void handlesSampleInput2(){
        Rectangle sampleFarm = new Rectangle(0, 0, 400, 600);
        List<Rectangle> barrenLand = Arrays.asList(
                new Rectangle(48, 192, 352, 208),
                new Rectangle(48, 392, 352, 408),
                new Rectangle(120, 52, 136, 548),
                new Rectangle(260, 52, 276, 548)
        );
        Farm farm = new Farm(sampleFarm, barrenLand);
        List<Integer> actual = farm.getAreaSizes();
        List<Integer> expected = Arrays.asList(22816, 192608);
        Assert.assertEquals(expected, actual);
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
