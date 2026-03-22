#include <stdio.h>
#include <math.h>

int main() {
    int total, type[50], couples = 0;

    printf("\nEnter the amount animals: ");
    scanf("%d", &total);

    for (int i = 0; i < total; i ++) {
        printf("\nEnter the type of animal (Male = positive) (Female = negative): ");
        scanf("%d", &type[i]);
    }

    for (int i = 0; i < total; i ++) {
        for (int j = 0; j < total; j++) {
            if (fabs(type[i]) == fabs(type[j]) && type[i] * type[j] < 0) {
                couples++;
                type[i] = type[j] = 0;

                break;
            }
        }
    }

    printf("\nformed %d couple(s)", couples);

    return 0;
}
