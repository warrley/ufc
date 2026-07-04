#include <stdio.h>
#include <math.h>

// https://github.com/qxcodefup/arcade/blob/master/base/loop_i/Readme.md#contexto

int main() {
    int num1, num2;

    printf("\nEnter the inferior limit:  ");
    scanf("%d", &num1);

    printf("\nEnter the superior limit:  ");
    scanf("%d", &num2);

    if(num1 > num2) {
        printf("\nEnter the valid values");
        return 1;
    };

    for(int i = num1; i < num2; i++) {
        printf("%d\n", i);
    }

    return 0;
}