package org.barren.land.area;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RectangleTests {
    @Test
    public void getStrips_mustReturnOneStrip(){
        Rectangle rectangle = new Rectangle(0, 0, 1, 5);
        List<Strip> actual = rectangle.getStrips();
        List<Strip> expected = Collections.singletonList(new Strip(0, 0, 5));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getStrips_mustReturnTwoStrips(){
        Rectangle rectangle = new Rectangle(0, 0, 2, 5);
        List<Strip> actual = rectangle.getStrips();
        List<Strip> expected = Arrays.asList(
                new Strip(0, 0, 5),
                new Strip(1, 0, 5)
                );
        Assert.assertEquals(expected, actual);
    }
}
