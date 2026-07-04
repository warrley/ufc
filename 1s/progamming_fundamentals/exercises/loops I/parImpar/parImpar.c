#include <stdio.h>
#include <math.h>

// https://github.com/qxcodefup/arcade/blob/master/base/esconde/Readme.md
int main() {
    int num1;

    printf("\nuntil what number?: ");
    scanf("%d", &num1);

    for(int i = 1; i <= num1; i+=2){
        printf("%d ", i);
    }

    if(num1 % 2 == 0){
        for(int i = num1; i >= 0; i-=2){
            printf("%d ", i);
        }
    } else {
        for(int i = num1 - 1; i >= 0; i-=2){
            printf("%d ", i);
        }
    }

    

    return 0;
}