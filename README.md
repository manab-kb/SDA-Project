# SDA-Project
A repository maintained as a codebase for an application to be built for the course - Software Design Analysis at TCD.

## Application Prototype (Figma)
- Navigate [here](https://www.figma.com/file/y6XAWtWTdosIOGrHp926Vc/Measure_App_Draft?node-id=0%3A1&t=qPMlzXOy4acdJqWL-1) to find the prototype sketch for the application.
- Navigate [here](https://www.figma.com/proto/y6XAWtWTdosIOGrHp926Vc/Measure_App_Draft?node-id=1%3A10&starting-point-node-id=1%3A10) to find the working (demo) prototype model of the application.

## Abstract & Motivation
We aim to build a clean and aesthetically pleasing measuring application, which uses relevant
sensorsto convert raw sensor data into human readable formatted outputs, and finally display
them as accurate measurements. We have taken inspiration from how iOS has its own
exclusive app to measure dimensions. Though Android possesses the capability to house in
the same set of features, there is no such official app for the same. Different applications are
required to deal with different dimensions. Hence, we have tried to make it all possible by
creating a single application to measure different dimensions.

## Core Functionality
The app we aim to build does exactly what it reads i.e., measures. The core principle of the
application is to make use of all sensors built into the phone by default. The app provides users
with a vast variety of dimensions to choose from. 

The different options provided are:
- Compass: Accelerometer and Magnetic Field Sensor
- Light sensor: Ambient Light Sensor
- Stopwatch: Programming Code
- Dimensions (height, width): Linear Accelerometer Sensor

## Tech Stack
The tech stack that we aim to use throughout the course of this project is as follows:
- Kotlin - Core programming language for business logic
- Java – Unit and implementation testing
- XML – UI/UX elements

We also aim to use Figma to design a basic prototype of the application and then proceed
with the development of an MVP.

## How to setup and run the app ?
### Code Repository Setup
- Clone this repo to your local machine (remember to switch to the *main* branch after cloning, if not set by default)
- Open the project in Android Studio.
- Allow all the background tasks to be completed. If not initiated, navigate to the *gradle* files and run *gradle sync*.
- Run the app on an Android Virtual Device / an android device connected via USB with developer options enabled.

### APK
- Alternatively, navigate to the **[v1.0](https://github.com/manab-kb/SDA-Project/releases/tag/v1.0)** release, and download the APK on your android device to run the app.

# Team Members
- Colm Agnew Gallagher
- Fiona Eguare
- Manab Kunar Biswas
- Yatharth Jain

# Contributing to SDA Project
Hello and welcome! We are so glad that you are interested in contributing to the SDA Project!
We only have a couple of rules and we hope you enjoy the process :)

## Contributing Rules
1. Don't move or delete any files. Only modify them.
2. Add all business logic files [here](https://github.com/manab-kb/SDA-Project/tree/main/app/src/main/java/com/example/measure).
3. Add all UI files [here](https://github.com/manab-kb/SDA-Project/tree/main/app/src/main/res/layout).

## Contributing Process
1. Fork the repository
2. Clone your forked repository to your computer
3. Head to the issues tab and look for an issue that you like.
4. Once you have decided what issue to work on, give it a shot!
5. Once done, push the code to your forked repository.
6. Head to the Pull Requests tab and click on "Create New Pull Request"
7. On the left of the arrow should be this repo and on the right should be yours.
8. Add a small description to the Pull Request describing what you've done.
9. Mention what Issue you have worked on. If the issue number is #3, you can mention "Closes #3" in the Pull Request description.
10. Submit Pull Request

It's that easy! We hope you enjoy contributing to our repository. Don't hesitate to contact any of the maintainers about any problems!
