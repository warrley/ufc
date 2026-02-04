#include <stdio.h>
#include <math.h>

// https://github.com/qxcodefup/arcade/blob/master/base/sapatos/Readme.md
int main() {
    int num1, num2, soma = 0;

    printf("\nEnter a first number: ");
    scanf("%d", &num1);

    printf("\nEnter a second number: ");
    scanf("%d", &num2);	

    if (num1 > num2) {
        printf("\nENTER A VALID VALUE");
        return 1;
    }

    for(int i = num1; i <= num2; i++) {
        if(i % 2 == 0 && i % 3 == 0) {
            soma = soma + i;
            printf("number = %d\n", i);
        }
    }

    printf("%d\n", soma);

    return 0;
}