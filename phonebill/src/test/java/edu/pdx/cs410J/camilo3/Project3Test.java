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

class Project3Test {

  // ALSO GOING TO ASK IN OFFICE HOURS ABOUT THIS, WHICH IS WHY IS HERE ON TURNIN.
//  @Test
//  void testEverythingWorksWonderfully() throws Exception {
//      Project2 proj = new Project2();
//      String[] args = new String[] {"Steven", "867-867-5309", "503-222-2222",
//                    "03/17/2022", "23:11", "03/17/2022", "23:27"};
//      proj.main(args);
//
//  }

  @Test
  void checkNamesMatchErrorThrown() throws Project3.NamesDontMatch {
    Project3 proj = new Project3();
    Exception exception = assertThrows(Project3.NamesDontMatch.class, () -> {
      proj.checkNamesMatch("hello", "goodbye");
    });
    assertTrue(exception.getMessage().contains("IT LOOKS LIKE YOUR NAMES DON'T MATCH."));
  }
}
