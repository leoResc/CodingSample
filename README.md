# CodingSample

**Purpose**  
This project exists for the purpose of providing some insights in my coding behaviour.

**Functionality**  
The application asks questions and reacts to user input inside a command line (terminal).

## App tech specs

* SpringBootApplication - CommandLineRunner
* Java 12 (down to v 1.8 is possible)
* Kotlin 1.3.31 (v 1.2 and lover isn't compatible)
* Maven

## Unusual code parts

* UserInput.kt
   * cleaning of the Scanner Buffer -> wrapped in asynchronous coroutine to make it terminate after certain time
* UserAnswer.kt
   * Sealed classes and inner sealed
   * Exceptions are caught and are part of the ("Invalid") answer
* DiscussionTopicTest.kt
   * Mocking an interface and giving it sequential behaviour, because the implementation has a state
   
   