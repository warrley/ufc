#include <stdio.h>
#include <math.h>

//https://github.com/qxcodefup/arcade/blob/master/base/loop_iv/Readme.md

int main() {
    int num1, num2;

    printf("\nEnter the first limit:  ");
    scanf("%d", &num1);

    printf("\nEnter the second limit:  ");
    scanf("%d", &num2);

    if (num1 < num2) {
        for (int i = num1; i < num2; i++) {
            printf("%d ", i);
        }
    } 
    // Se num1 for mnum1ior que num2, decrementnum1
    else {
        for (int i = num1; i > num2; i--) {
            printf("%d ", i);
        }
    }

    return 0;
}