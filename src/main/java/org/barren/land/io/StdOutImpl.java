package org.barren.land.io;

public class StdOutImpl implements StdOut{
    @Override
    public void output(String content) {
        System.out.println(content);
    }
}
