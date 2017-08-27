package org.barren.land.joiner;

import org.barren.land.area.Area;
import org.barren.land.area.Strip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StripJoiner {
    public List<Area> join(Map<Integer, List<Strip>> stripsByX, int fromX, int toX){
        List<Strip> noStrips = Collections.emptyList();
        List<Area> openAreas = new ArrayList<>();
        List<Area> closedAreas = new ArrayList<>();
        Joiner joiner = new Joiner();
        for(int rowNumber=fromX; rowNumber<toX; rowNumber++){
            List<Strip> stripsOnRow = stripsByX.getOrDefault(rowNumber, noStrips);
            List<List<Joinable>> areasAndStripsToJoin = getItemsToJoin(openAreas, stripsOnRow);
            List<List<Joinable>> joinedGroups = joiner.join(areasAndStripsToJoin);
            openAreas.clear();
            for(List<Joinable> joinedGroup : joinedGroups){
                if(isGroupOfOneItem(joinedGroup)){
                    Joinable soleItem = joinedGroup.get(0);
                    if(isArea(soleItem)){
                        closedAreas.add((Area)soleItem);
                    }else if(isStrip(soleItem)){
                        openAreas.add(Area.fromStrip((Strip)soleItem));
                    }
                }else{
                    Area openArea = Area.fromAreasAndStrips(joinedGroup);
                    openAreas.add(openArea);
                }
            }
        }
        closedAreas.addAll(openAreas);
        return closedAreas;
    }

    private boolean isGroupOfOneItem(List<Joinable> connectedGroup) {
        return connectedGroup.size() == 1;
    }

    private boolean isStrip(Joinable soleItem) {
        return soleItem instanceof Strip;
    }

    private boolean isArea(Joinable soleItem) {
        return soleItem instanceof Area;
    }

    private List<List<Joinable>> getItemsToJoin(List<Area> openAreas, List<Strip> stripsOnRow) {
        List<List<Joinable>> areasToJoin = openAreas.stream().
                map(area -> Collections.singletonList((Joinable)area)).
                collect(Collectors.toList());
        List<List<Joinable>> stripsToJoin = stripsOnRow.stream().
                map(area ->Collections.singletonList((Joinable)area)).
                collect(Collectors.toList());
        return Stream.concat(areasToJoin.stream(),
                stripsToJoin.stream())
                .collect(Collectors.toList());
    }
}
