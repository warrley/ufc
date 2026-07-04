#include <stdio.h>
#include <math.h>

int main() {

    int num1;
    int num2;
    int num3;
    int res;

    printf("\nEnter number 1:  ");
    scanf("%d", &num1);

    printf("\nEnter number 2:  ");
    scanf("%d", &num2);
    
    printf("\nEnter number 3:  ");
    scanf("%d", &num3);

    if ((num1 >= num2 && num1 <= num3) || (num1 >= num3 && num1 <= num2)) {
        res = num1;
    } else if ((num2 >= num1 && num2 <= num3) || (num2 >= num3 && num2 <= num1)) {
        res = num2;
    } else {
        res = num3;
    }

    printf("\n the middle number is:  %d", res);

    return 0;
}