#!/bin/bash

# compile
cd src/main/java
javac Contest.java

# run
echo "Running with:"
java -version 2>&1
echo

java Contest 2>&1

# cleanup
rm -rf Contest*.class

# system info
echo
echo "CPU info (available if on linux)"
grep -e "model name" -e "bogo" /proc/cpuinfo  | sort -u
echo
