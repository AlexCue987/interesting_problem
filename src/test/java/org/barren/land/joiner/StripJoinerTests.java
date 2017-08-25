package org.barren.land.joiner;

import org.barren.land.area.Area;
import org.barren.land.area.Strip;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class StripJoinerTests {
    private final StripJoiner sut = new StripJoiner();

    @Test
    public void handlesNoStrips(){
        Map<Integer, List<Strip>> stripsByX =  new HashMap<>(2);
        List<Area> areas = sut.join(stripsByX, 0, 2);
        List<Integer> areaSizes = getSortedAreaSizes(areas);
        List<Integer> expected = Collections.emptyList();
        Assert.assertEquals(expected, areaSizes);
    }

    @Test
    public void getsOneAreaWhenStripsOverlap(){
        Map<Integer, List<Strip>> stripsByX =  new HashMap<>(2);
        stripsByX.put(0, Collections.singletonList(new Strip(0, 0, 3)));
        stripsByX.put(1, Collections.singletonList(new Strip(1, 2, 4)));
        List<Area> areas = sut.join(stripsByX, 0, 2);
        List<Integer> areaSizes = getSortedAreaSizes(areas);
        List<Integer> expected = Collections.singletonList(5);
        Assert.assertEquals(expected, areaSizes);
    }

    @Test
    public void getsTwoAreasWhenStripsJustTouch(){
        Map<Integer, List<Strip>> stripsByX =  new HashMap<>(2);
        stripsByX.put(0, Collections.singletonList(new Strip(0, 0, 3)));
        stripsByX.put(1, Collections.singletonList(new Strip(1, 3, 4)));
        List<Area> areas = sut.join(stripsByX, 0, 2);
        List<Integer> areaSizes = getSortedAreaSizes(areas);
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
        List<Area> areas = sut.join(stripsByX, 0, 5);
        List<Integer> areaSizes = getSortedAreaSizes(areas);
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
        List<Area> areas = sut.join(stripsByX, 0, 5);
        List<Integer> areaSizes = getSortedAreaSizes(areas);
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
        List<Area> areas = sut.join(stripsByX, 0, 5);
        List<Integer> areaSizes = getSortedAreaSizes(areas);
        List<Integer> expected = Arrays.asList(20);
        Assert.assertEquals(expected, areaSizes);
    }

    @Test
    public void keepsAnIslandInsideACircle(){
        Map<Integer, List<Strip>> stripsByX =  new HashMap<>(10);
        stripsByX.put(0, Collections.singletonList(new Strip(0, 0, 10)));
        stripsByX.put(1, Arrays.asList(new Strip(1, 0, 2),
                new Strip(1, 8, 10)));
        stripsByX.put(2, Arrays.asList(new Strip(2, 0, 2),
                new Strip(2, 4, 6),
                new Strip(2, 8, 10)));
        stripsByX.put(3, Arrays.asList(new Strip(3, 0, 2),
                new Strip(3, 5, 7),
                new Strip(3, 8, 10)));
        stripsByX.put(4, Arrays.asList(new Strip(4, 0, 2),
                new Strip(4, 8, 10)));
        stripsByX.put(5, Collections.singletonList(new Strip(5, 1, 9)));
        List<Area> areas = sut.join(stripsByX, 0, 5);
        List<Integer> areaSizes = getSortedAreaSizes(areas);
        int islandSize = 4;
        int circleSize = 26;
        List<Integer> expected = Arrays.asList(islandSize, circleSize);
        Assert.assertEquals(expected, areaSizes);
    }

    @Test
    public void connectsLetterCintoOneArea(){
        Map<Integer, List<Strip>> stripsByX =  new HashMap<>(10);
        stripsByX.put(0, Collections.singletonList(new Strip(0, 1, 9)));
        stripsByX.put(1, Arrays.asList(new Strip(1, 0, 2),
                new Strip(1, 8, 10)));
        stripsByX.put(2, Collections.singletonList(new Strip(2, 0, 2)));
        stripsByX.put(3, Collections.singletonList(new Strip(3, 0, 2)));
        stripsByX.put(4, Arrays.asList(new Strip(4, 0, 2),
                new Strip(4, 8, 10)));
        stripsByX.put(5, Collections.singletonList(new Strip(5, 1, 9)));
        List<Area> areas = sut.join(stripsByX, 0, 6);
        List<Integer> areaSizes = getSortedAreaSizes(areas);
        List<Integer> expected = Arrays.asList(28);
        Assert.assertEquals(expected, areaSizes);
    }

    @Test
    public void connectsLetterWintoOneArea(){
        Map<Integer, List<Strip>> stripsByX =  new HashMap<>(10);
        stripsByX.put(1, Arrays.asList(new Strip(1, 0, 4),
                new Strip(1, 8, 12),
                new Strip(1, 16, 20)));
        stripsByX.put(2, Arrays.asList(new Strip(2, 1, 5),
                new Strip(2, 6, 9),
                new Strip(2, 11, 14),
                new Strip(2, 15, 19)));
        stripsByX.put(3, Arrays.asList(new Strip(3, 4, 8),
                new Strip(3, 12, 16)));
        List<Area> areas = sut.join(stripsByX, 0, 6);
        List<Integer> areaSizes = getSortedAreaSizes(areas);
        List<Integer> expected = Arrays.asList(34);
        Assert.assertEquals(expected, areaSizes);
    }

    private List<Integer> getSortedAreaSizes(List<Area> areas) {
        return areas.stream().
                    map(Area::getAreaInSquareMeters).
                    sorted().
                    collect(Collectors.toList());
    }
}
