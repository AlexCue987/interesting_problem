package org.barren.land.area;

import org.barren.land.joiner.Joinable;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Area implements Joinable {
    @NonNull
    private final List<Strip> strips;
    @NonNull
    private final List<Strip> openBorder;

    public static Area fromStrip(Strip strip){
        List<Strip> oneStrip = Collections.singletonList(strip);
        return new Area(oneStrip, oneStrip);
    }

    public static Area fromAreasAndStrips(List<Joinable> areasAndStrips){
        List<Strip> strips = new ArrayList<>();
        List<Strip> openBorder = new ArrayList<>();
        for(Joinable joinable : areasAndStrips){
            if(joinable instanceof Area){
                Area area = (Area) joinable;
                strips.addAll(area.getStrips());
                continue;
            }
            Strip strip = (Strip) joinable;
            strips.add(strip);
            openBorder.add(strip);
        }
        return new Area(strips, openBorder);
    }

    @Override
    public boolean canJoin(Joinable other) {
        if(!(other instanceof Strip)){
            return false;
        }
        Strip strip = (Strip)other;
        return openBorder.stream().
                anyMatch(strip::isAdjacentTo);
    }

    public int getAreaInSquareMeters(){
        return strips.stream().
                map(Strip::getArea).
                mapToInt(Integer::intValue).sum();
    }
}
