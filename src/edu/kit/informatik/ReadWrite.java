package edu.kit.informatik;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This is a replacement for a class I am for some reason not allowed to share ;(
 */
public final class ReadWrite {
    
    private static final BufferedReader INPUT = new BufferedReader(new InputStreamReader(System.in));

    public static void writeError(final String message) {
        ReadWrite.writeLine("Error, " + message);
    }

    public static void writeLine(final Object object) {
        System.out.println(object);
    }

    public static String readLine() throws IOException {
            return INPUT.readLine();
    }
    
}
