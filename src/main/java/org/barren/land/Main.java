package org.barren.land;

import org.barren.land.area.Farm;
import org.barren.land.io.StdIn;
import org.barren.land.io.StdInImpl;
import org.barren.land.io.StdOut;
import org.barren.land.io.StdOutImpl;

public class Main {
    public static void main(String[] args){
        try {
            String input = getInputFromStdin();
            Farm farm = Farm.fromStdinInput(input);
            String output = farm.getAreaSizesAsString();
            printOutputToStdout(output);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void printOutputToStdout(String output) {
        StdOut stdOut = new StdOutImpl();
        stdOut.output(output);
    }

    private static String getInputFromStdin() {
        StdIn stdIn = new StdInImpl();
        return stdIn.getAllInput();
    }
}
