package org.barren.land.area;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StripSubtractor {
    public List<Strip> subtract(Strip minuend, Strip subtrahend){
        if(stripsDoNotOverlap(minuend, subtrahend)){
            return Collections.singletonList(minuend);
        }
        List<Strip> ret = new ArrayList<Strip>(2);
        if(minuend.getFromY() < subtrahend.getFromY()){
            ret.add(new Strip(minuend.getX(), minuend.getFromY(), subtrahend.getFromY()));
        }
        if(minuend.getToY() > subtrahend.getToY()){
            ret.add(new Strip(minuend.getX(), subtrahend.getToY(), minuend.getToY()));
        }
        return ret;
    }

    private boolean stripsDoNotOverlap(Strip minuend, Strip subtrahend) {
        return minuend.getX() != subtrahend.getX() ||
                minuend.getToY() <= subtrahend.getFromY() ||
                minuend.getFromY() >= subtrahend.getToY();
    }
}
