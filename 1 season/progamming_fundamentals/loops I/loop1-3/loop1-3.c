#include <stdio.h>
#include <math.h>

//https://github.com/qxcodefup/arcade/blob/master/base/loop_iv/Readme.md

int main() {
    int num1, num2;

    printf("\nEnter the first limit:  ");
    scanf("%d", &num1);

    printf("\nEnter the second limit:  ");
    scanf("%d", &num2);

    if(num1 > num2) {
        printf("\nENTER A VALID VALUE");
        return 1;
    }

    if(num1 % 2 == 0) {
        num1 = num1 + 1;
    }
    for(int i = num1; i < num2; i += 2){
        printf("%d ", i);
    }

    return 0;
}