
# Hostel Room Allocation System

A simple Java program that runs in the terminal. It helps a hostel warden keep a record of rooms and students, and give each student a room. It does not use a database, a website or any extra library. All data is saved in two small CSV files, so nothing is lost when you close the program.

## What this project can do

- Add a student (roll number and name)
- Add a room (room number and how many beds it has)
- Give a room to a student
- Remove a student from a room (vacate)
- Show all rooms, with how many beds are used and who is staying there
- Show only the rooms that still have free beds
- Show all students and their room
- Search for one student by roll number
- Save everything automatically and load it again the next time
- Show a clear message when you type something wrong (for example letters instead of a number)

## What you need before you start

1. **Java JDK 8 or newer** installed on your computer.
2. A terminal. You can use the terminal inside VS Code.

To check that Java is installed, open a terminal and type these two lines. Both should show a version number.

```
java -version
javac -version
```

## How to run the project (step by step)

1. Download or copy the project folder to your computer.
2. Open the folder in VS Code (**File > Open Folder**). You should see files like `Main.java` and `HostelManager.java` in the list on the left.
3. Open the terminal in VS Code (**Terminal > New Terminal**).
4. Make sure the terminal is inside the project folder. The path shown in the terminal should end with the project folder name.
5. Compile the code by typing this and pressing Enter:

   ```
   javac -d out *.java
   ```

   If nothing is printed and you get the prompt back, the compile worked.
6. Run the program by typing this and pressing Enter:

   ```
   java -cp out Main
   ```

7. The menu will appear. Type a number and press Enter to choose an option.

Important: always run these commands from the project folder. The program looks for the `data` folder in the place where you start it.

## How to use the program (simple example)

Follow these steps to try everything once:

1. Choose **1** (Add Student). Enter a roll number and a name.
2. Choose **7** (View All Students). You will see the student with "Not allocated" in the Room column.
3. Choose **6** (View Available Rooms). You will see the rooms that have free beds.
4. Choose **3** (Allocate Room to Student). Enter the roll number and a room number, for example 101.
5. Choose **5** (View All Rooms). Room 101 now shows 1 occupied and the student's name.
6. Add two more students and give them room 101 as well. The second student fits (the starter rooms have 2 beds). For the third student you will see a message that the room is full.
7. Choose **4** (Vacate Student from Room) to take a student out of a room.
8. Choose **8** to search a student by roll number.
9. Choose **9** to exit. Your data is already saved.

### Sample output

```
===== HOSTEL ROOM ALLOCATION SYSTEM =====
1. Add Student
2. Add Room
3. Allocate Room to Student
4. Vacate Student from Room
5. View All Rooms
6. View Available Rooms
7. View All Students
8. Search Student by Roll No
9. Exit
Enter your choice: 1

--- Add Student ---
Roll number: 25BAI11371
Name: Zaara Parween
Student added.

Enter your choice: 3

--- Allocate Room ---
Student roll number: 25BAI11371
Room number: 101
Done. Room 101 allocated.

Enter your choice: 5

--- All Rooms ---
Room     Capacity   Occupied   Residents
-----------------------------------------------------------
101      2          1          Zaara Parween
102      2          0          -
```

## Where the data is saved

The program makes a folder called `data` the first time you run it. Inside it are two files:

- `data/rooms.csv` has one room on each line: `RoomNo,Capacity`
- `data/students.csv` has one student on each line: `RollNo,Name,RoomNo`

A `RoomNo` of `0` means the student has no room yet. On the first run the program also creates five starter rooms (101 to 105) with 2 beds each, so you can start using it right away.

The number of people in a room is counted from the students file. It is not saved in a separate place, so the two files cannot give different answers.

## Project files

```
Hostel-Room-Allocation/
├── Main.java                # menu and user input
├── HostelManager.java       # the allocation rules
├── FileHandler.java         # reads and writes the CSV files
├── Room.java                # room details
├── Student.java             # student details
├── HostelException.java     # general custom exception
├── RoomFullException.java   # used when a room has no free bed
├── data/                    # made automatically on the first run
│   ├── rooms.csv
│   └── students.csv
├── README.md
└── .gitignore
```

| File | What it does |
|---|---|
| Main | Shows the menu, takes input from the user and calls the manager |
| HostelManager | Keeps the list of rooms and students and checks the rules before giving a room |
| FileHandler | The only class that reads or writes files |
| Room | Holds a room number and its capacity |
| Student | Holds roll number, name and room number |
| HostelException | Error used for wrong actions, like an unknown roll number |
| RoomFullException | A special kind of HostelException used when a room is full |

## Java concepts used

- Classes, objects and constructors (`Room`, `Student`, `HostelManager`)
- Encapsulation (private variables with getters and setters)
- Inheritance (`RoomFullException` extends `HostelException`)
- Custom exceptions with `throw` and `throws`
- `try` and `catch` with more than one `catch` block
- `ArrayList` from the Collections framework
- File handling with `BufferedReader` and `FileWriter`
- String handling and formatted output with `printf`
- Loops, `switch` and `if/else`

## Common problems and fixes

**"Unresolved compilation problems" or "cannot be resolved to a type"**
This usually happens when you press the Run button in VS Code. Use the two terminal commands from the "How to run" section instead (`javac -d out *.java` and then `java -cp out Main`).

**"javac is not recognized"**
The JDK is not installed or not added to PATH. Install the JDK and open a new terminal.

**"Could not find or load main class Main"**
You are in the wrong folder, or you skipped the compile step. Go to the project folder and run both commands again.

**The data looks empty after running from another folder**
The program creates a new `data` folder in the folder where it is started. Always start it from the project folder.

## Ideas for improvement

- Move a student from one room to another in one step
- Add hostel blocks and floors
- Keep separate lists for boys and girls hostels
- Save a printable report of all allocations