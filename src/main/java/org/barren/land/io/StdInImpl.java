package org.barren.land.io;

import java.util.Scanner;

public class StdInImpl implements StdIn{
    @Override
    public String getAllInput() {
        try(Scanner stdin = new Scanner(System.in)){
            StringBuilder allInput = new StringBuilder();
            while (stdin.hasNextLine()){
                allInput.append(stdin.nextLine());
            }
            return allInput.toString();
        }
    }
}
