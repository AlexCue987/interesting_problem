package coalesce;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@RequiredArgsConstructor
@ToString
public class ConnectableToSameKindOrLarge implements Connectable{
    private final int kind;
    private final int size;

    @Override
    public boolean canConnect(Connectable other) {
        if(!(other instanceof ConnectableToSameKindOrLarge)){
            return false;
        }
        ConnectableToSameKindOrLarge otherConnectableToSameKindOrLarge = (ConnectableToSameKindOrLarge)other;
        return kind == otherConnectableToSameKindOrLarge.kind ||
                (isLarge() && otherConnectableToSameKindOrLarge.isLarge());
    }

    private boolean isLarge(){return size>2;}
}
