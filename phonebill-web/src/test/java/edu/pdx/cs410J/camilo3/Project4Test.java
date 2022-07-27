package edu.pdx.cs410J.camilo3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Project4Test {

    @Test
    void testToSeeIfEverythingRunsSmoothly() {
        Project4 proj = new Project4();
        String[] args = new String[] {"Steven", "867-867-5309", "503-222-2222",
                "03/17/2022", "11:11", "am", "03/17/2022", "12:01", "pm"};
        proj.main(args);
    }

}
