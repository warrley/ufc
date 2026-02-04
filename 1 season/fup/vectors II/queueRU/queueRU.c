#include <stdio.h>

int main() {
    int peoples, type[50];

    printf("\nHow many peoples in the queue?: ");
    scanf("%d", &peoples);

    for (int i = 0; i < peoples; i++) {
        printf("\nWho's? (Even - Servents) (Odd - Student)");
        scanf("%d", &type[i]);
    }

    printf("[ ");
    for (int i = 0; i < peoples; i++) {
        if(type[i] % 2 != 0) {
            printf("%d ", type[i]);
        }
    }
    printf("]");

    printf("\n[ ");
    for (int i = 0; i < peoples; i++) {
        if(type[i] % 2 == 0) {
            printf("%d ", type[i]);
        }
    }
    printf("]");

    return 0;
}