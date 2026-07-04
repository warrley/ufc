#include <stdio.h>
#include <math.h>

// https://github.com/qxcodefup/arcade/blob/master/base/salario/Readme.md 

int main() {
    double money;

    printf("\nHow much do you earn monthly?:  ");
    scanf("%lf", &money);

    if(money <= 1000) {
        money = money * 1.2;
    } else if(money <= 1500) {
        money = money * 1.15;
    } else if(money <= 2000) {
        money = money * 1.1;
    } else if(money > 2000) {
        money = money * 1.05;
    }

    printf("\nYou will receive %.2lf", money);

    return 0;
}