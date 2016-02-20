#!/bin/bash

for x in tests/*.in; do
    if [ -e ${x%.in}.import ]; then
        java -cp pt.jar:pt:poof-support.jar:. -Dimport=${x%.in}.import -Din=$x -Dout=${x%.in}.outhyp poof.textui.Shell;
    else
        java -cp pt.jar:pt:poof-support.jar:. -Din=$x -Dout=${x%.in}.outhyp poof.textui.Shell ;
    fi

    diff -cB -w ${x%.in}.out ${x%.in}.outhyp > ${x%.in}.diff ;
    if [ -s ${x%.in}.diff ]; then
        echo "FAIL: $x. See file ${x%.in}.diff " ;
        #cat ${x%.in}.diff ;
        #exit 0 ;
    else
        echo -n "."
        rm -f ${x%.in}.diff ${x%.in}.outhyp ; 
    fi
done

rm -f *.dat

echo "Done."

