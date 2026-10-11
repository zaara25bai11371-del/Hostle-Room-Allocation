# Hostel Room Allocation System

A small console application in core Java that helps a hostel warden keep track of
rooms and students, and decide who stays in which room. It runs completely in the
terminal and saves everything in two CSV files, so there is no database, GUI or
external library involved.

## Features

- Add students (roll number + name)
- Add rooms (room number + capacity)
- Allocate a room to a student, with a check that the room still has a free bed
- Vacate a student from their room
- View all rooms with capacity, occupancy and the names of residents
- View only the rooms that still have free beds
- View all students and their room status
- Search a student by roll number
- Data is loaded when the program starts and saved after every change
- Wrong input (letters instead of numbers, empty fields, unknown roll numbers) is
  handled with a message instead of a crash

## Java concepts used

| Concept | Where |
|---|---|
| Classes, objects, constructors | `Room`, `Student`, `HostelManager` |
| Encapsulation | private fields with getters/setters in `Room` and `Student` |
| Inheritance | `RoomFullException` extends `HostelException` |
| Custom exceptions, `throw` / `throws` | `HostelException`, `RoomFullException` |
| try / catch with multiple catch blocks | `Main.allocateRoom()` |
| `ArrayList` / Collections | `HostelManager` keeps lists of rooms and students |
| File handling (Reader / Writer streams) | `FileHandler` |
| Strings and formatted output | `String.format` style `printf` tables in `Main` |
| Loops, switch, if/else | menu loop and validation |

## Project structure

```
Hostel-Room-Allocation/
├── Main.java                # menu and user input
├── HostelManager.java       # allocation rules and logic
├── FileHandler.java         # reads/writes the csv files
├── Room.java                # room data
├── Student.java             # student data
├── HostelException.java     # general custom exception
├── RoomFullException.java   # thrown when a room has no free bed
├── data/                    # created automatically on first run
│   ├── rooms.csv
│   └── students.csv
├── README.md
└── .gitignore
```

## Requirements

- JDK 8 or newer (check with `java -version` and `javac -version`)
- Any terminal, or the terminal inside VS Code

## How to run

Open a terminal in the project folder (the one that contains the `.java` files) and run:

```
javac -d out *.java
java -cp out Main
```

Always run the `java` command from this folder, because the program looks for
`data/rooms.csv` and `data/students.csv` relative to where you start it.

## How the data is stored

- `data/rooms.csv` has one room per line: `RoomNo,Capacity`
- `data/students.csv` has one student per line: `RollNo,Name,RoomNo`
  (`RoomNo` is `0` when the student has not been given a room)
- If the files do not exist, the program creates them on the first run and adds
  five starter rooms (101 to 105) with two beds each
- Room occupancy is not stored separately. It is worked out from the students
  file, so the two files can never disagree about who is in which room
- A comma typed inside a name is replaced with a space so the csv format stays valid

## Example run

```
===== HOSTEL ROOM ALLOCATION SYSTEM =====
1. Add Student
...
9. Exit
Enter your choice: 1

--- Add Student ---
Roll number: 25BAI10604
Name: Vedprakash Singh
Student added.

Enter your choice: 3

--- Allocate Room ---
Student roll number: 25BAI10604
Room number: 101
Done. Room 101 allocated.

Enter your choice: 5

--- All Rooms ---
Room     Capacity   Occupied   Residents
-----------------------------------------------------------
101      2          1          Vedprakash Singh
102      2          0          -
```

## Possible improvements

- Move a student directly from one room to another
- Separate blocks or floors, and boys/girls hostels
- Fee or warden details per room
- Export the allocation list to a text report
