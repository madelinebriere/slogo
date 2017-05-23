## SLogo Project
## Maddie Briere, Jesse Yue, Matthew Barbano and Jimmy Shackford
## Team 07

**Date started:** 

February 20th, 2017 (Start of first sprint)

**Date finished:**

March 2nd, 2017 (2nd sprint)
March 9th, 2017 (3rd sprint)

**Number of hours:** 300 hours
## Overview:

This project addresses the creation of Integrated Development Environments, using the simplified “coding” language Logo. In this project, we will be creating a user-friendly coding environment supporting a simple coding language that is used by beginners. Sample commands and expressions are as follows:
>
FORWARD 50
SUBTRACT X Y
PENUP (Returns a boolean)
>

Within this IDE, the user is able to interact with a simulation -- in most instances of this particular program, this simulation is just a single display with a turtle at the center. Commands can control this turtle, moving it forward a certain number of steps, changing its speed, etc. 

With time, the user should be able to extend the environment, adding on user-defined commands and adding complexity to an initially simple environment. This means that we must add capabilities extending across a wide spectrum. On one hand, we must make the IDE extremely intuitive for beginners. On the other hand, it must be possible to build upon the basic framework of our program to develop and more complex program. This will require flexibility in the addition of new commands (meaning that commands cannot be hard-coded and must be added almost like entries in a dictionary). It will also require that the system be able to expand and grow WHILE running -- it must be able to incorporate user input to “learn.”

The closed elements of this design will be the items “higher on the totem pole.” Abstract superclasses like Actor, Instruction, Simulation, etc. will not be changeable -- adding a new instruction or actor type will not require modification of these superclasses. Rather, these superclasses will be extended to produce new functionalities. For instance, if a new type of actor were to be added to the simulation, we could extend the superclass Actor in this new class to inherit already existing functionality and add on new functionality. This new Actor would be a sibling to the already existing Turtle class. Similarly, adding a new instruction type will not require modification to the Instruction class. Rather, the new Instruction type will be create and extend the Instruction class, allowing for extension without modification of the initial framework. 

## Roles:
1. **Maddie** worked on the backend, writing the main engine for interpretation (Interpreter). She wrote all of the code controlling the backend, starting from when the string command was passed to the backend (non-processed and non-error checked) and ending with the execution of the line. This required the deconstruction of the input into a tree of instruction nodes (holding data and child nodes to display relevant argument relationships), the reading of this tree (using post-order traversal) and the construction of corresponding instructions (which then had to be executed). Several helper classes had to be created to facilitate this process, most notably: InstructionNode, InstructionClassifier, TreeBuilder and TreeExecuter. This included many utility classes as well (such as Argument Reader). She also created the resource files in the interpreter resources folder to flexibly assist the interpreter. In addition, she completed the more complicated commands (the entire Miscellaneous package), including making user-defined functions, executing user-defined functions, making variables, etc. This required the creation of helper classes such as FunctionData and VariableData. In the last sprint, a new component was added to the interpreter for increased flexibility: the BuilderUtil set of classes. These subclasses describe a unique way to “pre-process” certain relevant and more complex nodes. She used these classes to accommodate groupings, lists and recursion.

2. **Matthew** worked on the backend as well, focusing on the creation of Instructions and the various subclasses of Instruction. During the basic implementation sprint: Implemented all commands in Turtle Queries, Math Operations, and Boolean Operations, some in Turtle Commands (in collaboration with Jimmy), and all but TO in Variables/Control Structures/User-Defined Commands (in collaboration with Maddie). Made the MathUtil and PointPolar classes as helpers/utility classes and InstructionData for passing data from the frontend to the backend. Also created the whole SLogoException hierarchy and added throws and try-catch statements to handle exceptions, creating a popup Alert when thrown. During the extensions sprint: Main role was to continue implementing Instruction classes. Implemented all DisplayCommand and MultipleTurtleCommand subclasses, completing the Instruction inheritance hierarchy. Modified the Instruction superclass to handle multiple actors with executeAllToldActors(). Modified and refactored InstructionData with methods relevant to these new commands. Added Collections and associated getters, setters, and methods to ImageProperty (for the indexed turtle images) and the AbstractColorProperty (for the indexed colors).

3. **Jimmy** worked on the frontend. Wrote the design for the LogoController class, the abstract view, abstract model, abstract tool, page view, and the selection bar. Also wrote the AbstractButton, AbstractColorButton, HelpTool, and SettingsTool (in collaboration with Jesse) classes. With Jesse, wrote the ActorView class for displaying an actor. Wrote the TurtleView class for displaying turtle, and the PenView class for displaying the pen. Wrote tool button classes for editing background color, pen color, turtle image, and opening the help page. Made a basic html help page. Wrote most of the Turtle Commands (in collaboration with Matt), and wrote a couple of the Turtle Query Commands (pendown/showing). Wrote the generic property class and the associated properties for it. Implemented the properties. Added animations. Added graphical dynamic updaters and allowed the user to click on a turtle to update its properties. Created the right side-bar.

4. **Jesse** worked on the frontend. Edited the design of View, Abstractbutton, and Controller to make View and Controller Observers and AbstractButton observable. This allows the simulation to be updateable based on button actions. Worked on Controller, SimulationView, and SettingsTool  in collaboration with Jimmy. Also created the InputBox to take in text input, WorkspaceView to display and edit declared variables, VariableData to store variable data, VariableEditor a pop up that allows the user to edit the selected variable, FileTools, and AbstractLanguageButton to change the language. Wrote tool button classes for making new files by implementing tabs, opening files, saving files, and changing language.
Second Sprint: worked on multiple turtles, new selectionbar of buttons, saved commands view, XML parsing, default setting, 



## Resources:
JavaFX 8 Documentation
Java 8 Documentation
Stack Overflow
http://stackoverflow.com/questions/11592635/how-to-get-around-the-lack-of-abstract-fields-in-java
http://alvinalexander.com/java/edu/pj/pj010018
http://stackoverflow.com/questions/5168515/reading-txt-file-from-another-directory
http://stackoverflow.com/questions/11592635/how-to-get-around-the-lack-of-abstract-fields-in-java

Maddie:
Builder Design Pattern: http://www.fluffycat.com/Java-Design-Patterns/Builder/
Factory Design Pattern: http://www.fluffycat.com/Java-Design-Patterns/Abstract-Factory/
Reflection: https://docs.oracle.com/javase/tutorial/reflect/ 
Resource Bundles: http://docs.oracle.com/javase/7/docs/api/java/util/ResourceBundle.html?is-external=true
http://stackoverflow.com/questions/2316220/java-override-object-equals-method
 
Bug: SetShape doesn’t work if 1) change image with GUI, and 2) do “Shape” – not recognized as in the indexed list
 
http://stackoverflow.com/questions/225337/how-do-i-split-a-string-with-any-whitespace-chars-as-delimiters


## Files used to start the project (the class(es) containing main)
**Main.java** (in main package) 

## Files used to test the project
In the testers package:

**ArgumentReaderTester.java:** Used to test the reading of the NumArgs resource file which controlled how many arguments a command would “look for” when the instruction node tree was being created. In the final sprint, this tester was expanded to accommodate user-defined functions as well.

**InstructionClassifierTester.java:** Used to test the reading of the Syntax and language files. This class allows us to make sure that all of the inputs (e.g., fd, 50, make) were being correctly recognized as elements in the resource files and turned into the correct classes (e.g., Forward, Constant, MakeVariable) using reflection. This tester was mostly decommissioned in the final sprint (once reflection was working, we didn’t really need to come back to it).

**TreeBuilderTester.java:** Used to test that the TreeBuilder class was correctly implemented. This class allows us to print out each level of the tree of InstructionNodes to confirm that each command is taking the correct number of arguments and in the correct order. This tester proved EXTREMELY helpful in the final sprint, because it allows us to print out the levels of the instruction as the interpreter sees them. Hence, it is possible to see if a command is taking the wrong number of arguments, something isn’t getting read in, etc. This was really useful for file-reading and user-defined commands.

## Any data or resource files required by the project (including format of non-standard files)
In the data folder:
HelpPage.html - The help page for the user interface:
Resource files:
In resources.interpreter package: (for classes in the Interpreter package)

* Brackets.properties ( Matches each opening bracket to its closing bracket )

* JavaSpeak.properties ( Links every class to its exact location in the project -- required for reflection)

* NumArgs.properties (The number of arguments each command takes, which dictates how the nodes are connected in the instruction tree)

* Groupings.properties ( How to group certain commands -- for example, while ‘sum’ should be layered, like sum sum 10 20 30, when seen in parentheses, commands like ‘fd’ should be made into multiple commands, like fd 50 fd 10 fd 30 )

In resources.languages package:

* Exception.properties - messages and text displayed on Alert popup for SLogoExceptions

* Syntax.properties - Types of expressions (regex)

* English.properties - and several other languages; the commands in these languages



## Any information about using the program (i.e., command-line/applet arguments, key inputs, interesting example data files, or easter eggs)

The input allowed for this program is highly documented, as Logo is a common beginner’s programming language. The basic commands implemented in the first sprint can be found on the CompSci 308 assignment page (http://www.cs.duke.edu/courses/cps108/current/assign/03_slogo/commands.php). These types of input can be split into the following types:

1. Basic Syntax (Variables, Command, Comment, List, etc.)

2. Turtle Queries (XCor -- what is the x coordinate?, YCor -- what is the y coordinate?)

3. Turtle Commands (Forward, Backward, etc.)

4. Math Operations (Sum, Minus, etc.)

5. Boolean Operations (Less, Greater, etc.)

6. User-defined commands (both making and re-using them)

7. Control structures (if statements, for loops, etc.)

8. Variables (the creation of workspace variables like x and y)

9. Display Commands

10. Multiple Turtle Commands

Other useful information:

* Old commands can be clicked on to re-run the commands

* Variables created by the user are displayed and changeable in a side-bar in the workspace

* Certain typos can be corrected by the interpreter’s cleaning method (e.g., extra spaces)

* Mistakes will be announced to the user via a pop-up, telling the user what to fix in order to run a valid command

## Any known bugs, crashes, or problems with the project's functionality

**Back-end bugs**

**Second sprint**
The **variables** are still a bit buggy. The creation of variables works perfectly fine -- the correct values and names will be initialized in the sidebar. It is only when more complicated, nested commands are made that variables begin to break down (e.g., set :x :x 5).

For some reason, **comments** still are not recognized by the InstructionClassifier. This was tested thoroughly and eventually abandoned after too much time was put into the problem.

Commands across **multiple lines** still do not consistently run -- some sort of line cleaning method is required in the interpreter to work with spread out commands.

Some of the functionality is **hard-coded.** Because the components that were hard-coded are so essential to the program (e.g., making a variable), these were deemed reasonable assumptions (for the meantime). Still, these should be re-factored for the next sprint.

**Third (and final) sprint**

All of the bugs listed in the previous sprint were fixed in the following sprint. Variables were fixed using a new structure VariableData and a change in the way variables were initialized (including some nuanced variable scope). Comments were fixed with slight modifications to the InstructionCleaner class and the resource files for syntax. Commands across multiple lines were fixed via the multiple-line input box option. Finally, the hard-coded functionality was moved into separate components (the BuilderUtil classes). Obviously, however, new bugs appeared with the new sprint.

While nested lists work perfectly (they are necessary for most of the more complex commands, **nested groups** are buggy -- often, the interpreter will not match embedded group brackets correctly. This was ranked low on the to-fix list because of its low importance. 

The **recursion** added in this sprint works beautifully for most of the files, but has trouble with some of the more complicated recursive commands. These instruction parse/run procedures don’t properly recognize the base case (for reasons not yet identified). This bug seems unrelated to the variable stack added to accommodate recursion.

The **InstructionCleaner** is still not as functional as would be preferred. Some of the intended functions of the InstructionCleaner could not be implemented. 

Ask and askwith only execute their specified commands in their list argument for the first turtle specified.

Setshape occasionally does not properly update the image list

**Front-end bugs**

Turtle does not cleanly stop at simulation borders - is only a rough border detection algorithm

Running a command only affects the most recently opened tab (this functionality used to work--where a command would only execute on the currently opened tab--but it got messed up somehow. **FIXED**

When you load a new image for an actor, it doesn’t replace the last one and just gets placed over the old one. Moreover, new commands don’t work on the new image. **FIXED**

If you enter a forward/backward command too quickly after the last one and a Timeline animation is still running, the most recently run forward/backward command will not execute. **FIXED**

Only the most recent turtle is able to be clicked and updated in the preferences view.

Only the most recent turtle has the tooltip on hover.

The pen of new turtles overlaps the old turtles

Sidebar information sometimes runs off of the screen (like when you add too many colors to the color palette)

Whenever you set the image color of a turtle from the sidebar, you cannot return the image to the default color

Due to the loaded css file, some of the buttons are transparent and you can’t tell that they are actually buttons. (update actor image button)


## Any extra features included in the project

Multiple tabs, each with a different simulation, can be opened, and code can be saved. Also, the background color, turtle image, and pen color can be changed. This is accomplished through the dropdown menus in the top toolbar. An html help page can be opened through the toolbar.

The back-end implements an instruction-cleaner class that modifies the text in order to remove typos made by the user and heighten the chance of the command “going through.” This keeps the user from having to retype a command after having made a silly and predictable typo.

Animations added to the project. Made simulation classes cloneable so that there could possibly be an undo button in the future. Added the sidebar so that you can graphically update properties of a turtle.

## Your impressions of the assignment to help improve it in the future

Matthew - A challenging and rewarding project overall. Having all commands return double was a helpful simplification. It was tedious implementing the Instruction hierarchy, but rewarding. I learned to improve communication skills, as there were many times when extensive communication was required, and I learned quite a bit about Exception handling as I designed the SLogoException hierarchy.

Maddie - Working with a slightly larger group encouraged more cognizance of communication. It also made it necessary to split up tasks wisely, which was a great learning experience. Overall, a rewarding assignment to complete -- the result is a fully functional IDE! (*Third sprint*) The final sprint encouraged us to improve our design and stretch our project to its limits. We were able to constructively brainstorm modifications to improve our code, learning how to work as a larger team and accommodate a fast-paced, error-prone workflow. Again, this was a very educational experience.

Jimmy - It was interesting to make a design where the front-end didn’t need to know much about the back-end in order to make the project work. Team meetings helped me understand the overall design features of both the front-end and back-end. I really enjoyed seeing how you could make your own language compiler in Java.

Jesse - The project was challenging and fun. It was cool to create our own IDE where we can see our commands come to life.


