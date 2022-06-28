package edu.pdx.cs410J.camilo3;

import edu.pdx.cs410J.lang.Human;

//import java.util.ArrayList;
import java.util.*;

/**                                                                                 
 * This class is represents a <code>Student</code>.                                 
 */                                                                                 
public class Student extends Human {                                                
                                                                                    
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
//    throw new UnsupportedOperationException("Not implemented yet");
    return (this.name) + says();
//    return says();
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
      Student a_student = new Student(args[0], classList, Double.parseDouble(args[1]), args[2]);
      System.out.println(a_student.toString());
    }
  }
}