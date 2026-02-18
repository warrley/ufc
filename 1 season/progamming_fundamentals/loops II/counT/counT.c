#include <stdio.h>

// https://github.com/qxcodefup/arcade/blob/master/base/concentra/Readme.md
int main() {
    int num1, num2;

    printf("\nEnter the first number: ");
    scanf("%d", &num1);

    printf("\nEnter the second number: ");
    scanf("%d", &num2);

    printf("[");

    for (int a = num1, b = num2; b >= num1; a++, b--){
        printf(" %d %d", a, b);
    }

    printf("]");

    return 0;
}