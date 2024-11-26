package org.example;

public class Calc {

    public static int run(String expr) {
        return typeCalc(expr);
    }

    private static int typeCalc(String expr) {
        if (expr.contains(" + ")) {
            String[] split = expr.split(" \\+ ");
            return Integer.parseInt(split[0]) + Integer.parseInt(split[1]);
        }
        return 0;
    }
}
