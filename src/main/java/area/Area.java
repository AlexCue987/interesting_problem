package area;

import lombok.*;

import java.util.Collections;
import java.util.List;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Area {
    @NonNull
    private final List<Strip> strips;
    @NonNull
    private final List<Strip> openBorder;

    public static Area fromStrip(Strip strip){
        List<Strip> oneStrip = Collections.singletonList(strip);
        return new Area(oneStrip, oneStrip);
    }
}
