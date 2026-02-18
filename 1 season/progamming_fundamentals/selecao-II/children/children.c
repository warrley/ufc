#include <stdio.h>
#include <math.h>

//https://github.com/qxcodefup/arcade/blob/master/base/monica/Readme.md

int main() {
    int mother, sun1, sun2, sun3, oldest;

    printf("\nEnter mother's age: ");
    scanf("%d", &mother);

    printf("\nEnter sun 1 age: ");
    scanf("%d", &sun1);

    printf("\nEnter sun 2 age: ");
    scanf("%d", &sun2);

        sun3 = mother - (sun1 + sun2);

    if(mother >= 40 && mother <= 110 && sun1 != sun2 && sun1 > 0 && sun2 > 0 && sun1 < mother && sun2 < mother) {
        if(sun3 > sun1 && sun3 > sun2){
            oldest = sun3;
        } else if(sun2 > sun1 && sun2 > sun3) {
            oldest = sun2;
        } else {
            oldest = sun1;
        }
    }

    printf("\nThe age of the eldest son is: %d", oldest);

    return 0;
}