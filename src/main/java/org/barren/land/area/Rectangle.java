package org.barren.land.area;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
@EqualsAndHashCode
@ToString
public class Rectangle {
    private final int bottomLeftX;
    private final int bottomLeftY;
    private final int topRightX;
    private final int topRightY;

    public Rectangle(int bottomLeftX, int bottomLeftY,
                     int topRightX, int topRightY){
        validateInput(bottomLeftX, bottomLeftY, topRightX, topRightY);
        this.bottomLeftX = bottomLeftX;
        this.topRightX = topRightX;
        this.bottomLeftY = bottomLeftY;
        this.topRightY =  topRightY;
    }

    private void validateInput(int bottomLeftX, int bottomLeftY, int topRightX, int topRightY) {
        validateRange(bottomLeftX, topRightX, "(bottomLeftX, topRightX)");
        validateRange(bottomLeftY, topRightY, "(bottomLeftY, topRightY)");
    }

    private void validateRange(int x1, int x2, String description){
        if(x1 >= x2){
            throw new IllegalArgumentException("invalid pair" + description + ": " + x1 +
                    " >= " + x2);
        }
    }

    public List<Strip> getStrips(){
        return IntStream.range(bottomLeftX, topRightX).boxed().
                map(x -> new Strip(x, bottomLeftY, topRightY)).
                collect(Collectors.toList());
    }
//
//    public boolean isInsideOf(Rectangle outer){
//        return bottomLeftX>=outer.bottomLeftX &&
//                bottomLeftY>=outer.bottomLeftY &&
//                topRightX<=outer.topRightX &&
//                topRightY<=outer.topRightY;
//    }
}
