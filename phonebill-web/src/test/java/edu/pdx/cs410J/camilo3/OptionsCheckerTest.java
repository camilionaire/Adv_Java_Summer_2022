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
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class OptionsCheckerTest {
    @Test
    void test01ReadmeCanBeReadAsResource() throws IOException {
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
     * These 4 tests are for the ReadMe test on the arg strings.
     */
    @Test
    void test02StringOfArgsWithOptionOfReadmeTrueSingleOption() {
        ArrayList<String> argList = new ArrayList<>(List.of("-README"));
        assertTrue(OptionsChecker.checkForReadme(argList));
    }
    @Test
    void test03stringOfArgsWithOptionOfReadmeTrueMultipleOptions() {
        ArrayList<String> argList = new ArrayList<> (Arrays.asList(
                "-print", "-hello-world", "-README", "more things"));
        assertTrue(OptionsChecker.checkForReadme(argList));
    }

    @Test
    void test04StringOfArgsWithOptionOfReadmeTrueWithHost() {
        ArrayList<String> argList = new ArrayList<> (Arrays.asList("-print", "-host", "yowzahost",
                "-hello-world", "-README", "more things"));
        assertTrue(OptionsChecker.checkForReadme(argList));
    }

    @Test
    void test05StringOfArgsWithOptionOfReadmeFalseMultipleOptions() {
        ArrayList<String> argList = new ArrayList<>(Arrays.asList("-print", "-hello-world", "Robert Paulson", "-README"));
        assertFalse(OptionsChecker.checkForReadme(argList));
    }

    /**
     * These next few check the checkForPrint paths
     */
    @Test
    void test06CheckForPrintTrue() {
        ArrayList<String> argList = new ArrayList<>(Arrays.asList("-print", "-first"));
        assertTrue(OptionsChecker.checkForPrint(argList));
    }

    @Test
    void test07CheckForPrintTrueAfter() {
        ArrayList<String> argList = new ArrayList<>(Arrays.asList("-first", "-print"));
        assertTrue(OptionsChecker.checkForPrint(argList));
    }

    @Test
    void test08CheckForPrintTrueAfterEverything() {
        ArrayList<String> argList = new ArrayList<>(Arrays.asList(
                "-first", "-host", "aHosthost", "-port", "8675", "-print"));
        assertTrue(OptionsChecker.checkForPrint(argList));
    }

    @Test
    void test09CheckForPrintFalseZero() {
        assertFalse(OptionsChecker.checkForPrint(new ArrayList<>()));
    }

    @Test
    void test10CheckForPrintFalseOutOfOptions() {
        ArrayList<String> argList = new ArrayList<>(Arrays.asList("-first", "-second", "third"));

        assertFalse(OptionsChecker.checkForPrint(argList));
    }

    /**
     * These next few check the checkForSearch paths
     */
    @Test
    void test11CheckForSearchTrue() {
        ArrayList<String> argList = new ArrayList<>(Arrays.asList("-search", "-first"));

        assertTrue(OptionsChecker.checkForSearch(argList));
    }

    @Test
    void test12CheckForSearchTrueAfter() {
        ArrayList<String> argList = new ArrayList<>(Arrays.asList("-first", "-search"));

        assertTrue(OptionsChecker.checkForSearch(argList));
    }

    @Test
    void test13heckForSearchTrueAfterEverything() {
        ArrayList<String> argList = new ArrayList<>(Arrays.asList(
                "-first", "-host", "aHosthost", "-port", "8675", "-search"));

        assertTrue(OptionsChecker.checkForSearch(argList));
    }

    @Test
    void test14CheckForSearchFalseZero() {
        assertFalse(OptionsChecker.checkForSearch(new ArrayList<>()));
    }

    @Test
    void test15CheckForSearchFalseOutOfOptions() {
        ArrayList<String> argList = new ArrayList<>(Arrays.asList("-first", "-second", "third"));

        assertFalse(OptionsChecker.checkForSearch(argList));
    }

    /**
     * These next couple test out the check for host in the options
     * @throws OptionsChecker.MissingName says we're missing a name
     */
    @Test
    void test16CheckForHostTrue() throws OptionsChecker.MissingName {
        String[] test = {"-print", "-host", "outOfTownHost", "-hello-world", "-README", "more things"};
        ArrayList<String> argList = new ArrayList<>(Arrays.asList(test));
        ArrayList<String> compare = new ArrayList<>(Arrays.asList("-print", "-hello-world", "-README", "more things"));

        String host = OptionsChecker.checkForHost(argList);

        assertEquals(host, "outOfTownHost");

        assertEquals(compare, argList);
    }

    @Test
    void test17CheckForHostTrueAfterPort() throws OptionsChecker.MissingName {
        String[] test = {"-print", "-port", "9876", "-host", "partyHost", "-hello-world", "-README", "more things"};
        ArrayList<String> argList = new ArrayList<>(Arrays.asList(test));
        ArrayList<String> compare = new ArrayList<>(Arrays.asList("-print", "-port", "9876", "-hello-world", "-README", "more things"));

        String host = OptionsChecker.checkForHost(argList);

        assertEquals(host, "partyHost");

        assertEquals(compare, argList);
    }

    @Test
    void test18CheckForHostError() {
        String[] test = {"-print", "-host", "-hello-world", "-README", "more things"};
        ArrayList<String> argList = new ArrayList<>(Arrays.asList(test));

        Exception exception = assertThrows(OptionsChecker.MissingName.class, () ->
                OptionsChecker.checkForHost(argList));
        assertTrue(exception.getMessage().contains("IT LOOKS LIKE YOU'RE MISSING A HOSTNAME."));
    }

    @Test
    void test19CheckForHostErrorLastArg() {
        String[] test = {"-print", "-some-option", "-host"};
        ArrayList<String> argList = new ArrayList<>(Arrays.asList(test));

        Exception exception = assertThrows(OptionsChecker.MissingName.class, () ->
                OptionsChecker.checkForHost(argList));
        assertTrue(exception.getMessage().contains("IT LOOKS LIKE YOU'RE MISSING A HOSTNAME."));
    }

    /**
     * These next couple test out the check for port in the options
     * basically just a copy replace of the Host tests above.
     * @throws OptionsChecker.MissingName a special error message.
     */
    @Test
    void test20CheckForPortFlagTrue() throws OptionsChecker.MissingName {
        String[] test = {"-print", "-port", "9090", "-hello-world", "-README", "more things"};
        ArrayList<String> argList = new ArrayList<>(Arrays.asList(test));
        ArrayList<String> compare = new ArrayList<>(Arrays.asList("-print", "-hello-world", "-README", "more things"));

        String port = OptionsChecker.checkForPort(argList);

        assertEquals(port, "9090");

        assertEquals(compare, argList);
    }

    @Test
    void test21CheckForPortFlagTrueAfterTextFile() throws OptionsChecker.MissingName {
        String[] test = {"-print", "-host", "localhost", "-port", "5309", "-hello-world", "-README", "more things"};
        ArrayList<String> argList = new ArrayList<>(Arrays.asList(test));
        ArrayList<String> compare = new ArrayList<>(Arrays.asList("-print", "-host", "localhost", "-hello-world", "-README", "more things"));

        String port = OptionsChecker.checkForPort(argList);

        assertEquals(port, "5309");

        assertEquals(compare, argList);
    }

    @Test
    void test22CheckForPortFlagError() {
        String[] test = {"-print", "-port", "-hello-world", "-README", "more things"};
        ArrayList<String> argList = new ArrayList<>(Arrays.asList(test));

        Exception exception = assertThrows(OptionsChecker.MissingName.class, () ->
                OptionsChecker.checkForPort(argList));
        assertTrue(exception.getMessage().contains("IT LOOKS LIKE YOU'RE MISSING A PORT."));
    }

    @Test
    void test24CheckForPortLastArgError() {
        String[] test = {"-print", "-port"};
        ArrayList<String> argList = new ArrayList<>(Arrays.asList(test));

        Exception exception = assertThrows(OptionsChecker.MissingName.class, () ->
                OptionsChecker.checkForPort(argList));
        assertTrue(exception.getMessage().contains("IT LOOKS LIKE YOU'RE MISSING A PORT."));
    }

    /**
     * These next few tests go through checking to see
     * that port and host are both set or neither set.
     */
    @Test
    void test25ForHostPortBothNull() throws OptionsChecker.HostPortGoesTogether {
        String[] result = OptionsChecker.hostAndPortOrNeither(null, null);
        assertEquals("localhost", result[0]);
        assertEquals("8080", result[1]);
    }

    @Test
    void test26ForHostPortBothSet() throws OptionsChecker.HostPortGoesTogether {
        String host = "hostfromouttatown";
        String port = "1234";
        String[] result = OptionsChecker.hostAndPortOrNeither(host, port);
        assertEquals(result[0], host);
        assertEquals(result[1], port);
    }

    @Test
    void test27ForHostPortPortNotSet() {
        String host = "hostfromouttatown";
        Exception exception = assertThrows(OptionsChecker.HostPortGoesTogether.class, () ->
                OptionsChecker.hostAndPortOrNeither(host, null));
        assertTrue(exception.getMessage().contains("MISSING HOST OR PORT!\n" +
                "You need to have a host and a port,\n"));
    }

    @Test
    void test28ForHostPortHostNotSet() {
        String port = "1234";
        Exception exception = assertThrows(OptionsChecker.HostPortGoesTogether.class, () ->
                OptionsChecker.hostAndPortOrNeither(null, port));
        assertTrue(exception.getMessage().contains("MISSING HOST OR PORT!\n" +
                "You need to have a host and a port,\n"));
    }

} // end of OptionsCheckerTest class
