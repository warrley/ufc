#include <stdio.h>
#include <math.h>

// https://github.com/qxcodefup/arcade/blob/master/base/apertada/Readme.md
int main() {
    int num[5];

    printf("\nEnter a first number: ");
    scanf("%d", &num[0]);

    printf("\nEnter a second number: ");
    scanf("%d", &num[1]);

    printf("\nEnter a third number: ");
    scanf("%d", &num[2]);

    printf("\nEnter a fourth number: ");
    scanf("%d", &num[3]);

    printf("\nEnter a fifth number: ");
    scanf("%d", &num[4]);

    int small = num[0];
    for (int i = 1; i < 5; i++) {
        if(num[i] < small) {
            small = num[i];
        }
    }

    printf("\n%d is the smaller", small);

    return 0;
}