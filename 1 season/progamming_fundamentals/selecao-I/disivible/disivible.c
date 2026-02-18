#include <stdio.h>
#include <math.h>

int main() {

    int num1;
    int num2;

    printf("\nEnter a number:  ");
    scanf("%d", &num1);

    printf("\nEnter a number to check if it is divisible by %d:  ", num1);
    scanf("%d", &num2);


    if(num1 % num2 == 0){
        printf("\n%d is divisible by %d", num1, num2);
    } else {
        printf("\n%d is not divisible by %d", num1, num2);
    }

    return 0;
}