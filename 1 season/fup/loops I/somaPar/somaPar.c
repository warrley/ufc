#include <stdio.h>
#include <math.h>

// https://github.com/qxcodefup/arcade/blob/master/base/pares/Readme.md

int main() {
    int num1, num2, soma = 0;

    printf("\nEnter a number: ");
    scanf("%d", &num1);

    printf("\nEnter another number: ");
    scanf("%d", &num2);

    if(num1 > num2) {
        printf("\nENTER A VALID VALUE");
        return 1;
    }

    if (num1 % 2 != 0) {
        num1++;
    }

    if(num1 % 2 == 0) {
        for(int i = num1; i <= num2; i += 2){
            soma += i;
        }   
    }

    printf("\nThe sum of all even numbers is %d", soma);

    return 0;
}