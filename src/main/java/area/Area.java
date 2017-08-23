package area;

import coalesce.Connectable;
import lombok.*;

import java.util.Collections;
import java.util.List;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Area implements Connectable {
    @NonNull
    private final List<Strip> strips;
    @NonNull
    private final List<Strip> openBorder;

    public static Area fromStrip(Strip strip){
        List<Strip> oneStrip = Collections.singletonList(strip);
        return new Area(oneStrip, oneStrip);
    }

    @Override
    public boolean canConnect(Connectable other) {
        if(!(other instanceof Strip)){
            return false;
        }
        Strip strip = (Strip)other;
        return openBorder.stream().
                anyMatch(strip::isAdjacentTo);
    }
}
