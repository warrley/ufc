#include <stdio.h>
#include <math.h>

int main(){

    int num1;
    int num2;
    char opr;
    int result;

    printf("\nChoose a number:  ");
    scanf("%d", &num1);

    printf("\nChoose another number:  ");
    scanf("%d", &num2);

    printf("\nChoose a operation (+ - / *)");
    scanf(" %c", &opr);

    switch (opr)
    {
    case '+':
        result = num1 + num2;
        break;
    case '-': 
        result = num1 - num2;
        break;
    case '/':
        if(num2 == 0) {
            printf("\nError: Division by zero");
            return 1;
        } 
        result = num1 / num2;
        break;
    case '*':
        result = num1 * num2;
        break;
    default:
        printf("\nType a number");
        break;
    }

    printf("\nThe result is:  %d", result);

    return 0;
}