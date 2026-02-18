#include <stdio.h>
#include <math.h>

int main() {

    int num;

    printf("Type a number");
    scanf("%d", &num);

    if(num > 0) {
        printf("+");
    } else if(num < 0) {
        printf("-");
    } else {
        printf("0");
    }

    return 0;
}