package org.barren.land.joiner;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@RequiredArgsConstructor
@ToString
public class JoinableToSameKindOrLarge implements Joinable {
    private final int kind;
    private final int size;

    @Override
    public boolean canJoin(Joinable other) {
        if(!(other instanceof JoinableToSameKindOrLarge)){
            return false;
        }
        JoinableToSameKindOrLarge otherConnectableToSameKindOrLarge = (JoinableToSameKindOrLarge)other;
        return kind == otherConnectableToSameKindOrLarge.kind ||
                (isLarge() && otherConnectableToSameKindOrLarge.isLarge());
    }

    private boolean isLarge(){return size>2;}
}
