#!/bin/bash

submissionpath=$1  	#/home/sami/Play/Shell-Scripting-Assignment-Files/Workspace/submissions
target=$2		#/home/sami/Play/target
testpath=$3		#/home/sami/Play/Shell-Scripting-Assignment-Files/Workspace/tests
anspath=$4		#/home/sami/Play/Shell-Scripting-Assignment-Files/Workspace/answers



./intro.sh "$1" "$2"

 if [ "$5" = '-noexecute' ]; then
     echo "done"
    else
        ./exc.sh "$2" "$3" "$4"

	./exjav.sh "$2" "$3" "$4"

	./expy.sh "$2" "$3" "$4"

    fi


