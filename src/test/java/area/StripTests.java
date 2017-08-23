package area;

import org.junit.Assert;
import org.junit.Test;

public class StripTests {
    @Test
    public void getArea_works(){
        Strip strip = new Strip(1, 2, 3);
        Assert.assertEquals(1, strip.getArea());
    }

    @Test
    public void cannotCreateInvalidStrip(){
        try{
            Strip strip = new Strip(1, 2, 2);
            Assert.fail("Must blow up");
        }catch (IllegalArgumentException e){
            Assert.assertEquals("invalid: fromY=2 >= toY=2", e.getMessage());
        }
    }
}
