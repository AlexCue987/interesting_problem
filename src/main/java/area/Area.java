package area;

import lombok.*;

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
}
