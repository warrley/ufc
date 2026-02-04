#include <stdio.h>
#include <ctype.h>

// https://github.com/qxcodefup/arcade/blob/master/base/dindin/Readme.md

int main() {
    int sold, chocolate = 0, lemon = 0, morning = 0, afternoon = 0;
    char flavor[100], time[100];

    printf("\nHow many are sold? ");
    scanf("\n%d", &sold);

    for (int i = 1; i <= sold; i++) {
        printf("\nWhat's flavor? (C - Chocolate) (L - Lemon) ");
        scanf(" %c", &flavor[i - 1]);
        flavor[i - 1] = toupper(flavor[i - 1]);
        if(flavor[i - 1] == 'C') {
            chocolate++;
        } else if(flavor[i - 1] == 'L') {
            lemon++;
        }

        printf("\nAt what time?? (M - Morning) (A - Afternoon) ");
        scanf(" %c", &time[i - 1]);
        time[i - 1] = toupper(time[i - 1]);
        if(time[i - 1] == 'M') {
            morning++;
        } else if(time[i - 1] == 'A') {
            afternoon++;
        }
    }

    if(chocolate > lemon) {
        printf("\nChocolate was sold more than lemon");
    } else if(lemon > chocolate) {
        printf("\nLemon was sold more than chocolate");
    } else {
        printf("\nBoth flavors were sold equally");
    }

    if(morning > afternoon) {
        printf("\nIn the afternoon it was more vague than in the morning");
    } else if(afternoon > morning) {
        printf("\nIn the morning it was more vague than in the afternoon");
    } else {
        printf("\nBoth were equally vague");
    }

    return 0;
}