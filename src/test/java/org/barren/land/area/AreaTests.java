package org.barren.land.area;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AreaTests {
    @Test
    public void getAreaInSquareMeters_works(){
        List<Strip> strips = Arrays.asList(new Strip(0, 1, 10),
                new Strip(1, 5, 20));
        List<Strip> openBorderIgnored = Collections.emptyList();
        Area area = new Area(strips, openBorderIgnored);
        Assert.assertEquals(24, area.getAreaInSquareMeters());
    }

    @Test
    public void getAreaInSquareMeters_handlesEmptyArea(){
        List<Strip> noStrips = Collections.emptyList();
        List<Strip> openBorderIgnored = Collections.emptyList();
        Area area = new Area(noStrips, openBorderIgnored);
        Assert.assertEquals(0, area.getAreaInSquareMeters());
    }
}
