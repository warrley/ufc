#include <stdio.h>

// https://github.com/qxcodefup/arcade/blob/master/base/semana/Readme.md

int main() {
    int s1, s2, s3;

    printf("\nEnter a side 1 of triangle:  ");
    scanf("%d", &s1);

    printf("\nEnter a side 2 of triangle:  ");
    scanf("%d", &s2);

    printf("\nEnter a side 3 of triangle:  ");
    scanf("%d", &s3);


    if(s1 >= s2 + s3 || s2 >= s1 + s3 || s3 >= s1 + s2) {
        printf("\nNot possible");
    }else {
        printf("\nPossilbe");
    }

    return 0;
}