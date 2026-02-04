#include <stdio.h>

// https://github.com/qxcodefup/arcade/blob/master/base/dieta/Readme.md

int main() {
    int days, calories[50], res = 0, average;

    printf("\nHow many days him register the calories? ");
    scanf("%d", &days);

    for (int i = 1; i <= days; i++) {
        printf("\nHow many calories him consume in day %d: ", i);
        scanf("%d", &calories[i - 1]);
        res += calories[i - 1];
    }
    average = res / days;

    printf("\nHim consume is %d calories per day", average);

    return 0;
}