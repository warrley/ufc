#include <stdio.h>

//https://github.com/qxcodefup/arcade/blob/master/base/fatores/Readme.md

int main() {
    int fator = 2, num, count;
    printf("\nEnter a number: ");
    scanf("%d", &num);

    while(num > 1) {
        count = 0;
        while (num % fator == 0){
            count++;
            num = num / fator;
        }
        if(count > 0) {
            printf("%d is divisor %d times\n", fator, count);
        }
        fator++;
    }

    return 0;
}