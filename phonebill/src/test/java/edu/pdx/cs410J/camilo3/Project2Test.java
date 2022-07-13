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

/**
 * A unit test for code in the <code>Project1</code> class.  This is different
 * from <code>Project1IT</code> which is an integration test (and can capture data
 * written to {@link System#out} and the like.
 */

class Project2Test {

  @Test
  void readmeCanBeReadAsResource() throws IOException {
    try (
      InputStream readme = Project2.class.getResourceAsStream("README.txt")
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

    Project2 proj = new Project2();
    ArrayList argList = new ArrayList<>(Arrays.asList("-README"));

    assertEquals(proj.checkForReadme(argList), true);
  }
  @Test
  void stringOfArgsWithOptionOfReadmeTrueMultipleOptions() {
    String[] test = {"-print", "-hello-world", "-README", "more things"};
    Project2 proj = new Project2();
    ArrayList argList = new ArrayList<> (Arrays.asList("-print", "-hello-world", "-README", "more things"));

    assertEquals(proj.checkForReadme(argList), true);
  }

  @Test
  void stringOfArgsWithOptionOfReadmeTrueWithTextFile() {
    String[] test = {"-print", "-textFile", "yowza.txt", "-hello-world", "-README", "more things"};
    Project2 proj = new Project2();
    ArrayList argList = new ArrayList<> (Arrays.asList("-print", "-hello-world", "-README", "more things"));

    assertEquals(proj.checkForReadme(argList), true);
  }

  @Test
  void stringOfArgsWithOptionOfReadmeFalseMultipleOptions() {
    ;
    Project2 proj = new Project2();
    ArrayList argList = new ArrayList<>(Arrays.asList("-print", "-hello-world", "Robert Paulson", "-README"));

    assertEquals(proj.checkForReadme(argList), false);
  }

  /**
   * These next few check the checkForPrint paths
   */
  @Test
  void checkForPrintTrue() {
    Project2 proj = new Project2();
    ArrayList argList = new ArrayList<>(Arrays.asList("-print", "-first"));

    assertEquals(proj.checkForPrint(argList), true);
  }

  @Test
  void checkForPrintFalse() {
    Project2 proj = new Project2();
    ArrayList argList = new ArrayList<>(Arrays.asList("-first", "-print"));

    assertEquals(proj.checkForPrint(argList), true);
  }

  @Test
  void checkForPrintFalseZero() {
    Project2 proj = new Project2();
    ArrayList argList = new ArrayList<>();

    assertEquals(proj.checkForPrint(argList), false);
  }

  @Test
  void checkForTextFlagTrue() throws Project2.MissingFileName {
    Project2 proj = new Project2();
    String[] test = {"-print", "-textFile", "yowza.txt", "-hello-world", "-README", "more things"};
    ArrayList argList = new ArrayList<>(Arrays.asList(test));
    ArrayList compare = new ArrayList(Arrays.asList(new String[]{"-print", "-hello-world", "-README", "more things"}));

    String yowza = proj.checkForTextFile(argList);

    assertEquals(yowza, "yowza.txt");

    assertEquals(compare, argList);
  }

  @Test
  void checkForTextFlagError() throws Project2.MissingFileName {
    Project2 proj = new Project2();
    String[] test = {"-print", "-textFile", "-hello-world", "-README", "more things"};
    ArrayList argList = new ArrayList<>(Arrays.asList(test));

    Exception exception = assertThrows(Project2.MissingFileName.class, () -> {
      proj.checkForTextFile(argList);
    });
    assertTrue(exception.getMessage().contains("IT LOOKS LIKE YOU'RE MISSING A FILENAME."));
  }

  @Test
  void checkNamesMatchNoErrorThrown() throws Project2.NamesDontMatch {
    Project2 proj = new Project2();
    assertTrue(proj.checkNamesMatch("hello", "hello"));
  }

  @Test
  void checkNamesMatchErrorThrown() throws Project2.NamesDontMatch {
    Project2 proj = new Project2();
    Exception exception = assertThrows(Project2.NamesDontMatch.class, () -> {
      proj.checkNamesMatch("hello", "goodbye");
    });
    assertTrue(exception.getMessage().contains("IT LOOKS LIKE YOUR NAMES DON'T MATCH."));
  }
}
