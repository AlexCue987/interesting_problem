package area;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;

public class AreaTests {
    private final Strip leftStrip = new Strip(10, 20, 30);
    private final Strip rightStrip = new Strip(10, 50, 60);

    private final List<Strip> twoStrips = Arrays.asList(leftStrip, rightStrip);
    private final Area area = new Area(twoStrips, twoStrips);

    @Test
    public void canConnect_cannotToAnotherArea(){
        Area anotherArea = mock(Area.class);
        Assert.assertFalse(area.canConnect(anotherArea));
    }

    @Test
    public void canConnect_canToAdjacentStrip(){
        Strip adjacentToRightStrip = new Strip(11, 55, 65);
        Assert.assertTrue(area.canConnect(adjacentToRightStrip));
    }

    @Test
    public void canConnect_cannotToNonAdjacentStrip(){
        Strip nonAdjacentStrip = new Strip(11, 60, 65);
        Assert.assertFalse(area.canConnect(nonAdjacentStrip));
    }

}
