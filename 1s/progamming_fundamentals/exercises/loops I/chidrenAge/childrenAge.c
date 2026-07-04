#include <stdio.h>
#include <math.h>

// https://github.com/qxcodefup/arcade/blob/master/base/filhos/Readme.md
int main() {
    int num1, num2, son = 1;

    printf("\nWhat the age of youngest son? ");
    scanf("%d", &num1);

    printf("\nHow many children are there? ");
    scanf("%d", &num2);

    if(num1 < 0 || num2 < 0) {
        printf("\nINVALID DATA");
        return 1;
    }

    for(int i = num1; son < num2; i+=2) {
        printf("\nThe %d son has %d years old", son, i);
        son+=1;
    }

    return 0;
};