package coalesce;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@RequiredArgsConstructor
@ToString
public class ConnectableToOneClassOnly implements Connectable {
    private final int i;

    @Override
    public boolean canConnect(Connectable other) {
        return other instanceof ConnectableToAnything;
    }
}
