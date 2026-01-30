# Fibonacci Crypter

## WHAT

A simple crypter for amusement purpose; it translate a char to a Fibonacci number, according to char position in Fibonacci sequence.

A kind of:
    - a -> 1
    - b -> 2
    - c -> 3
    - d -> 5
    - e -> 8
    - ...

Really:
    - 0-9 chars are first 10 Fibonacci numbers
    - a-z (lowercase) alphabet chars start at 11nth and complete at 36nth
    - A-Z (uppercase) alphabet chars start at 37nth and complete at 63nth

I wrote this just to send birthday greetings to my cousin, who is studying her first programming exam ;D


## HOW

You need Maven >= 3.6 and Java JDK 11

from:
https://dlcdn.apache.org/maven/maven-3/3.8.4/binaries/apache-maven-3.8.4-bin.zip

from:
https://download.java.net/java/GA/jdk11/9/GPL/openjdk-11.0.2_windows-x64_bin.zip

after downloaded/installed, type:

mvn spring-boot:run

