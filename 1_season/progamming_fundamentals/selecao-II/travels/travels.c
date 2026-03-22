#include <stdio.h>
#include <math.h>

// https://github.com/qxcodefup/arcade/blob/master/base/teleferico/Readme.md

int main() {
    double capacity, students, travels;
    
    printf("\nWhat is the total capacity?:  ");
    scanf("%lf", &capacity);

    printf("\nHow many students?:  ");
    scanf("%lf", &students);

    if(capacity < 2 || capacity > 100 || students < 1 || students > 1000) {
        printf("Values outside the permitted limits\n");
        return 1;
    }

    travels = ceil((students)/(capacity - 1));

    printf("\nNeed %0.lf trips", travels);

    return 0;
}