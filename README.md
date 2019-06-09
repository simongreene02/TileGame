# TileGame
To generate a test coverage report, enter

    ./gradlew build jacocoTestReport
into the terminal.
##Game
##Prim
Currently, all executions of the program will run Prim.

#####Usage

    ./gradlew run --args="-r <number of rows per maze> -c <number of columns per maze> -m <number of mazes generated> -s <random seed> -d <directory to write mazes to>"

#####Example

    ./gradlew run --args="-r 7 -c 10 -m 3 -s 0 -d /home/ninja-jr/Documents/Mazes"

