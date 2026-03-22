#include <stdio.h>
#include <ctype.h>

// https://github.com/qxcodefup/arcade/blob/master/base/semana/Readme.md

int main() {
    int day;
    int hour;

    printf("\nNeed Work?");
    printf("\nEnter a day: (1 = Domingo), (2 = Segunda), (3 = Terca), (4 = Quarta), (5 = Quinta), (6 = Sexta), (7 = Sabado):  ");
    scanf("%d", &day);

    printf("\nEnter a time (24h):  ");
    scanf("%d", &hour);

    if(day == 1) {
        printf("\nDon't work");
    } else if(day == 2 || day == 3 || day == 4 || day == 5 || day == 6) {
        if(hour >= 8 && hour <= 11 || hour >= 14 && hour <= 17) {
            printf("\nWork");
        }else {
            printf("\nDon't work");
        }
    } else if(day == 7) {
        if(hour >= 8 && hour <= 11) {
            printf("\nWork");
        }else {
            printf("\nDon't work");
        }
    } else {
        printf("\nEnter a valid value");
    }

    return 0;
}