#include <stdio.h>
#include <math.h>

// https://github.com/qxcodefup/arcade/blob/master/base/ceu/Readme.md
int main() {
    int num1;

    printf("\nWhere is the stone? ");
    scanf("%d", &num1);

    for(int i = 0; i < 10; i++) {
        if(i == num1) {
            i++;
        }
        printf("%d ", i);
    }
    
    if(num1 != 10){
        printf("sky");
    }

    return 0;
}