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

    ./gradlew run -PbuildMaze=true --args="-r_min <minimum number of rows per maze> -r_max <maximum_number of rows per maze> -c_min <minimum number of columns per maze> -c_max <maximum number of columns per maze> -m <number of mazes generated> -s <random seed> -d <directory to write mazes to>"

#####Example

    ./gradlew run -PbuildMaze=true --args="-r_min 7 -r_max 9 -c_min 10 -c_max 20 -m 3 -s 0 -d /home/ninja-jr/Documents/Mazes"

##Problems

#####Cache
When altering data files (e.g. maze layouts), the cache files are not properly updated, which causes the program to not register changes.

/home/ninja-jr/IdeaProjects/TileGame/out/production/resources