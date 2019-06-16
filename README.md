# TileGame
To generate a test coverage report, enter

    ./gradlew build jacocoTestReport
into the terminal.
##Game
No special arguments or flags are required to run the game.

    ./gradlew run

##Prim
Currently, the "buildMaze" property must be present and set to "true" to run Prim.

#####Usage

    ./gradlew run -PbuildMaze=true --args="-r <number of rows per maze> -c <number of columns per maze> -m <number of mazes generated> -s <random seed> -d <directory to write mazes to>"

#####Example

    ./gradlew run -PbuildMaze=true --args="-r 7 -c 10 -m 3 -s 0 -d /home/ninja-jr/Documents/Mazes"

