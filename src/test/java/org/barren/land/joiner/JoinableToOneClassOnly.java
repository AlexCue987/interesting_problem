package org.barren.land.joiner;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@RequiredArgsConstructor
@ToString
public class JoinableToOneClassOnly implements Joinable {
    private final int i;

    @Override
    public boolean canJoin(Joinable other) {
        return other instanceof JoinableToAnything;
    }
}
