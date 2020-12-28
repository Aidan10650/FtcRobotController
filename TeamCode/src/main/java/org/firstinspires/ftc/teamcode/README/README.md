## NOTICE

This repository contains the public code for the preparation in the season after SKYSTONE (2019-2020) competition season.

## Welcome!
This GitHub repository contains the source code that is used for **Team 10650 Biohazards**. Download/clone the entire project to your local computer.
********
********
## Getting Started

This project was conducted completely inside of IntelliJ IDEA, no other IDE has been tested. Download IntelliJ IDEA [here](https://dud.link)

To get started with writing code inside of this repository it is first necessary to install the most recent [FTC Repository](https://github.com/FIRST-Tech-Challenge). Once this is downloaded open it up inside of IntelliJ as a project from Github. Once it is opened, delete everything after the file java in the directory structure of Teamcode/src/main/java

The next step in setting up this connection is to download the most recent simulator version from [this Repository](https://github.com/Aidan10650). This code should be ready to go so all that needs to be done is to open it up as an IntelliJ project.

Refer to the sections below on how to continue.

## Downloading the Project

* If you are a git user, you can clone the most current version of the repository:

<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;git clone https://github.com/Aidan10650/TopLevelTeamCode.git</p>

## Connecting to the robot controller

IntelliJ offers users to connect folders outside of a project to a chosen folder inside of the project. Find the _TeamCode/src/main/java/_ folder and make this a module and make the TopLevelTeamCode a source folder

## Connecting to the simulator

Repeat the same process as with the robot controller except connect the folder directly to TeamCode

### User Documentation and Tutorials

Currently there is not a tutorial, but there may be in the future

***
#Credit
***
Original simulator was used from **FTC Team Beta**. This was edited for the upcoming season, but the heavy lifting of connecting motors to a 2d object was done by them. The original repository can be found [here](https://dud.link)
**************************************************************************************
# Release Information
**************************************************************************************
Version 1.0.1 (built on 7.12.20)

Changes include:
 * The addition of a second controller to the simulator (allowing for a driver and manipulator)
 * Reduction of redundancies in the complex op mode
 * Changes to the max power that an orientation calc can have (now capped at 1 to -1)

Version 1.0 (built on 7.1.20)

Changes include:
 * Initial version

***
##Why?
***
* Problems
- [ ] All previous testing was on the robot
- [ ] The integration of sensors into the game code was haphazard
- [ ] Drive code and sensor code had to be manually mixed for each use
- [ ] The team could not have two programmers
- [ ] No robot = no programming
- [ ] The team can’t attempt any complexity because of limited time on the robot
- [ ] The team is forced to focus on the basics 
- [ ] Difficult to train new programmers
- [ ] Mystery code was throughout the code
- [ ] Lots of bandaid code 
- [ ] Example: gyro was reversed 4 times
- [ ] Code Organization was bad
- [ ] There were bad threading attempts 

* Solutions
- [ ] Simulator is like CAD for programming
- [ ] Fail fast, without the robot
- [ ] Alternate game code approaches easy to test, and then try on robot
- [ ] Webcam integration to game code
- [ ] Experimentation allows for innovation
- [ ] Teleop with auton features
- [ ] Show simulator off in booth
- [ ] Someone on the team can do 3D robot
- [ ] Rapid iterate at completion with allies 
- [ ] Add allies robot’s auton based on vision, so it can play 
- [ ] Rapid prototyping 
- [ ] Standardized way to add function and share undoing other game code
