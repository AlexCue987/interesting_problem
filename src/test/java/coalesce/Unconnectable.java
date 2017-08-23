package coalesce;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@RequiredArgsConstructor
@ToString
public class Unconnectable implements Connectable{
    private final int i;

    @Override
    public boolean canConnect(Connectable other) {
        return false;
    }

}
