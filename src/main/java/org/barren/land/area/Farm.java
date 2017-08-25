package org.barren.land.area;

import org.barren.land.joiner.StripJoiner;
import org.barren.land.parser.InputParser;

import java.util.*;
import java.util.stream.Collectors;

public class Farm {
    private final List<Integer> areaSizes;
    private static Rectangle defaultAllArea = new Rectangle(0, 0, 400, 600);

    public Farm(Rectangle allArea, List<Rectangle> barrenLand){
        List<Strip> allStrips = allArea.getStrips();
        Map<Integer, List<Strip>> barrenStripsByX = getBarrenStripsByX(barrenLand);
        Map<Integer, List<Strip>> fertileStripsByX = getFertileStripsByX(allStrips, barrenStripsByX);
        StripJoiner joiner = new StripJoiner();
        List<Area> areas = joiner.join(fertileStripsByX, allArea.getBottomLeftX(), allArea.getTopRightX());
        areaSizes = getSortedAreaSizes(areas);
    }

    public static Farm fromStdinInput(String input){
        InputParser parser = new InputParser();
        List<List<Integer>> barrenLandCoordinates = parser.parseInput(input);
        List<Rectangle> barrenLand = barrenLandCoordinates.stream().
                map(Farm::getRectangle).collect(Collectors.toList());
        return new Farm(defaultAllArea, barrenLand);
    }

    public static Rectangle getRectangle(List<Integer> rectangleCoordinatesFromInput){
        Integer bottomLeftXAsIs = rectangleCoordinatesFromInput.get(0);
        Integer bottomLeftYAsIs = rectangleCoordinatesFromInput.get(1);
        int topRightXAdjusted = rectangleCoordinatesFromInput.get(2) + 1;
        int topRightYAdjusted = rectangleCoordinatesFromInput.get(3) + 1;
        return new Rectangle(bottomLeftXAsIs,
                bottomLeftYAsIs,
                topRightXAdjusted,
                topRightYAdjusted);
    }

    public List<Integer> getAreaSizes() {
        return areaSizes;
    }

    public String getAreaSizesAsString(){
        return areaSizes.stream().map(Object::toString).collect(Collectors.joining(" "));
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
