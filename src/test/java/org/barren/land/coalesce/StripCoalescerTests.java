package org.barren.land.coalesce;

import org.barren.land.area.Area;
import org.barren.land.area.Strip;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StripCoalescerTests {
    private final StripCoalescer sut = new StripCoalescer();

    @Test
    public void getsOneAreaWhenStripsOverlap(){
        Map<Integer, List<Strip>> stripsByX =  new HashMap<>(2);
        stripsByX.put(0, Collections.singletonList(new Strip(0, 0, 3)));
        stripsByX.put(1, Collections.singletonList(new Strip(1, 2, 4)));
        List<Area> areas = sut.coalesce(stripsByX, 0, 2);
        System.out.println(areas);
//        List<Integer> areaSizes = areas.stream().map(Area::)
    }
}
