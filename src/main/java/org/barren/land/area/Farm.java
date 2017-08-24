package org.barren.land.area;

import org.barren.land.joiner.StripJoiner;

import java.util.*;
import java.util.stream.Collectors;

public class Farm {
    private final List<Integer> areaSizes;

    public Farm(Rectangle allArea, List<Rectangle> barrenLand){
        List<Strip> allStrips = allArea.getStrips();
        Map<Integer, List<Strip>> barrenStripsByX = getBarrenStripsByX(barrenLand);
        Map<Integer, List<Strip>> fertileStripsByX = getFertileStripsByX(allStrips, barrenStripsByX);
        StripJoiner joiner = new StripJoiner();
        List<Area> areas = joiner.join(fertileStripsByX, allArea.getBottomLeftX(), allArea.getTopRightX());
        areaSizes = getSortedAreaSizes(areas);
    }

    public List<Integer> getAreaSizes() {
        return areaSizes;
    }

    static Map<Integer, List<Strip>> getBarrenStripsByX(List<Rectangle> barrenLand) {
        List<Strip> barrenStrips = barrenLand.stream().
                map(Rectangle::getStrips).
                flatMap(List::stream).
                collect(Collectors.toList());
        return barrenStrips.stream().
                collect(Collectors.groupingBy(Strip::getX));
    }

    static Map<Integer, List<Strip>> getFertileStripsByX(List<Strip> allStrips, Map<Integer, List<Strip>> barrenStripsByX){
        final List<Strip> noBarrenStrips = new ArrayList<>();
        StripSubtractor stripSubtractor = new StripSubtractor();
        Map<Integer, List<Strip>> fertileStripsByX = new HashMap<>(allStrips.size());
        for(Strip strip : allStrips){
            List<Strip> fertileStrips = Collections.singletonList(strip);
            List<Strip> barrenStripsForX = barrenStripsByX.getOrDefault(strip.getX(), noBarrenStrips);
            for(Strip barrenStrip : barrenStripsForX){
                fertileStrips = stripSubtractor.subtractFromList(fertileStrips, barrenStrip);
            }
            if(fertileStrips.size()>0){
                fertileStripsByX.put(strip.getX(), fertileStrips);
            }
        }
        return fertileStripsByX;
    }

    private List<Integer> getSortedAreaSizes(List<Area> areas) {
        return areas.stream().
                map(Area::getAreaInSquareMeters).
                sorted().
                collect(Collectors.toList());
    }
}
