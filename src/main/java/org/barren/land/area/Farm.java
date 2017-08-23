package org.barren.land.area;

import java.util.*;
import java.util.stream.Collectors;

public class Farm {
    public Farm(Rectangle totalArea, List<Rectangle> barrenLand){
        List<Strip> totalStrips = totalArea.getStrips();
        List<Strip> barrenStrips = barrenLand.stream().
                map(Rectangle::getStrips).
                flatMap(List::stream).
                collect(Collectors.toList());
        Map<Integer, List<Strip>> barrenStripsByX =
                barrenStrips.stream().
                        collect(Collectors.groupingBy(Strip::getX));
    }

    public Map<Integer, List<Strip>> getFertileStrips(List<Strip> totalStrips, Map<Integer, List<Strip>> barrenStripsByX){
        final List<Strip> noBarrenStrips = new ArrayList<>();
        StripSubtractor stripSubtractor = new StripSubtractor();
        Map<Integer, List<Strip>> fertileStripsByX = new HashMap<>(totalStrips.size());
        for(Strip strip : totalStrips){
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
}
