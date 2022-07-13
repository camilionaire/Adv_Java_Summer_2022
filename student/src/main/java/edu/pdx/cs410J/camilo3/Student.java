package edu.pdx.cs410J.camilo3;

import edu.pdx.cs410J.lang.Human;

//import java.util.ArrayList;
import java.util.*;

/**                                                                                 
 * This class is represents a <code>Student</code>.                                 
 */                                                                                 
public class Student extends Human {

  private double gpa = 0;
  private String gender = "other";
  private ArrayList<String> classes = new ArrayList();

  /**
   * Creates a new <code>Student</code>                                             
   *
   * @param name                                                                    
   *        The student's name                                                      
   * @param classes                                                                 
   *        The names of the classes the student is taking.  A student              
   *        may take zero or more classes.                                          
   * @param gpa                                                                     
   *        The student's grade point average                                       
   * @param gender                                                                  
   *        The student's gender ("male", "female", or "other", case insensitive)
   */                                                                               
  public Student(String name, ArrayList<String> classes, double gpa, String gender) {
    super(name);
    this.gpa = gpa;
    this.gender = gender;
    this.classes = classes;
  }

  /**                                                                               
   * All students say "This class is too much work"
   */
  @Override
  public String says() {
//    throw new UnsupportedOperationException("Not implemented yet");
    return "This class is too much work";
  }
                                                                                    
  /**                                                                               
   * Returns a <code>String</code> that describes this                              
   * <code>Student</code>.                                                          
   */                                                                               
  public String toString() {
//    throw new UnsupportedOperationException("Not implemented) yet");
//    Dave has a GPA of 3.64 and is taking 3 classes: Algorithms, Operating
//    Systems, and Java. He says "This class is too much work".
    var lastly = name + " has a GPA of " + gpa;
    lastly = lastly + " and is taking " + classes.size() + " classes: ";
    lastly = lastly + classes + ". ";
    if (gender == "male") {
      lastly = lastly + "He says \"";
    } else if (gender == "female") {
      lastly = lastly + "She says \"";
    } else {
      lastly = lastly + "They say \"";
    }
    lastly = lastly + says() + "\".";
//    return (this.name) + says();
//    return says();
    return lastly;
  }

  /**
   * Main program that parses the command line, creates a
   * <code>Student</code>, and prints a description of the student to
   * standard out by invoking its <code>toString</code> method.
   */
  public static void main(String[] args) {


    if (args.length < 3) {
      System.err.println("Missing command line arguments");
    } else {
      System.out.println("Hello world!!!");
      var my_classes = Arrays.copyOfRange(args, 3, args.length);
      ArrayList<String> classList = new ArrayList<>(Arrays.asList(my_classes));
      Student a_student = new Student(args[0], classList, Double.parseDouble(args[2]), args[1]);
      System.out.println(a_student.toString());
    }
  }
}