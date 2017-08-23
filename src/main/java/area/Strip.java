package area;

import lombok.*;

@Data
@EqualsAndHashCode
@ToString
public class Strip {
    private final int x;
    private final int fromY;
    private final int toY;

    public Strip(int x, int fromY, int toY){
        if(fromY >= toY){
            throw new IllegalArgumentException("invalid: fromY=" + fromY +
                    " >= toY=" + toY);
        }
        this.x=x;
        this.fromY=fromY;
        this.toY=toY;
    }

    public int getArea(){return toY-fromY;}
}
