## Sodoku Solver
This program solves sodoku puzzles. It is run from the command line, and reads in puzzles given 
by the user. It is able to solve most puzzles in less than a second, regardless of size. It utilizes 
a variety of common solving techniques.

## Puzzle Types
This program can only solve square puzzles of normal sizes. This includes 4x4, 9x9, 16x16, 25x25, 36x36, etc.
Puzzles are defined in the following way: 

Line 1: the number of lines in the puzzle
Line 2: The characters used in the puzzle
Line 3+: The puzzle

Examples of how puzzles should be formatted are included in this repo.

## Directions
Run the program from the command line. Puzzles can be input in one of two ways. If the user only inputs the path of the puzzle,
the ouput will be shown in the console. If the user inputs the path of the puzzle, followed by an output path, the solution 
will be written to the output file. 

## Requirements
This project used the StopWatch class from Apache Commons 3.8.1. I included the jar 
in this repo. Just make sure you add it to the project path and export it with the other
files so there arent any runtime or compilation errors.
