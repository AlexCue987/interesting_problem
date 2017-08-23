package org.barren.land.area;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StripSubtractorTests {
    private final int minuendX=1;
    private final int minuendFromY=10;
    private final int minuendToY=20;
    private final Strip minuend = new Strip(minuendX, 10, 20);
    private final List<Strip> minuendUnchanged = Collections.singletonList(minuend);

    StripSubtractor sut = new StripSubtractor();

    @Test
    public void noOverlapIfSubtrahendBelow(){
        Strip subtrahendBelow = new Strip(minuendX-1, minuendFromY, minuendToY);
        List<Strip> actual = sut.subtract(minuend, subtrahendBelow);
        Assert.assertEquals(minuendUnchanged, actual);
    }

    @Test
    public void noOverlapIfSubtrahendAbove(){
        Strip subtrahendBelow = new Strip(minuendX + 1, minuendFromY, minuendToY);
        List<Strip> actual = sut.subtract(minuend, subtrahendBelow);
        Assert.assertEquals(minuendUnchanged, actual);
    }

    @Test
    public void noOverlapIfSubtrahendOnLeft(){
        Strip subtrahendBelow = new Strip(minuendX, minuendFromY-5, minuendFromY);
        List<Strip> actual = sut.subtract(minuend, subtrahendBelow);
        Assert.assertEquals(minuendUnchanged, actual);
    }

    @Test
    public void noOverlapIfSubtrahendOnRight(){
        Strip subtrahendBelow = new Strip(minuendX, minuendToY, minuendToY+10);
        List<Strip> actual = sut.subtract(minuend, subtrahendBelow);
        Assert.assertEquals(minuendUnchanged, actual);
    }
    
    @Test
    public void removesLeftEnd(){
        int subtrahendToY = minuendFromY + 5;
        Strip subtrahend = new Strip(minuendX, minuendFromY-5, subtrahendToY);
        List<Strip> actual = sut.subtract(minuend, subtrahend);
        List<Strip> expected = Collections.singletonList(new Strip(minuendX, subtrahendToY, minuendToY));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void removesRightEnd(){
        int subtrahendFromY = minuendToY - 5;
        Strip subtrahend = new Strip(minuendX, subtrahendFromY, minuendToY);
        List<Strip> actual = sut.subtract(minuend, subtrahend);
        List<Strip> expected = Collections.singletonList(new Strip(minuendX, minuendFromY, subtrahendFromY));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void removesMiddle(){
        int subtrahendFromY = minuendFromY + 2;
        int subtrahendToY = minuendToY - 2;
        Strip subtrahend = new Strip(minuendX, subtrahendFromY, subtrahendToY);
        List<Strip> actual = sut.subtract(minuend, subtrahend);
        List<Strip> expected = Arrays.asList(
                new Strip(minuendX, minuendFromY, subtrahendFromY),
                new Strip(minuendX, subtrahendToY, minuendToY)
                );
        Assert.assertEquals(expected, actual);
    }
}
