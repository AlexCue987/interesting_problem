package area;

import org.junit.Assert;
import org.junit.Test;

public class StripTests {
    Strip strip = new Strip(10, 10, 15);

    @Test
    public void getArea_works(){
        Assert.assertEquals(5, strip.getArea());
    }

    @Test
    public void cannotCreateInvalidStrip(){
        try{
            Strip strip = new Strip(1, 2, 2);
            Assert.fail("Must throw IllegalArgumentException");
        }catch (IllegalArgumentException e){
            Assert.assertEquals("invalid: fromY=2 >= toY=2", e.getMessage());
        }catch (Exception e){
            Assert.fail("Must throw IllegalArgumentException");
        }
    }

    @Test
    public void notAdjacent_tooLow(){
        Strip other = new Strip(8, 10, 11);
        Assert.assertFalse(strip.isAdjacentTo(other));
    }

    @Test
    public void notAdjacent_sameLine(){
        Strip other = new Strip(10, 10, 11);
        Assert.assertFalse(strip.isAdjacentTo(other));
    }

    @Test
    public void notAdjacent_tooHigh(){
        Strip other = new Strip(12, 10, 11);
        Assert.assertFalse(strip.isAdjacentTo(other));
    }

    @Test
    public void notAdjacent_tooLeft(){
        Strip other = new Strip(9, 9, 10);
        Assert.assertFalse(strip.isAdjacentTo(other));
    }

    @Test
    public void notAdjacent_tooRight(){
        Strip other = new Strip(9, 15, 17);
        Assert.assertFalse(strip.isAdjacentTo(other));
    }

    @Test
    public void adjacent(){
        Strip other = new Strip(9, 9, 12);
        Assert.assertTrue(strip.isAdjacentTo(other));
    }
}
