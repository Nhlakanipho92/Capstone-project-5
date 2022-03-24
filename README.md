# Capstone-project-5
## Poised Project Final
## Description

Description
The Poised Database Project is a program written the Java language that was created to fulfill the level 3 task requirements for the Hyperion Development Software Engineering Bootcamp that I am enrolled in. The aim of this project was to enhance the previous 'Poised Project Final', by incorporating database programmming into the project management system in place of external text files to store data. A small structural engineering company could use this program to keep track of various building projects that they are working on. A database called 'PoisePMS' was created to store project data, and the dependency diagram, as well as the Entity Relationship diagram in this repository, show the relationships between the tables in the PoisePMS database. Code was written in the Java language to then interact with the database, as well as provide a main user menu.

Functionality
Inside the 'src/Poised Package' folder of this projects contents, there are five Java classes that run the project management system for the user. These will be explained briefly to outline the overall functionality of the program:

The 'PoisedInputChecks.java' class file is a superclass that basically contains code to check different types of user inputs that may be used with the program. It has three methods that check whether a user has correctly entered a string (list of words or sentence), integer (number) or double (number with decimal points) respectively and if not, the user is repeatedly asked to re-enter their input until it is correct. This type of defensive programming ensures that the program is robust and able to deal with user errors.

The 'Person.java' class file contains a constructor and method which allow the user to add a new person object for a project; which basically contains contact details for a particular person (e.g. a contractor, customer etc.). When accessed from the main menu class, this method prompts the user to enter new person information (e.g. name, contact number, address etc.) and then displays the completed person object to the user once completed.

The 'PoisedMenu.java' class file is where the main program is run from. When initiated, it displays a clear menu to the user, with options to view existing files, add a new file, update existing project info, view specific projects, or to find a specific project. Depending on the user choice, methods from the other previously mentioned classes above will be called on to perform the user's desired action. All outputted information is displayed to the user in a clear, easy-to-read format (e.g. new projects, person details etc.). The user is returned to the main menu after each option, until they choose the last numbered option to 'exit' the program.
Each class of code is written to access, edit, view, or display information related to the Poised company, which is stored in the external database 'PoisePMS'.
Usefulness

This Java project is useful because it provides a straightforward, efficient program for a small company to use as a project management system. The comments within the program allow the user a comprehensive understanding of how the code works and the Java Documentation for the program gives the user more working knowledge on the different classes and methods within the Poised program. The use of database programming for the Poised Project is also much more efficient and superior to the previous version of the project whereby the program made use of external text files to store information. The program has been effectively de-bugged and refactored to ensure overall clarity and better functionality.

How can I use it?
Firstly, you need to clone this repository with the Task Manager program and related text files to a local repository on your computer, so that you can access and run the program. If you need help, follow the instructions as set out github help webpage:

https://help.github.com/en/github/creating-cloning-and-archiving-repositories/cloning-a-repository

In order to run this Poised Java program, you will then need to install the Java Development Kit (JDK) onto your computer's operating system (OS):

https://www3.ntu.edu.sg/home/ehchua/programming/howto/JDK_HowTo.html#jdk-install - Use this link as a guide to downloading and installing the JDK.

You will then need to install an integrated development environment (IDE) which is a program that enables you to view, write and run Java code. A link for an IDE called 'Eclipse' is provided below:

https://www.ntu.edu.sg/home/ehchua/programming/howto/EclipseJava_HowTo.html - Use this link as a guide to downloading and installing Eclipse.

In order to get the Server working for this database program, you will also need to instal MySQL from the following link:

https://dev.mysql.com/downloads/mysql/

Contributors
This project was worked on individually by myself for the software engineering bootcamp. However, the helpful mentors from Hyperion Development have reviwed the project and commented on it too!

About
Poised Database Project

Resources
 Readme
Stars
 0 stars
Watchers
 1 watching
Forks
 0 forks
Releases
No releases published
Packages
No packages published
Languages
HTML
68.8%
 
Java
14.1%
 
CSS
8.9%
 
JavaScript
8.2%
© 2022 GitHub, Inc.
Terms
Privacy
Security
Status
Docs
Contact GitHub
Pricing
