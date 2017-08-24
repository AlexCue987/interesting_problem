package org.barren.land.joiner;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class JoinerTests {
    private final Joiner joiner = new Joiner();

    @Test
    public void keepsUnconnectablesAsIs(){
        List<List<Joinable>> groups = Arrays.asList(
                Collections.singletonList(new Unjoinable(1)),
                Collections.singletonList(new Unjoinable(2))
        );
        String expectedStr = joinGroups(groups);
        List<List<Joinable>> actual = joiner.join(groups);
        String actualStr = joinGroups(actual);
        Assert.assertEquals(expectedStr, actualStr);
    }

    @Test
    public void removesEmptyGroups(){
        List<List<Joinable>> groups = Arrays.asList(
                Collections.emptyList(),
                Collections.singletonList(new Unjoinable(1)),
                Collections.emptyList(),
                Collections.singletonList(new Unjoinable(2)),
                Collections.emptyList()
        );
        List<List<Joinable>> actual = joiner.join(groups);
        groups = groups.stream().filter(t ->t.size()>0).collect(Collectors.toList());
        String expectedStr = joinGroups(groups);
        String actualStr = joinGroups(actual);
        Assert.assertEquals(expectedStr, actualStr);
    }

    @Test
    public void coalescesIntoOneGroup(){
        List<List<Joinable>> groups = Arrays.asList(
                Collections.singletonList(new JoinableToAnything(1)),
                Collections.singletonList(new JoinableToAnything(2)),
                Collections.singletonList(new JoinableToAnything(3))
        );
        List<Joinable> allItems = groups.stream().flatMap(List::stream).collect(Collectors.toList());
        List<List<Joinable>> allItemsInOneGroup = Collections.singletonList(allItems);
        String expectedStr = joinGroups(allItemsInOneGroup);
        List<List<Joinable>> actual = joiner.join(groups);
        String actualStr = joinGroups(actual);
        Assert.assertEquals(expectedStr, actualStr);
    }

    @Test
    public void coalescesIntoOneGroup_becauseOneCanConnectToAnything(){
        List<List<Joinable>> groups = Arrays.asList(
                Collections.singletonList(new JoinableToOneClassOnly(1)),
                Collections.singletonList(new JoinableToOneClassOnly(2)),
                Collections.singletonList(new JoinableToOneClassOnly(3)),
                Collections.singletonList(new JoinableToOneClassOnly(4)),
                Collections.singletonList(new JoinableToAnything(5))
        );
        List<Joinable> allItems = groups.stream().flatMap(List::stream).collect(Collectors.toList());
        List<List<Joinable>> actual = joiner.join(groups);
        Assert.assertEquals(1, actual.size());
        for(Joinable joinable : allItems){
            Assert.assertTrue(actual.get(0).contains(joinable));
        }
    }

    @Test
    public void coalescesIntoTwoGroupsByKind(){
        List<List<Joinable>> groups = Arrays.asList(
                Collections.singletonList(new JoinableToSameKindOrLarge(1, 1)),
                Collections.singletonList(new JoinableToSameKindOrLarge(2, 1)),
                Collections.singletonList(new JoinableToSameKindOrLarge(1, 2)),
                Collections.singletonList(new JoinableToSameKindOrLarge(2, 2))
        );
        List<List<Joinable>> expected = Arrays.asList(
                Arrays.asList(new JoinableToSameKindOrLarge(1, 1),
                        new JoinableToSameKindOrLarge(1, 2)),
                Arrays.asList(new JoinableToSameKindOrLarge(2, 1),
                        new JoinableToSameKindOrLarge(2, 2))
        );
        String expectedStr = joinGroups(expected);
        List<List<Joinable>> actual = joiner.join(groups);
        String actualStr = joinGroups(actual);
        Assert.assertEquals(expectedStr, actualStr);
    }

    @Test
    public void coalescesIntoOneGroupConnectedByLargeItems(){
        List<List<Joinable>> groups = Arrays.asList(
                Collections.singletonList(new JoinableToSameKindOrLarge(1, 1)),
                Collections.singletonList(new JoinableToSameKindOrLarge(2, 1)),
                Collections.singletonList(new JoinableToSameKindOrLarge(1, 2)),
                Collections.singletonList(new JoinableToSameKindOrLarge(2, 2)),
                Collections.singletonList(new JoinableToSameKindOrLarge(1, 3)),
                Collections.singletonList(new JoinableToSameKindOrLarge(2, 3))
        );
        Set<Joinable> allItems = groups.stream().flatMap(List::stream).
                collect(Collectors.toSet());
        List<List<Joinable>> actual = joiner.join(groups);
        Assert.assertEquals(1, actual.size());
        Assert.assertEquals(allItems, new HashSet<>(actual.get(0)));
    }

    private String joinGroups(List<List<Joinable>> groups) {
        return groups.stream().map(this::joinItems).collect(Collectors.joining("\n"));
    }

    private String joinItems(List<Joinable> items){
        return items.stream().map(Object::toString).collect(Collectors.joining(",\n"));
    }
}
