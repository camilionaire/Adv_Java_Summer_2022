package edu.pdx.cs410J.camilo3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class OptionsCheckerTest {

    @Test
    void readmeCanBeReadAsResource() throws IOException {
        try (
                InputStream readme = Project3.class.getResourceAsStream("README.txt")
        ) {
            assertThat(readme, not(nullValue()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
            String line = reader.readLine();
            assertThat(line, containsString("Project 1+: Designing a Phone Bill Application"));
        }
    }

    /**
     * These 3 tests are for the ReadMe test on the arg strings.
     */
    @Test
    void stringOfArgsWithOptionOfReadmeTrueSingleOption() {

        OptionsChecker optCh = new OptionsChecker();
        ArrayList argList = new ArrayList<>(Arrays.asList("-README"));

        assertEquals(optCh.checkForReadme(argList), true);
    }
    @Test
    void stringOfArgsWithOptionOfReadmeTrueMultipleOptions() {
        String[] test = {"-print", "-hello-world", "-README", "more things"};
        OptionsChecker optCh = new OptionsChecker();
        ArrayList argList = new ArrayList<> (Arrays.asList("-print", "-hello-world", "-README", "more things"));

        assertEquals(optCh.checkForReadme(argList), true);
    }

    @Test
    void stringOfArgsWithOptionOfReadmeTrueWithTextFile() {
        String[] test = {"-print", "-textFile", "yowza.txt", "-hello-world", "-README", "more things"};
        OptionsChecker optCh = new OptionsChecker();
        ArrayList argList = new ArrayList<> (Arrays.asList("-print", "-hello-world", "-README", "more things"));

        assertEquals(optCh.checkForReadme(argList), true);
    }

    @Test
    void stringOfArgsWithOptionOfReadmeFalseMultipleOptions() {
        OptionsChecker optCh = new OptionsChecker();
        ArrayList argList = new ArrayList<>(Arrays.asList("-print", "-hello-world", "Robert Paulson", "-README"));

        assertEquals(optCh.checkForReadme(argList), false);
    }

    /**
     * These next few check the checkForPrint paths
     */
    @Test
    void checkForPrintTrue() {
        OptionsChecker optCh = new OptionsChecker();
        ArrayList argList = new ArrayList<>(Arrays.asList("-print", "-first"));

        assertEquals(optCh.checkForPrint(argList), true);
    }

    @Test
    void checkForPrintTrueAfter() {
        OptionsChecker optCh = new OptionsChecker();
        ArrayList argList = new ArrayList<>(Arrays.asList("-first", "-print"));

        assertEquals(optCh.checkForPrint(argList), true);
    }

    @Test
    void checkForPrintFalseZero() {
        OptionsChecker optCh = new OptionsChecker();
        ArrayList argList = new ArrayList<>();

        assertEquals(optCh.checkForPrint(argList), false);
    }

    @Test
    void checkForPrintFalseOutOfOptions() {
        OptionsChecker optCh = new OptionsChecker();
        ArrayList argList = new ArrayList<>(Arrays.asList("-first", "-second", "third"));

        assertEquals(optCh.checkForPrint(argList), false);
    }

    /**
     * These next couple test out the check for text file in the options
     * @throws OptionsChecker.MissingFileName
     */
    @Test
    void checkForTextFlagTrue() throws OptionsChecker.MissingFileName {
        OptionsChecker optCh = new OptionsChecker();
        String[] test = {"-print", "-textFile", "yowza.txt", "-hello-world", "-README", "more things"};
        ArrayList argList = new ArrayList<>(Arrays.asList(test));
        ArrayList compare = new ArrayList(Arrays.asList(new String[]{"-print", "-hello-world", "-README", "more things"}));

        String yowza = optCh.checkForTextFile(argList);

        assertEquals(yowza, "yowza.txt");

        assertEquals(compare, argList);
    }

    @Test
    void checkForTextFlagError() throws OptionsChecker.MissingFileName {
        OptionsChecker optCh = new OptionsChecker();
        String[] test = {"-print", "-textFile", "-hello-world", "-README", "more things"};
        ArrayList argList = new ArrayList<>(Arrays.asList(test));

        Exception exception = assertThrows(OptionsChecker.MissingFileName.class, () -> {
            optCh.checkForTextFile(argList);
        });
        assertTrue(exception.getMessage().contains("IT LOOKS LIKE YOU'RE MISSING A FILENAME."));
    }
}
