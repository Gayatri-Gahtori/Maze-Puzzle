 # Maze Puzzle Game in Java

This is a simple *maze game built using Java and Swing* (Java's GUI library).  
You control a red ball and move it through the maze using arrow keys.  
The goal is to go from the Start ('S') to the End ('E') without hitting the walls marked as #.

-------------------------------------------------------------------------------------------------------------

#  Features

- Move using arrow keys (↑ ↓ ← →)
- Walls (#) can't be passed through
- Your movement path is shown with a blue line
- A message appears when you *reach the end* or get *stuck*

--------------------------------------------------------------------------------------------------------------

#  How the Maze Works

- The maze is created using a 2D grid (a matrix).
- Different characters represent different things:
  - S = Start point (yellow)
  - E = End point (green)
  - #= Walls (gray)
  - . = Empty path (light gray)
  
This is the main maze window where the red player starts from the 'S' position.
    ![Maze Step 1](Screenshot%202025-07-08%20225719.png)

After navigating through the path, the red ball reaches the 'E' and success popup appears.    
    ![Maze Step 2](Screenshot%202025-07-08%20225756.png)

When there are no possible moves left,a "you are stuck" popup message is shown.    
    ![Maze Step 3](Screenshot%202025-07-08%20225912.png)

-----------------------------------------------------------------------------------------------------------------

#  How to Run the Project

--> Requirements:
      - Java must be installed 
      - A Java IDE (like VS Code, IntelliJ, or Eclipse)

--> Steps:

1. Clone or download this project  
     https://github.com/Gayatri-Gahtori/Maze-Puzzle

2. Open Program.java file inside your Java IDE

3. Compile and run the file:

```bash
javac Program.java
java Program






