# TileGame

Screenshot
Summary/Description

TileGame, as the project is currently called, allows you to not only navigate through a randomly-generated maze, but also freely generate new mazes as well.

## Build

The project can be built from the source files in one of two ways.

The first is to create a standalone archive file, using either the command: 

    ./gradlew distTar

in order to create a tar file, or the command: 

    ./gradlew distZip
    
to create a zip file.

The archive file will be placed in the TileGame/build/distributions folder, and can be executed by running the executable script in the "bin" folder.

The second method is to run the command: 

    ./gradlew jar

which will create a fully executable jar file in the TileGame/gradle/wrapper folder.


## Download

Currently, there are no executable file downloads.

The project source files can be downloaded through ordinary means using GitHub.

## Execution

### Game Mode

To run the project in game mode, simply enter the command:

    ./gradlew run

without any other parameters or options, into the terminal.

This will start the ordinary game mode, in which you navigate a maze that was randomly generated ahead of time.

### Prim Mode



### Metadata Mode

Currently, metadata mode is not fully implemented, and cannot be run.

## Features

### Level Generation (using Prim)

### Game Features

## Technical description

## Tests
JUnit 5.

### Jacoco

### Google Truth

### Mockito

### Temp Files



# TileGame

To generate a test coverage report, enter

    ./gradlew build jacocoTestReport
into the terminal.


## Build

To create an independent zip distribution file, enter

    ./gradlew distZip

## Download



## Game

No special arguments or flags are required to run the game.

    ./gradlew run

## Prim

Currently, the "buildMaze" property must be present and set to "true" to run Prim.

##### Usage

    ./gradlew run -PbuildMode=prim --args="-r_min <minimum number of rows per maze> -r_max <maximum_number of rows per maze> -c_min <minimum number of columns per maze> -c_max <maximum number of columns per maze> -m <number of mazes generated> -s <random seed> -d <directory to write mazes to>"

##### Example

    ./gradlew run -PbuildMode=prim --args="-r_min 10 -r_max 10 -c_min 20 -c_max 20 -m 5 -s 1 -d /home/ninja/ws/github/TileGame/src/main/resources"

## Metadata

NOTE: This feature is currently incomplete, and cannot be performed at this time.

##### Usage

    ./gradlew run -PbuildMode=metadata

## Problems

##### Cache

When altering data files (e.g. maze layouts), the cache files are not properly updated, which causes the program to not register changes.

/home/ninja-jr/IdeaProjects/TileGame/out/production/resources