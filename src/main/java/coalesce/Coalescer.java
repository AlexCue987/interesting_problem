package coalesce;

import java.util.ArrayList;
import java.util.List;

public class Coalescer {
    public List<List<Connectable>> coalesce(List<List<Connectable>> groups){
        List<List<Connectable>> groupsToCoalesce = getDeepCopy(groups);
        boolean makingProgress = true;
        while(makingProgress) {
            makingProgress = false;
            for (int i = 0; i < groupsToCoalesce.size() - 1; i++) {
                List<Connectable> possibleTarget = groupsToCoalesce.get(i);
                for (int j = i + 1; j < groupsToCoalesce.size(); j++) {
                    List<Connectable> possibleSource = groupsToCoalesce.get(j);
                    if (canConnect(possibleSource, possibleTarget)) {
                        makingProgress = true;
                        possibleTarget.addAll(possibleSource);
                        groupsToCoalesce.remove(j);
                        //another item might take the deleted one's place
                        //it also needs to be considered
                        j--;
                    }
                }
            }
        }
        return groupsToCoalesce;
    }

    boolean canConnect(List<Connectable> group1, List<Connectable> group2){
        //nested loop is simpler than streaming alternatives
        for(int i=0; i<group1.size(); i++){
            for(int j=0; j<group2.size(); j++){
                if(group1.get(i).canConnect(group2.get(j))){
                    return true;
                }
            }
        }
        return false;
    }

    List<List<Connectable>> getDeepCopy(List<List<Connectable>> groups){
        List<List<Connectable>> ret = new ArrayList<>(groups.size());
        for(List<Connectable> group: groups){
            ret.add(new ArrayList<>(group));
        }
        return ret;
    }
}
