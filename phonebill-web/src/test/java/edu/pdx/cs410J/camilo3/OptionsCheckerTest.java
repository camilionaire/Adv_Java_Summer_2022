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
                InputStream readme = Project4.class.getResourceAsStream("README.txt")
        ) {
            assertThat(readme, not(nullValue()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
            String line = reader.readLine();
            assertThat(line, containsString("Project 4: Designing a Phone-Bill-Web Application"));
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
        OptionsChecker optCh = new OptionsChecker();
        ArrayList<String> argList = new ArrayList<> (Arrays.asList("-print", "-hello-world", "-README", "more things"));

        assertEquals(optCh.checkForReadme(argList), true);
    }

    @Test
    void stringOfArgsWithOptionOfReadmeTrueWithHost() {
        String[] test = {"-print", "-host", "yowzahost", "-hello-world", "-README", "more things"};
        OptionsChecker optCh = new OptionsChecker();
        ArrayList argList = new ArrayList<> (Arrays.asList("-print", "-host", "yowzahost",
                "-hello-world", "-README", "more things"));

        assertEquals(optCh.checkForReadme(argList), true);
    }

    @Test
    void stringOfArgsWithOptionOfReadmeFalseMultipleOptions() {
        OptionsChecker optCh = new OptionsChecker();
        ArrayList argList = new ArrayList<>(Arrays.asList("-print", "-hello-world", "Robert Paulson", "-README"));

        assertEquals(optCh.checkForReadme(argList), false);
    }

    /**
     * These next few check the checkForHost paths
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
    void checkForPrintTrueAfterEverything() {
        OptionsChecker optCh = new OptionsChecker();
        ArrayList argList = new ArrayList<>(Arrays.asList(
                "-first", "-host", "aHosthost", "-port", "8675", "-print"));

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
     * @throws OptionsChecker.MissingName
     */
    @Test
    void checkForHostTrue() throws OptionsChecker.MissingName {
        OptionsChecker optCh = new OptionsChecker();
        String[] test = {"-print", "-host", "outOfTownHost", "-hello-world", "-README", "more things"};
        ArrayList argList = new ArrayList<>(Arrays.asList(test));
        ArrayList compare = new ArrayList(Arrays.asList(new String[]{"-print", "-hello-world", "-README", "more things"}));

        String host = optCh.checkForHost(argList);

        assertEquals(host, "outOfTownHost");

        assertEquals(compare, argList);
    }

    @Test
    void checkForHostTrueAfterPort() throws OptionsChecker.MissingName {
        OptionsChecker optCh = new OptionsChecker();
        String[] test = {"-print", "-port", "9876", "-host", "partyHost", "-hello-world", "-README", "more things"};
        ArrayList argList = new ArrayList<>(Arrays.asList(test));
        ArrayList compare = new ArrayList(Arrays.asList(new String[]{"-print", "-port", "9876", "-hello-world", "-README", "more things"}));

        String host = optCh.checkForHost(argList);

        assertEquals(host, "partyHost");

        assertEquals(compare, argList);
    }

    @Test
    void checkForHostError() throws OptionsChecker.MissingName {
        OptionsChecker optCh = new OptionsChecker();
        String[] test = {"-print", "-host", "-hello-world", "-README", "more things"};
        ArrayList argList = new ArrayList<>(Arrays.asList(test));

        Exception exception = assertThrows(OptionsChecker.MissingName.class, () -> {
            optCh.checkForHost(argList);
        });
        assertTrue(exception.getMessage().contains("IT LOOKS LIKE YOU'RE MISSING A HOSTNAME."));
    }

    @Test
    void checkForHostErrorLastArg() throws OptionsChecker.MissingName {
        OptionsChecker optCh = new OptionsChecker();
        String[] test = {"-print", "-some-option", "-host"};
        ArrayList argList = new ArrayList<>(Arrays.asList(test));

        Exception exception = assertThrows(OptionsChecker.MissingName.class, () -> {
            optCh.checkForHost(argList);
        });
        assertTrue(exception.getMessage().contains("IT LOOKS LIKE YOU'RE MISSING A HOSTNAME."));
    }

    /**
     * These next couple test out the check for port in the options
     * basically just a copy replace of the Host tests above.
     * @throws OptionsChecker.MissingName
     */
    @Test
    void checkForPortFlagTrue() throws OptionsChecker.MissingName {
        OptionsChecker optCh = new OptionsChecker();
        String[] test = {"-print", "-port", "9090", "-hello-world", "-README", "more things"};
        ArrayList argList = new ArrayList<>(Arrays.asList(test));
        ArrayList compare = new ArrayList(Arrays.asList(new String[]{"-print", "-hello-world", "-README", "more things"}));

        String port = optCh.checkForPort(argList);

        assertEquals(port, "9090");

        assertEquals(compare, argList);
    }

    @Test
    void checkForPortFlagTrueAfterTextFile() throws OptionsChecker.MissingName {
        OptionsChecker optCh = new OptionsChecker();
        String[] test = {"-print", "-host", "localhost", "-port", "5309", "-hello-world", "-README", "more things"};
        ArrayList argList = new ArrayList<>(Arrays.asList(test));
        ArrayList compare = new ArrayList(Arrays.asList(new String[]{"-print", "-host", "localhost", "-hello-world", "-README", "more things"}));

        String port = optCh.checkForPort(argList);

        assertEquals(port, "5309");

        assertEquals(compare, argList);
    }

    @Test
    void checkForPortFlagError() throws OptionsChecker.MissingName {
        OptionsChecker optCh = new OptionsChecker();
        String[] test = {"-print", "-port", "-hello-world", "-README", "more things"};
        ArrayList argList = new ArrayList<>(Arrays.asList(test));

        Exception exception = assertThrows(OptionsChecker.MissingName.class, () -> {
            optCh.checkForPort(argList);
        });
        assertTrue(exception.getMessage().contains("IT LOOKS LIKE YOU'RE MISSING A PORT."));
    }

    @Test
    void checkForPortLastArgError() throws OptionsChecker.MissingName {
        OptionsChecker optCh = new OptionsChecker();
        String[] test = {"-print", "-port"};
        ArrayList argList = new ArrayList<>(Arrays.asList(test));

        Exception exception = assertThrows(OptionsChecker.MissingName.class, () -> {
            optCh.checkForPort(argList);
        });
        assertTrue(exception.getMessage().contains("IT LOOKS LIKE YOU'RE MISSING A PORT."));
    }
} // end of OptionsCheckerTest class
