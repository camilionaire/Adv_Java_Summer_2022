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


}
