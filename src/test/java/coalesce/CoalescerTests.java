package coalesce;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class CoalescerTests {
    private final Coalescer coalescer = new Coalescer();

    @Test
    public void keepsUnconnectablesAsIs(){
        List<List<Connectable>> groups = Arrays.asList(
                Collections.singletonList(new Unconnectable(1)),
                Collections.singletonList(new Unconnectable(2))
        );
        String expectedStr = joinGroups(groups);
        List<List<Connectable>> actual = coalescer.coalesce(groups);
        String actualStr = joinGroups(actual);
        Assert.assertEquals(expectedStr, actualStr);
    }

    @Test
    public void coalescesIntoOneGroup(){
        List<List<Connectable>> groups = Arrays.asList(
                Collections.singletonList(new ConnectableToAnything(1)),
                Collections.singletonList(new ConnectableToAnything(2)),
                Collections.singletonList(new ConnectableToAnything(3))
        );
        List<Connectable> allItems = groups.stream().flatMap(List::stream).collect(Collectors.toList());
        List<List<Connectable>> allItemsInOneGroup = Collections.singletonList(allItems);
        String expectedStr = joinGroups(allItemsInOneGroup);
        List<List<Connectable>> actual = coalescer.coalesce(groups);
        String actualStr = joinGroups(actual);
        Assert.assertEquals(expectedStr, actualStr);
    }

    @Test
    public void coalescesIntoTwoGroupsByKind(){
        List<List<Connectable>> groups = Arrays.asList(
                Collections.singletonList(new ConnectableToSameKindOrLarge(1, 1)),
                Collections.singletonList(new ConnectableToSameKindOrLarge(2, 1)),
                Collections.singletonList(new ConnectableToSameKindOrLarge(1, 2)),
                Collections.singletonList(new ConnectableToSameKindOrLarge(2, 2))
        );
        List<List<Connectable>> expected = Arrays.asList(
                Arrays.asList(new ConnectableToSameKindOrLarge(1, 1),
                        new ConnectableToSameKindOrLarge(1, 2)),
                Arrays.asList(new ConnectableToSameKindOrLarge(2, 1),
                        new ConnectableToSameKindOrLarge(2, 2))
        );
        String expectedStr = joinGroups(expected);
        List<List<Connectable>> actual = coalescer.coalesce(groups);
        String actualStr = joinGroups(actual);
        Assert.assertEquals(expectedStr, actualStr);
    }

    @Test
    public void coalescesIntoOneGroupConnectedByLargeItems(){
        List<List<Connectable>> groups = Arrays.asList(
                Collections.singletonList(new ConnectableToSameKindOrLarge(1, 1)),
                Collections.singletonList(new ConnectableToSameKindOrLarge(2, 1)),
                Collections.singletonList(new ConnectableToSameKindOrLarge(1, 2)),
                Collections.singletonList(new ConnectableToSameKindOrLarge(2, 2)),
                Collections.singletonList(new ConnectableToSameKindOrLarge(1, 3)),
                Collections.singletonList(new ConnectableToSameKindOrLarge(2, 3))
        );
        Set<Connectable> allItems = groups.stream().flatMap(List::stream).
                collect(Collectors.toSet());
        List<List<Connectable>> actual = coalescer.coalesce(groups);
        Assert.assertEquals(1, actual.size());
        Assert.assertEquals(allItems, new HashSet<>(actual.get(0)));
    }

    private String joinGroups(List<List<Connectable>> groups) {
        return groups.stream().map(this::joinItems).collect(Collectors.joining("\n"));
    }
    private String joinItems(List<Connectable> items){
        return items.stream().map(Object::toString).collect(Collectors.joining(",\n"));
    }

}
