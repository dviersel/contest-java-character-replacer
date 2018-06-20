import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

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
 * We write and run this for java 8. Also tried running in java 9, which seemed a bit quicker.
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
                    return "(#4) Edwin-4, Parallel stream version";
                }

                @Override
                public String convert(String input) {
                    StringBuilder result = new StringBuilder(input.length());
                    Map<Character, Character> complements = new HashMap<>();
                    complements.put('A', 'T');
                    complements.put('T', 'A');
                    complements.put('C', 'G');
                    complements.put('G', 'C');
                    input.chars().mapToObj(i -> (char)i).parallel().forEachOrdered(value -> result.append(complements.get(value)));
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
            },
            ////////////////////////////////////////////////////////
            new Contender() {
                @Override
                public String getDescription() {
                    return "(#10) Jan-1, dirty :)";
                }

                @Override
                public String convert(final String input) {
                    char[] ca = input.toCharArray();
                    for (int i=0; i<ca.length; i++) {
                        char c = ca[i];
                        if ('A' == c) {
                            ca[i] ='T';
                        } else if ('T' == c) {
                            ca[i] ='A';
                        } else if ('C' == c) {
                            ca[i] ='G';
                        } else if ('G' == c) {
                            ca[i] ='C';
                        }
                    }

                    return new String(ca);
                }
            },
            ////////////////////////////////////////////////////////
            new Contender() {
                @Override
                public String getDescription() {
                    return "(#11) Jan-2, simple replace + no-upper";
                }

                @Override
                public String convert(String input) {
                    return input
                            .replace('A', 't')
                            .replace('T', 'A')
                            .replace('C', 'g')
                            .replace('G', 'C')
                            .replace('t', 'T')
                            .replace('g', 'G');
                }
            },
            ////////////////////////////////////////////////////////
            new Contender() {
                class Block {
                    int index;
                    String input;
                    String output;
                }

                @Override
                public String getDescription() {
                    return "(#12) Milo-1, parallel string replace";
                }

                @Override
                public String convert(String input) {
                    // Split input in multiple parts
                    int partCount = 8;
                    List<Block> blocks = new ArrayList<>();
                    for (int i = 0; i < partCount; i++) {
                        int start = i * CHAIN_SIZE / partCount;
                        int end = start + CHAIN_SIZE / partCount;
                        Block block = new Block();
                        block.index = i;
                        block.input = input.substring(start, end);
                        blocks.add(block);
                    }

                    StringBuilder result = new StringBuilder(input.length());
                    blocks.parallelStream().forEach(block -> block.output =
                            block.input.replace('A', 't')
                                    .replace('T', 'a')
                                    .replace('C', 'g')
                                    .replace('G', 'c')
                                    .toUpperCase());

                    blocks.forEach(block -> result.append(block.output));
                    return result.toString();
                }
            },
            ////////////////////////////////////////////////////////
            new Contender() {
                class Block {
                    int index;
                    String input;
                    String output;
                }

                @Override
                public String getDescription() {
                    return "(#13) Jan-3, Milo-1 + Jan-2, parallel string replace";
                }

                @Override
                public String convert(String input) {
                    // Split input in multiple parts
                    int partCount = 8;
                    List<Block> blocks = new ArrayList<>();
                    for (int i = 0; i < partCount; i++) {
                        int start = i * CHAIN_SIZE / partCount;
                        int end = start + CHAIN_SIZE / partCount;
                        Block block = new Block();
                        block.index = i;
                        block.input = input.substring(start, end);
                        blocks.add(block);
                    }

                    StringBuilder result = new StringBuilder(input.length());
                    blocks.parallelStream().forEach(block -> block.output =
                            block.input.replace('A', 't')
                                    .replace('T', 'A')
                                    .replace('C', 'g')
                                    .replace('G', 'C')
                                    .replace('t', 'T')
                                    .replace('g', 'G'));

                    blocks.forEach(block -> result.append(block.output));
                    return result.toString();
                }
            },
            ////////////////////////////////////////////////////////
            new Contender() {

                @Override
                public String getDescription() {
                    return "(#14) Jan-4, ForkJoinPool";
                }

                class ChainOpposite extends RecursiveAction {
                    private final char[] mSource;
                    private final int mStart;
                    private final int mLength;
                    private final char[] mDestination;

                    ChainOpposite(char[] mSource, int mStart, int mLength, char[] mDestination) {
                        this.mSource = mSource;
                        this.mStart = mStart;
                        this.mLength = mLength;
                        this.mDestination = mDestination;
                    }

                    protected void computeDirectly() {
                        for (int index = mStart; index < mStart + mLength; index++) {
                            if ('A' == mSource[index]) {
                                mDestination[index] = 'T';
                            } else if ('T' == mSource[index]) {
                                mDestination[index] = 'A';
                            } else if ('C' == mSource[index]) {
                                mDestination[index] = 'G';

                            } else if ('G' == mSource[index]) {
                                mDestination[index] = 'C';

                            }
                        }

                    }

                    protected int sThreshold = 100000;

                    protected void compute() {
                        if (mLength < sThreshold) {
                            computeDirectly();
                            return;
                        }

                        int split = mLength / 2;

                        invokeAll(new ChainOpposite(mSource, mStart, split, mDestination),
                                new ChainOpposite(mSource, mStart + split, mLength - split,
                                        mDestination));
                    }
                }

                @Override
                public String convert(String inputString) {
                    char[] input = inputString.toCharArray();
                    char[] output = new char[input.length];

                    ForkJoinPool forkJoinPool = new ForkJoinPool();
                    forkJoinPool.invoke(new ChainOpposite(input, 0, input.length, output));

                    return new String(output);
                }
            },
            ////////////////////////////////////////////////////////
            new Contender() {
                class Block {
                    long index;
                    String input;
                    String output;
                }

                @Override
                public String getDescription() {
                    return "(#15) Milo-2, parallel string replace";
                }

                @Override
                public String convert(String input) {
                    // Split input in multiple parts
                    long partCount = 400;
                    List<Block> blocks = new ArrayList<>();
                    for (long i = 0; i < partCount; i++) {
                        long start = i * CHAIN_SIZE / partCount;
                        long end = start + CHAIN_SIZE / partCount;
                        Block block = new Block();
                        block.index = i;
                        block.input = input.substring(new Long(start).intValue(), new Long(end).intValue());
                        blocks.add(block);
                    }

                    StringBuilder result = new StringBuilder(input.length());
                    blocks.parallelStream().forEach(block -> block.output = multiReplace(block.input));

                    blocks.forEach(block -> result.append(block.output));
                    return result.toString();
                }

                String multiReplace(String input) {
                    byte[] inputBytes = input.getBytes();
                    byte[] outputBytes = new byte[inputBytes.length];
                    for (int i = 0; i < inputBytes.length; i++) {
                        switch (inputBytes[i]) {
                            case 'A':
                                outputBytes[i] = 'T';
                                break;
                            case 'T':
                                outputBytes[i] = 'A';
                                break;
                            case 'C':
                                outputBytes[i] = 'G';
                                break;
                            case 'G':
                                outputBytes[i] = 'C';
                                break;
                        }
                    }
                    return new String(outputBytes);
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

            // We run the contender 5 times, to get a "warming-up" (jit compiler optimization).
            String result = null;
            for (int warmingUps = 0 ; warmingUps < 5 ; warmingUps++) {
                // Cleanup memory (I know, we are not smarter than JVM, but trying anyway)
                Runtime.getRuntime().gc();
                Thread.sleep(500);

                t = System.currentTimeMillis();
                result = contender.convert(input);
                procTime = (System.currentTimeMillis() - t);
            }

            report(contender.getDescription(), result, procTime, checkHash);
            if (checkHash == null && result != null) {
                // assume the first test is OK, all tests should show same hashcode...
                checkHash = result.hashCode();
            }
        }
    }


}

