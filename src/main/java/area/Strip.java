package area;

import lombok.*;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Strip {
    private final int x;
    private final int fromY;
    private final int toY;

    public int getArea(){return toY-fromY;}
}
