#include <stdio.h>
#include <math.h>

//https://github.com/qxcodefup/arcade/blob/master/base/zigzag/Readme.md

int main() {
    int num1, num2;

    printf("\nEnter the start number: ");
    scanf("%d", &num1);

    printf("\nEnter the end number: ");
    scanf("%d", &num2);

    for(int i = num1; i <= num2; i++){
        if(i % 3 == 0){
            if(i % 5 == 0) {
                printf("\nzigzag");
            } else {
            printf("\nzig");
            }
        } else if(i % 5 == 0){
            printf("\nzag");
        } else if(i % 15 == 0) {
            printf("\nzigzag");
        } else {
            printf("\n%d", i);
        }
    }

    return 0;
}