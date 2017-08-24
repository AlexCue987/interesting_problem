package org.barren.land.area;

import org.barren.land.joiner.Joinable;
import lombok.*;

@Data
@EqualsAndHashCode
@ToString
public class Strip implements Joinable {
    private final int x;
    private final int fromY;
    private final int toY;

    public Strip(int x, int fromY, int toY){
        validateInput(fromY, toY);
        this.x=x;
        this.fromY=fromY;
        this.toY=toY;
    }

    private void validateInput(int fromY, int toY) {
        if(fromY >= toY){
            throw new IllegalArgumentException("invalid: fromY=" + fromY +
                    " >= toY=" + toY);
        }
    }

    public int getArea(){return toY-fromY;}

    public boolean isAdjacentTo(Strip other){
        return(x == other.x+1 || x == other.x-1) &&
                !(toY<=other.fromY || other.toY<=fromY);
    }

    @Override
    public boolean canJoin(Joinable other) {
        if(!(other instanceof Area)){
            return false;
        }
        return other.canJoin(this);
    }
}
