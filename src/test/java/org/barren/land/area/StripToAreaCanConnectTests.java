package org.barren.land.area;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;

public class StripToAreaCanConnectTests {
    private final Strip leftStrip = new Strip(10, 20, 30);
    private final Strip rightStrip = new Strip(10, 50, 60);
    private final Strip adjacentToRightStrip = new Strip(11, 55, 65);

    private final List<Strip> twoStrips = Arrays.asList(leftStrip, rightStrip);
    private final Area area = new Area(twoStrips, twoStrips);

    @Test
    public void canConnect_areaCannotToAnotherArea(){
        Area anotherArea = mock(Area.class);
        Assert.assertFalse(area.canJoin(anotherArea));
    }

    @Test
    public void stripCannotConnectToAnotherStrip(){
        Assert.assertFalse(rightStrip.canJoin(adjacentToRightStrip));
    }

    @Test
    public void canConnect_areaCanToAdjacentStrip(){
        Assert.assertTrue(area.canJoin(adjacentToRightStrip));
        Assert.assertTrue(adjacentToRightStrip.canJoin(area));
    }

    @Test
    public void canConnect_areaCannotToNonAdjacentStrip(){
        Strip nonAdjacentStrip = new Strip(11, 60, 65);
        Assert.assertFalse(area.canJoin(nonAdjacentStrip));
        Assert.assertFalse(nonAdjacentStrip.canJoin(area));
    }

}
