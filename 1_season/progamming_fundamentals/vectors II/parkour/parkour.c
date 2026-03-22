#include <stdio.h>
#include <math.h>


int main() {
    int peoples, type[50], size, helped = 0, soldier = 0;

    printf("\nHow many peoples? ");
    scanf("%d", &peoples);

    for (int i = 0; i < peoples; i++) {
        printf("\nWho's? (0 = soldier) (1 = doctor) ");
        scanf("%d", &type[i]);
        if(type[i] == 0) {
            soldier++;
        }
    }

    for (int i = 0; i < peoples; i++) {
        if (type[i] == 0 && ((i > 0 && type[i - 1] == 1) || (i < peoples - 1 && type[i + 1] == 1))) {
            helped++;
        }
    }

    printf("\n%d soldiers were not helped", soldier - helped);

    return 0;
}