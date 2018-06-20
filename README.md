# contest-java-character-replacer

Contest, find the quickest run/algorithm for converting a "deoxyribonucleic acid" chain.
(We keep the abbreviation of the chain on purpose out of this code,
this challenge is part of some "exam"... so we do not want to be found on the abbreviation).

Code readability (just for the convert methods) is also nice, but speed was the challenge here...

The objective is to replace all the characters in the chain, according to these rules:

- A chain consists of 22000000 characters.
- The characters are chosen from (uppercase) A, T, C, G.
- We replace them like this: A by T, T by A, C by G, G by C.

The only methods of importance here are the "convert" implementations. The rest is used to
run the different implementations, and do some basic timing.

We write and run this for java 8. We noticed that (#5) is the quickest one (on my machine), and that
relative speeds vary depending on the CPU architecture you are running on.
Correction: (#11) is the winner now! Well done Jan! (ok, inspired by #5).

You can run this using (linux script):

./run-contest.sh

Or you can simply run it with:

javac Contest.java
java Contest


There's an example output in the file "example-run.txt".
