package org.barren.land.coalesce;

import org.barren.land.area.Area;
import org.barren.land.area.Strip;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class StripCoalescerTests {
    private final StripCoalescer sut = new StripCoalescer();

    @Test
    public void getsOneAreaWhenStripsOverlap(){
        Map<Integer, List<Strip>> stripsByX =  new HashMap<>(2);
        stripsByX.put(0, Collections.singletonList(new Strip(0, 0, 3)));
        stripsByX.put(1, Collections.singletonList(new Strip(1, 2, 4)));
        List<Area> areas = sut.coalesce(stripsByX, 0, 2);
        List<Integer> areaSizes = getSortedAreaSizes(areas);
//        System.out.println(areaSizes);
        List<Integer> expected = Collections.singletonList(5);
        Assert.assertEquals(expected, areaSizes);
    }

    @Test
    public void getsTwoAreasWhenStripsJustTouch(){
        Map<Integer, List<Strip>> stripsByX =  new HashMap<>(2);
        stripsByX.put(0, Collections.singletonList(new Strip(0, 0, 3)));
        stripsByX.put(1, Collections.singletonList(new Strip(1, 3, 4)));
        List<Area> areas = sut.coalesce(stripsByX, 0, 2);
        List<Integer> areaSizes = getSortedAreaSizes(areas);
//        System.out.println(areaSizes);
        List<Integer> expected = Arrays.asList(1, 3);
        Assert.assertEquals(expected, areaSizes);
    }

    @Test
    public void getsTwoHorizontalAreas(){
        Map<Integer, List<Strip>> stripsByX =  new HashMap<>(5);
        stripsByX.put(0, Collections.singletonList(new Strip(0, 0, 3)));
        stripsByX.put(1, Collections.singletonList(new Strip(1, 0, 4)));
        stripsByX.put(3, Collections.singletonList(new Strip(3, 0, 6)));
        stripsByX.put(4, Collections.singletonList(new Strip(4, 1, 4)));
        List<Area> areas = sut.coalesce(stripsByX, 0, 5);
        List<Integer> areaSizes = getSortedAreaSizes(areas);
//        System.out.println(areaSizes);
        List<Integer> expected = Arrays.asList(7, 9);
        Assert.assertEquals(expected, areaSizes);
    }

    @Test
    public void getsTwoVerticalAreas(){
        Map<Integer, List<Strip>> stripsByX =  new HashMap<>(2);
        stripsByX.put(0, Collections.singletonList(new Strip(0, 0, 3)));
        stripsByX.put(1, Arrays.asList(new Strip(1, 0, 3),
                new Strip(1, 6, 10)));
        stripsByX.put(2, Collections.singletonList(new Strip(2, 8, 12)));
        List<Area> areas = sut.coalesce(stripsByX, 0, 5);
        List<Integer> areaSizes = getSortedAreaSizes(areas);
//        System.out.println(areaSizes);
        List<Integer> expected = Arrays.asList(6, 8);
        Assert.assertEquals(expected, areaSizes);
    }

    @Test
    public void connectsTwoVerticalAreasByLastStrip(){
        Map<Integer, List<Strip>> stripsByX =  new HashMap<>(2);
        stripsByX.put(1, Arrays.asList(new Strip(1, 0, 3),
                new Strip(1, 6, 10)));
        stripsByX.put(2, Arrays.asList(new Strip(2, 2, 5),
                new Strip(2, 8, 12)));
        stripsByX.put(3, Collections.singletonList(new Strip(3, 4, 10)));
        List<Area> areas = sut.coalesce(stripsByX, 0, 5);
        List<Integer> areaSizes = getSortedAreaSizes(areas);
//        System.out.println(areaSizes);
        List<Integer> expected = Arrays.asList(20);
        Assert.assertEquals(expected, areaSizes);
    }

    private List<Integer> getSortedAreaSizes(List<Area> areas) {
        return areas.stream().
                    map(Area::getAreaInSquareMeters).
                    sorted().
                    collect(Collectors.toList());
    }
}
