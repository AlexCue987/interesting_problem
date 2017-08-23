package org.barren.land.area;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class FarmTests {
    private final Rectangle allArea = new Rectangle(0, 0, 4, 4);
    private final List<Strip> allStrips = allArea.getStrips();

    @Test
    public void getFertileStrips_keepsAsIsIfNoBarrenLand(){
        Map<Integer, List<Strip>> expected = allStrips.stream().
                collect(Collectors.groupingBy(Strip::getX));
        HashMap<Integer, List<Strip>> noBarrenLand = new HashMap<>();
        Map<Integer, List<Strip>> actual = Farm.getFertileStrips(allStrips, noBarrenLand);
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
        Map<Integer, List<Strip>> actual = Farm.getFertileStrips(allStrips, barrenLand);
        Assert.assertEquals(expected, actual);
    }
}
