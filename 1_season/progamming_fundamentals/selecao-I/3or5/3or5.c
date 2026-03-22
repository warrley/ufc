#include <stdio.h>
#include <math.h>

int main() {

    int number;

    printf("\nType a number\n");
    scanf("%d", &number);

    if(number == 3 || number == 5) {
        printf("SIM");
    } else {
        printf("NÃO");
    }

    return 0;
}