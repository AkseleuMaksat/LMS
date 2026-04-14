-- liquibase formatted sql

--changeset bitlab:002-insert-courses
INSERT INTO courses (name, description, created_time, updated_time) VALUES
                                                                        (
                                                                            'Java Developer',
                                                                            'A comprehensive course covering Java programming from fundamentals to advanced topics including OOP, data structures, Spring Boot, and modern development practices.',
                                                                            NOW(), NOW()
                                                                        ),
                                                                        (
                                                                            'Python Developer',
                                                                            'A practical course on Python programming covering syntax, data structures, OOP, web development with Django/Flask, and data analysis fundamentals.',
                                                                            NOW(), NOW()
                                                                        );

--changeset bitlab:002-insert-chapters
INSERT INTO chapters (name, description, "order", course_id, created_time, updated_time) VALUES
-- Java Developer chapters (course_id = 1)
(
    'Java Basics & Syntax',
    'Introduction to Java programming language, JVM internals, basic syntax, variables, data types, and operators.',
    1, 1, NOW(), NOW()
),
(
    'Control Flow Statements',
    'Conditional statements (if-else, switch), loops (for, while, do-while), and branching (break, continue, return).',
    2, 1, NOW(), NOW()
),
(
    'Object-Oriented Programming',
    'Core OOP concepts: classes, objects, inheritance, polymorphism, encapsulation, and abstraction in Java.',
    3, 1, NOW(), NOW()
),
(
    'Collections Framework',
    'Java Collections: List, Set, Map, Queue interfaces and their implementations — ArrayList, HashMap, LinkedList, etc.',
    4, 1, NOW(), NOW()
),
-- Python Developer chapters (course_id = 2)
(
    'Python Basics',
    'Python syntax, variables, data types, operators, and the Python interpreter.',
    1, 2, NOW(), NOW()
),
(
    'Data Structures in Python',
    'Lists, tuples, dictionaries, sets — usage, operations, and best practices.',
    2, 2, NOW(), NOW()
),
(
    'Functions and Modules',
    'Defining functions, lambda expressions, decorators, and organizing code into modules and packages.',
    3, 2, NOW(), NOW()
);

--changeset bitlab:002-insert-lessons
INSERT INTO lessons (name, description, content, "order", chapter_id, created_time, updated_time) VALUES

-- Chapter 1: Java Basics (chapter_id = 1)
(
    'Introduction to Java and JVM',
    'Overview of Java history, platform independence, and how the JVM works.',
    'Java is a high-level, class-based, object-oriented programming language that is designed to have as few implementation dependencies as possible. It was developed by James Gosling at Sun Microsystems and released in 1995.

One of the key features of Java is platform independence, achieved through the Java Virtual Machine (JVM). When you compile a Java program, it is converted into bytecode — an intermediate representation that the JVM can execute on any operating system.

The Java development process:
1. Write source code (.java files)
2. Compile with javac → produces bytecode (.class files)
3. JVM executes bytecode on the host OS

Key features of Java:
- Write Once, Run Anywhere (WORA)
- Strongly typed language
- Automatic memory management via Garbage Collection
- Rich standard library (JDK)
- Multithreading support built-in

The JVM consists of three main components:
- Class Loader: loads .class files into memory
- Runtime Data Areas: method area, heap, stack, etc.
- Execution Engine: interprets or JIT-compiles bytecode to native machine code',
    1, 1, NOW(), NOW()
),
(
    'Variables, Data Types and Operators',
    'Primitive and reference data types, variable declaration, and Java operators.',
    'Java is a statically typed language, meaning every variable must have a declared type before use.

Primitive Data Types in Java:
- byte: 8-bit signed integer (-128 to 127)
- short: 16-bit signed integer
- int: 32-bit signed integer (most commonly used)
- long: 64-bit signed integer (use L suffix: 100L)
- float: 32-bit floating point (use F suffix: 3.14F)
- double: 64-bit floating point (default for decimals)
- char: 16-bit Unicode character (e.g. ''A'')
- boolean: true or false

Variable declaration examples:
int age = 25;
double salary = 75000.50;
String name = "Alice";   // String is a reference type
boolean isActive = true;

Java Operators:
- Arithmetic: + - * / % ++ --
- Comparison: == != > < >= <=
- Logical: && || !
- Assignment: = += -= *= /=
- Ternary: condition ? valueIfTrue : valueIfFalse

Type casting:
int x = (int) 9.99;   // explicit cast: x = 9
double d = 5;          // implicit widening cast: d = 5.0',
    2, 1, NOW(), NOW()
),

-- Chapter 2: Control Flow (chapter_id = 2)
(
    'Lecture: if-else and switch Statements',
    'Deep dive into conditional statements in Java with real-world examples.',
    'Control flow statements allow your program to make decisions and execute different code paths depending on conditions.

The if-else Statement:
The most fundamental conditional construct. Syntax:

if (condition) {
    // executes if condition is true
} else if (anotherCondition) {
    // executes if anotherCondition is true
} else {
    // executes if none of the above are true
}

Real-world example — grade calculator:
int score = 85;
String grade;
if (score >= 90) {
    grade = "A";
} else if (score >= 80) {
    grade = "B";
} else if (score >= 70) {
    grade = "C";
} else {
    grade = "F";
}

The switch Statement:
More readable than multiple if-else when comparing a single variable against many values.

switch (dayOfWeek) {
    case 1 -> System.out.println("Monday");
    case 2 -> System.out.println("Tuesday");
    case 6, 7 -> System.out.println("Weekend!");
    default -> System.out.println("Unknown day");
}

Java 14+ introduced switch expressions:
String result = switch (status) {
    case "ACTIVE" -> "User is active";
    case "BANNED" -> "User is banned";
    default -> "Unknown status";
};

Best practices:
- Always include a default case in switch
- Prefer switch expressions (arrow syntax) in modern Java
- Avoid deeply nested if-else — extract to methods instead',
    1, 2, NOW(), NOW()
),
(
    'Practice: Control Flow Exercises',
    'Hands-on practice tasks to reinforce understanding of conditional logic in Java.',
    'Practice Tasks for Control Flow Statements

Task 1 — FizzBuzz:
Write a program that prints numbers from 1 to 100.
- For multiples of 3, print "Fizz"
- For multiples of 5, print "Buzz"
- For multiples of both, print "FizzBuzz"

for (int i = 1; i <= 100; i++) {
    if (i % 15 == 0) System.out.println("FizzBuzz");
    else if (i % 3 == 0) System.out.println("Fizz");
    else if (i % 5 == 0) System.out.println("Buzz");
    else System.out.println(i);
}

Task 2 — Season Detector:
Given a month number (1–12), print the season using a switch statement.
- 12, 1, 2 → Winter
- 3, 4, 5 → Spring
- 6, 7, 8 → Summer
- 9, 10, 11 → Autumn

Task 3 — Simple Calculator:
Accept two numbers and an operator (+, -, *, /) via Scanner.
Use switch to perform the correct operation.
Handle division by zero explicitly.

Task 4 — Leap Year Checker:
A year is a leap year if:
- Divisible by 4 AND
- NOT divisible by 100, UNLESS also divisible by 400

int year = 2024;
boolean isLeap = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);

Try to solve all 4 tasks before checking solutions!',
    2, 2, NOW(), NOW()
),

-- Chapter 3: OOP (chapter_id = 3)
(
    'Classes, Objects and Encapsulation',
    'Understanding the building blocks of OOP: defining classes, creating objects, and protecting data.',
    'Object-Oriented Programming (OOP) is a programming paradigm based on the concept of objects — entities that combine state (fields) and behavior (methods).

Defining a Class in Java:
public class BankAccount {
    // Fields (state) — kept private for encapsulation
    private String owner;
    private double balance;

    // Constructor
    public BankAccount(String owner, double initialBalance) {
        this.owner = owner;
        this.balance = initialBalance;
    }

    // Methods (behavior)
    public void deposit(double amount) {
        if (amount > 0) balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > balance) return false;
        balance -= amount;
        return true;
    }

    // Getters
    public double getBalance() { return balance; }
    public String getOwner() { return owner; }
}

Creating Objects:
BankAccount account = new BankAccount("Alice", 1000.0);
account.deposit(500.0);
account.withdraw(200.0);
System.out.println(account.getBalance()); // 1300.0

Encapsulation principles:
- Declare fields as private
- Provide public getters and setters only where necessary
- Validate input inside setters
- Use @Getter/@Setter from Lombok to reduce boilerplate

The ''this'' keyword refers to the current object instance and is used to differentiate between field names and constructor/method parameters.',
    1, 3, NOW(), NOW()
),

-- Chapter 4: Collections (chapter_id = 4)
(
    'Introduction to Java Collections Framework',
    'Overview of the Java Collections Framework, core interfaces, and when to use each implementation.',
    'The Java Collections Framework (JCF) provides a unified architecture for storing and manipulating groups of objects. It includes interfaces, implementations, and algorithms.

Core Interfaces:
- Collection: root interface
  - List: ordered, allows duplicates (ArrayList, LinkedList)
  - Set: no duplicates (HashSet, LinkedHashSet, TreeSet)
  - Queue: FIFO ordering (LinkedList, PriorityQueue)
- Map: key-value pairs, keys are unique (HashMap, LinkedHashMap, TreeMap)

ArrayList — most commonly used List:
List<String> languages = new ArrayList<>();
languages.add("Java");
languages.add("Python");
languages.add("Go");
languages.remove("Go");
System.out.println(languages.get(0)); // Java
System.out.println(languages.size()); // 2

HashMap — key-value storage:
Map<String, Integer> scores = new HashMap<>();
scores.put("Alice", 95);
scores.put("Bob", 87);
scores.put("Carol", 92);
System.out.println(scores.get("Alice")); // 95

// Iterating a Map
for (Map.Entry<String, Integer> entry : scores.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}

Choosing the right collection:
- Need fast random access? → ArrayList
- Need fast insert/delete at ends? → LinkedList
- Need unique elements? → HashSet
- Need sorted unique elements? → TreeSet
- Need key-value lookup? → HashMap
- Need insertion-ordered map? → LinkedHashMap

With Java generics, collections are type-safe at compile time, preventing ClassCastException at runtime.',
    1, 4, NOW(), NOW()
),

-- Python chapters
(
    'Introduction to Python',
    'Python philosophy, installation, first program, and basic syntax.',
    'Python is a high-level, interpreted, dynamically typed programming language known for its readability and simplicity. Created by Guido van Rossum and first released in 1991, Python emphasizes code readability with its notable use of significant indentation.

Python Philosophy (The Zen of Python — PEP 20):
- Beautiful is better than ugly
- Explicit is better than implicit
- Simple is better than complex
- Readability counts

Python vs Java — Key Differences:
- Python is dynamically typed; Java is statically typed
- Python uses indentation for blocks; Java uses braces {}
- Python is interpreted; Java is compiled to bytecode
- Python has fewer lines of code for the same logic

Your first Python program:
# This is a comment
print("Hello, World!")

name = input("Enter your name: ")
print(f"Hello, {name}! Welcome to Python.")

Variables and Types:
age = 25              # int
salary = 75000.50     # float
name = "Alice"        # str
is_active = True      # bool
nothing = None        # NoneType

Python automatically infers types — no declaration needed.

type() function:
print(type(age))      # <class ''int''>
print(type(name))     # <class ''str''>

F-strings (Python 3.6+):
message = f"Name: {name}, Age: {age}"
print(message)',
    1, 5, NOW(), NOW()
),
(
    'Lists and Dictionaries in Python',
    'Working with Python lists, slicing, list comprehensions, and dictionaries.',
    'Python provides powerful built-in data structures that make common programming tasks simple and expressive.

Python Lists:
Lists are ordered, mutable, and allow duplicate values.

fruits = ["apple", "banana", "cherry", "apple"]
print(fruits[0])       # apple
print(fruits[-1])      # apple (last element)
print(fruits[1:3])     # [''banana'', ''cherry''] (slicing)

Common list methods:
fruits.append("mango")        # add to end
fruits.insert(1, "blueberry") # insert at index
fruits.remove("apple")        # remove first occurrence
fruits.pop()                   # remove and return last
fruits.sort()                  # sort in place
fruits.reverse()               # reverse in place

List Comprehensions — Pythonic way to create lists:
# Traditional approach
squares = []
for x in range(10):
    squares.append(x ** 2)

# List comprehension (preferred)
squares = [x ** 2 for x in range(10)]

# With condition
evens = [x for x in range(20) if x % 2 == 0]

Python Dictionaries:
Dictionaries store key-value pairs (similar to Java HashMap).

student = {
    "name": "Alice",
    "age": 22,
    "courses": ["Math", "Physics"]
}

print(student["name"])            # Alice
print(student.get("grade", "N/A")) # N/A (safe access)

student["grade"] = "A"            # add/update key

# Iterating
for key, value in student.items():
    print(f"{key}: {value}")

# Dictionary comprehension
word_lengths = {word: len(word) for word in ["hello", "world", "python"]}',
    1, 6, NOW(), NOW()
);