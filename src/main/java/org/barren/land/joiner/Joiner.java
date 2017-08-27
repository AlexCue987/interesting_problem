package org.barren.land.joiner;

import java.util.ArrayList;
import java.util.List;

public class Joiner {
    public List<List<Joinable>> join(List<List<Joinable>> groups){
        List<List<Joinable>> groupsToJoin = getDeepCopy(groups);
        boolean makingProgress = true;
        while(makingProgress) {
            makingProgress = false;
            //because we are joining groups, comparing two groups only once is not enough:
            // we might have added to any of the groups and need to re-compare
            for (int i = 0; i < groupsToJoin.size() - 1; i++) {
                List<Joinable> possibleTarget = groupsToJoin.get(i);
                for (int j = i + 1; j < groupsToJoin.size(); j++) {
                    List<Joinable> possibleSource = groupsToJoin.get(j);
                    if (canJoin(possibleSource, possibleTarget)) {
                        makingProgress = true;
                        possibleTarget.addAll(possibleSource);
                        groupsToJoin.remove(j);
                        //another item might take the deleted one's place
                        //it also needs to be considered
                        j--;
                    }
                }
            }
        }
        return groupsToJoin;
    }

    boolean canJoin(List<Joinable> group1, List<Joinable> group2){
        for(int i=0; i<group1.size(); i++){
            for(int j=0; j<group2.size(); j++){
                if(group1.get(i).canJoin(group2.get(j))){
                    return true;
                }
            }
        }
        return false;
    }

    List<List<Joinable>> getDeepCopy(List<List<Joinable>> groups){
        List<List<Joinable>> ret = new ArrayList<>(groups.size());
        for(List<Joinable> group: groups){
            if(group.size() > 0) {
                ret.add(new ArrayList<>(group));
            }
        }
        return ret;
    }
}
