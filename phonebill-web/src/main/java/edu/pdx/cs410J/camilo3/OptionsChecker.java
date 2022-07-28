package edu.pdx.cs410J.camilo3;

import com.google.common.annotations.VisibleForTesting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

/**
 * a class for doing a lot of validation on the individual options
 * and arguments
 */
public class OptionsChecker {

    /**
     * if -README option is invoked, this method is called and prints the
     * readme file found at README.txt.
     */
    @VisibleForTesting
    static void printReadme() {
        try (InputStream readme = Project4.class.getResourceAsStream("README.txt")
        ) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
            for (String line; (line = reader.readLine()) != null;) {
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * just sees if there is a -README option in any of the options
     */
    @VisibleForTesting
    static boolean checkForReadme(ArrayList<String> argList) {
        String prev = null;
        for (String arg : argList) {
            if (arg.equals("-README")) {
                printReadme();
                return true;
            } else if (! arg.startsWith("-") &&
                    (arg == argList.get(0) ||
                            (!Objects.equals(prev, "-host") &&
                                    !Objects.equals(prev, "-port")))) {
                return false;
            }
            prev = arg;
        }
        return false;
    }

    /**
     * just sees if there is a -print option in any of the options
     * works with -host, -port additional possibility
     */
    @VisibleForTesting
    static boolean checkForPrint(ArrayList<String> argList) {
        String prev = null;
        for (int i=0; i < argList.size(); i++) {
            if (argList.get(i).equals("-print")) {
                argList.remove(i);
                return true;
            } else if (! argList.get(i).startsWith("-") &&
                    (i == 0 || ( !prev.equals("-host") && !prev.equals("-port")))) {
                return false;
            }
            prev = argList.get(i);
        }
        return false;
    }

    /**
     * just sees if there is a -print option in any of the options
     * works with -host, -port additional possibility
     */
    @VisibleForTesting
    static boolean checkForSearch(ArrayList<String> argList) {
        String prev = null;
        for (int i=0; i < argList.size(); i++) {
            if (argList.get(i).equals("-search")) {
                argList.remove(i);
                return true;
            } else if (! argList.get(i).startsWith("-") &&
                    (i == 0 || ( !prev.equals("-host") && !prev.equals("-port")))) {
                return false;
            }
            prev = argList.get(i);
        }
        return false;
    }

    /**
     * just sees if there is a -host option in any of the options
     * if there is, it will remove the next arg as the host, delete that
     * delete the option and return the string, else return null string.
     * if the next arg is an option or is non-existent, throws error.
     */
    @VisibleForTesting
    static String checkForHost(ArrayList<String> argList) throws MissingName {
        String prev = null;
        String turnString = null;
        for (int i=0; i < argList.size(); i++) {
            String curr = argList.get(i);
            if (curr.equals("-host")) {
                // if we haven't reached the end of the args
                // and the next arg isn't an option
                if (i + 1 < argList.size() && !argList.get(i + 1).startsWith("-")) {
                    turnString = argList.get(i + 1);
                    argList.remove(i + 1);
                    argList.remove(i);
                } else {
                    throw new MissingName("HOSTNAME");
                }
            } else if (! curr.startsWith("-") &&
                    (i == 0 || !prev.equals("-port"))) {
                    break;
                }
                prev = curr;
        }
        return turnString;
    }

    /**
     * just sees if there is a -pretty option in any of the options
     * if there is, it will remove the next arg as the filename/'-', delete that
     * delete the option and return the string, else return null string.
     * if the next arg is an option or is non-existent, throws error.
     */
    @VisibleForTesting
    static String checkForPort(ArrayList<String> argList) throws MissingName {
        String turnString = null;
        String prev = null;
        for (int i=0; i < argList.size(); i++) {
            String curr = argList.get(i);
            if (curr.equals("-port")) {
                // if we haven't reached the end of the args
                // and the next arg isn't an option or is '-'
                if (i+1 < argList.size() && ! argList.get(i+1).startsWith("-")) {
                    turnString = argList.get(i+1);
                    argList.remove(i+1);
                    argList.remove(i);
                } else {
                    throw new MissingName("PORT");
                }
            } else if (! curr.startsWith("-") &&
                    (i == 0 || !prev.equals("-host"))) {
                break;
            }
            prev = curr;
        }
        return turnString;
    }

    /**
     * if host and port are null, will set host to localhost and port to 8080
     * if host and port are not null, will return host and port
     * if we have one but not the other, will throw error.
     * @param host string name for host, or null
     * @param port string name for port, or null
     * @return returns a string array of size two, the first string
     * will be the host name and the second will be the port name
     * @throws HostPortGoesTogether get's caught up the line.
     */
    @VisibleForTesting
    static String[] hostAndPortOrNeither(String host, String port) throws HostPortGoesTogether {
        if (host == null && port == null) {
            host = "localhost";
            port = "8080";
        } else if (host == null || port == null) {
            throw new HostPortGoesTogether();
        }
        return new String[]{host, port};
    }

    /**
     * wrapper function for error handling on checking if port
     * is parsable.
     * @param portString string of the port to be parsed
     * @return the parsed portString
     * @throws PortIsNotAnInteger throws error if not parsable
     */
    @VisibleForTesting
    static int parsedPort(String portString) throws PortIsNotAnInteger {
        int port;
        try {
            port = Integer.parseInt(portString);
            return port;
        } catch (NumberFormatException e) {
            throw new PortIsNotAnInteger();
        }
    }

    /**
     * exception that is thrown if host without port or
     * port without host
     */
    static class HostPortGoesTogether extends Exception {
        public HostPortGoesTogether() {
            super("MISSING HOST OR PORT!\n" +
                    "You need to have a host and a port,\n" +
                    "or you can do neither and I'll just set\n" +
                    "host to localhost and port to 8080.");
        }
    }

    /**
     * throws an error if the port isn't an int, will probs be refactored out
     */
    static class PortIsNotAnInteger extends Exception {
        public PortIsNotAnInteger() {
            super("THE PORT HAS GOTTA BE AN INTEGER DUDE!");
        }
    }

    /**
     * exception that is thrown when the file argument is missing.
     */
    static class MissingName extends Exception {
        public MissingName(String missing) {
            super("IT LOOKS LIKE YOU'RE MISSING A " + missing + ".\n" +
                    "proper usage is -host hostname <args>\n" +
                    "please run with -README flag for more details.\n" +
                    "Thank you.");
        }
    }
} // end of OptionsChecker class.
