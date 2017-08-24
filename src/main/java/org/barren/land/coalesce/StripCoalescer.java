package org.barren.land.coalesce;

import org.barren.land.area.Area;
import org.barren.land.area.Strip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StripCoalescer {
    public List<Area> coalesce(Map<Integer, List<Strip>> stripsByX, int fromX, int toX){
        List<Strip> noStrips = Collections.emptyList();
        List<Area> openAreas = new ArrayList<>();
        List<Area> closedAreas = new ArrayList<>();
        Coalescer coalescer = new Coalescer();
        for(int x=fromX; x<toX; x++){
            List<Strip> stripsOnRow = stripsByX.getOrDefault(x, noStrips);
            List<List<Connectable>> toConnect = getItemsToConnect(openAreas, stripsOnRow);
            List<List<Connectable>> connectedGroups = coalescer.coalesce(toConnect);
            openAreas.clear();
            for(List<Connectable> connectedGroup : connectedGroups){
                if(connectedGroup.size() == 1){
                    Connectable soleItem = connectedGroup.get(0);
                    if(soleItem instanceof Area){
                        closedAreas.add((Area)soleItem);
                    }else if(soleItem instanceof Strip){
                        openAreas.add(Area.fromStrip((Strip)soleItem));
                    }
                }else{
                    Area openArea = Area.fromAreasAndStrips(connectedGroup);
                    openAreas.add(openArea);
                }
            }
        }
        closedAreas.addAll(openAreas);
        return closedAreas;
    }

    private List<List<Connectable>> getItemsToConnect(List<Area> openAreas, List<Strip> stripsOnRow) {
        List<List<Connectable>> areasToConnect = openAreas.stream().
                map(area -> Collections.singletonList((Connectable)area)).
                collect(Collectors.toList());
        List<List<Connectable>> stripsToConnect = stripsOnRow.stream().
                map(area ->Collections.singletonList((Connectable)area)).
                collect(Collectors.toList());
        return Stream.concat(areasToConnect.stream(),
                stripsToConnect.stream())
                .collect(Collectors.toList());
    }
}
