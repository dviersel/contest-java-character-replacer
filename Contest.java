import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Contest, find the quickest run/algorithm for converting a "deoxyribonucleic acid" chain.
 * (We keep the abbreviation of the chain on purpose out of this code,
 * this challenge is part of some "exam"... so we do not want to be found on the abbreviation).
 *
 * Code readability (just for the convert methods) is also nice, but speed was the challenge here...
 *
 * The objective is to replace all the characters in the chain, according to these rules:
 *
 * - A chain consists of 22000000 characters.
 * - The characters are chosen from (uppercase) A, T, C, G.
 * - We replace them like this: A by T, T by A, C by G, G by C.
 *
 * The only methods of importance here are the "convert" implementations. The rest is used to
 * run the different implementations, and do some basic timing.
 *
 * We write and run this for java 8. We noticed that (#5) is the quickest one (on my machine), and that
 * relative speeds vary depending on the CPU architecture you are running on.
 */
public class Contest {

    public static final int CHAIN_SIZE = 22000000;
    public static final int BAR_GRAPH_MS_DIVISOR = 10;

    /**
     * This is the list of contenders. Consisting of a description and an implementation to run.
     */
    private static final List<Contender> contenders = Arrays.asList(

            ////////////////////////////////////////////////////////
            new Contender() {
                @Override
                public String getDescription() {
                    return "(#1) Edwin-1, Map and Stream to StringBuilder";
                }

                @Override
                public String convert(String input) {
                    StringBuilder result = new StringBuilder(input.length());
                    Map<Integer, Character> complements = new HashMap<>();
                    complements.put((int) 'A', 'T');
                    complements.put((int) 'T', 'A');
                    complements.put((int) 'C', 'G');
                    complements.put((int) 'G', 'C');

                    input.chars().forEach(value -> result.append(complements.get(value)));

                    return result.toString();
                }
            },
            ////////////////////////////////////////////////////////
            new Contender() {
                @Override
                public String getDescription() {
                    return "(#2) Edwin-2, Map and For to StringBuilder";
                }

                @Override
                public String convert(String input) {
                    Map<Integer, Character> complements = new HashMap<>();
                    complements.put((int) 'A', 'T');
                    complements.put((int) 'T', 'A');
                    complements.put((int) 'C', 'G');
                    complements.put((int) 'G', 'C');

                    StringBuilder result = new StringBuilder(input.length());
                    for (int i = 0; i < input.length(); i++) {
                        result.append(complements.get((int) input.charAt(i)));
                    }

                    return result.toString();
                }
            },
            ////////////////////////////////////////////////////////
            new Contender() {
                @Override
                public String getDescription() {
                    return "(#3) Edwin-3, Map and For to StringBuffer";
                }

                @Override
                public String convert(String input) {
                    Map<Integer, Character> complements = new HashMap<>();
                    complements.put((int) 'A', 'T');
                    complements.put((int) 'T', 'A');
                    complements.put((int) 'C', 'G');
                    complements.put((int) 'G', 'C');

                    StringBuffer result = new StringBuffer(input.length());
                    for (int i = 0; i < input.length(); i++) {
                        result.append(complements.get((int) input.charAt(i)));
                    }

                    return result.toString();
                }
            },
            ////////////////////////////////////////////////////////
            new Contender() {
                @Override
                public String getDescription() {
                    return "(#4) Edwin-4, Parallel attempt (FAILED... Sorry, Edwin)";
                }

                @Override
                public String convert(String input) {
                    StringBuilder result = new StringBuilder(input.length());
                    Map<Character, Character> complements = new HashMap<>();
                    complements.put('A', 'T');
                    complements.put('T', 'A');
                    complements.put('C', 'G');
                    complements.put('G', 'C');
                    input.chars().mapToObj(i -> (char)i).parallel().forEach(value -> result.append(complements.get(value)));
                    return result.toString();
                }
            },
            ////////////////////////////////////////////////////////
            new Contender() {
                @Override
                public String getDescription() {
                    return "(#5) Thijs-1, simple replace + to-upper";
                }

                @Override
                public String convert(String input) {
                    return input
                            .replace('A', 't')
                            .replace('T', 'a')
                            .replace('C', 'g')
                            .replace('G', 'c')
                            .toUpperCase();
                }
            },
            ////////////////////////////////////////////////////////
            new Contender() {
                @Override
                public String getDescription() {
                    return "(#6) Thijs-2, For with if/else to StringBuilder";
                }

                @Override
                public String convert(String input) {
                    StringBuilder result = new StringBuilder(input.length());
                    for (int i = 0 ; i < input.length() ; i++) {
                        final char c = input.charAt(i);

                        if ('A' == c) {
                            result.append('T');
                        } else if ('T' == c) {
                            result.append('A');
                        } else if ('C' == c) {
                            result.append('G');
                        } else /* if ('G' == c) */ {
                            result.append('C');
                        }
                    }
                    return result.toString();
                }
            },
            ////////////////////////////////////////////////////////
            new Contender() {
                @Override
                public String getDescription() {
                    return "(#7) Thijs-3, For with switch/case to StringBuilder";
                }

                @Override
                public String convert(String input) {
                    StringBuilder result = new StringBuilder(input.length());
                    for (int i = 0 ; i < input.length() ; i++) {
                        final char c = input.charAt(i);

                        switch (c) {
                            case 'A':
                                result.append('T');
                                break;
                            case 'T':
                                result.append('A');
                                break;
                            case 'C':
                                result.append('G');
                                break;
                            case 'G':
                                result.append('C');
                                break;
                        }
                    }
                    return result.toString();
                }
            },
            ////////////////////////////////////////////////////////
            new Contender() {
                @Override
                public String getDescription() {
                    return "(#8) Thijs-4, For with if/else to byte array";
                }

                @Override
                public String convert(String input) {
                    byte [] result = new byte[input.length()];
                    for (int i = 0 ; i < input.length() ; i++) {
                        final char c = input.charAt(i);

                        if ('A' == c) {
                            result[i] ='T';
                        } else if ('T' == c) {
                            result[i] ='A';
                        } else if ('C' == c) {
                            result[i] ='G';
                        } else /* if ('G' == c) */ {
                            result[i] ='C';
                        }
                    }
                    return new String(result);
                }
            },
            ////////////////////////////////////////////////////////
            new Contender() {
                @Override
                public String getDescription() {
                    return "(#9) Armand-1, replace with look-ahead regex (TODO // reserved spot, to be coded)";
                }

                @Override
                public String convert(String input) {
                    return null;
                }
            }
            ////////////////////////////////////////////////////////

    );

    /**
     * The contest interface.
     */
    private interface Contender {
        /**
         * The description, containing developer name, and some info about the implementation.
         * @return description
         */
        String getDescription();
        String convert(String input);
    }

    /**
     * Generate random "deoxyribonucleic acid" chain.
     * @param size the number of characters to generate
     * @return chain string
     */
    private static String generateInput(int size) {
        Random random = new Random();
        StringBuilder result = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            result.append("CATG".charAt(random.nextInt(4)));
        }
        return result.toString();
    }

    /**
     * Show bar graph of result speed.
     * @param size number of #-es to show.
     * @return "bar".
     */
    private static String barGraph(int size) {
        StringBuilder graph = new StringBuilder(size);
        for (int i = 0; i < size ; i++) {
            graph.append("#");
        }
        return graph.toString();
    }

    /**
     * Write report.
     * @param description info
     * @param result converted input, passed in to verify correctness.
     * @param timeMs convert run duration
     * @param checkHash result must match this hash
     */
    private static void report(String description, String result, long timeMs, Integer checkHash) {
        if (result == null) {
            System.out.println(description + " - NO DATA");
            System.out.println("---");
            return;
        }
        System.out.println(description + ", result length: " + result.length() + ", result hash: " + result.hashCode() + ", process-time: " + timeMs + " ms");
        if (CHAIN_SIZE != result.length()) {
            System.out.println("*** ERROR *** length mismatch? *** check the implementation ***");
        }
        if (checkHash != null && checkHash != result.hashCode()) {
            System.out.println("*** ERROR *** hash-code mismatch? *** check the implementation ***");
        }
        System.out.println("Result: " + result.substring(0, 60) + "..." + result.substring(result.length() - 60, result.length()) + " (partial only)");
        System.out.println(barGraph((int) timeMs / BAR_GRAPH_MS_DIVISOR));
        System.out.println("---");
    }

    /**
     * Test runner. Generate chain, and execute all contenders. Measure elapsed system time (in ms), and
     * do a garbage collect before the test, just to nudge the jvm not to do that halfway a next test.
     * @param args - none
     * @throws InterruptedException on error
     */
    public static void main(String[] args) throws InterruptedException {
        long t = System.currentTimeMillis();
        String input = generateInput(CHAIN_SIZE);
        long procTime = (System.currentTimeMillis() - t);

        report("Generate input", input, procTime, null);

        Integer checkHash = null;
        for (Contender contender : contenders) {
            // Cleanup memory (I know, we are not smarter than JVM, but trying anyway)
            Runtime.getRuntime().gc();
            Thread.sleep(500);

            t = System.currentTimeMillis();
            String result = contender.convert(input);
            procTime = (System.currentTimeMillis() - t);

            report(contender.getDescription(), result, procTime, checkHash);
            if (checkHash == null && result != null) {
                // assume the first test is OK, all tests should show same hashcode...
                checkHash = result.hashCode();
            }
        }

    }


}

