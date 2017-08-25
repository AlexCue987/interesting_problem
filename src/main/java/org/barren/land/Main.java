package org.barren.land;

import org.barren.land.io.StdIn;
import org.barren.land.io.StdInImpl;
import org.barren.land.io.StdOut;
import org.barren.land.io.StdOutImpl;

public class Main {
    public static void main(String[] args){
        System.out.println("got it");
        StdIn stdIn = new StdInImpl();
        String input = stdIn.getAllInput();
        StdOut stdOut = new StdOutImpl();
        stdOut.output("processed: " + input);
    }
}
