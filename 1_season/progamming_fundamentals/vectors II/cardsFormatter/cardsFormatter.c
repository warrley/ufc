#include <stdio.h>
#include <ctype.h>
#include <math.h>

// https://github.com/qxcodefup/arcade/blob/master/base/cartas/Readme.md

int main() {
    int cards, card[13];

    printf("\nEnter the number of cards in your hand: ");
    scanf("%d", &cards);
    int last = cards - 1;

    if(cards > 13 || cards < 0) {
        printf("\nEnter a number valid");
        return 1;
    }

    for (int i = 0; i < cards; i ++) {
        printf("\nEnter the number of your card: ");
        scanf("%d", &card[i]);
        if(card[i] > 13 || card[i] < 1) {
            printf("\nEnter a valid card");
            return 1;
        }
    }

    printf("[ ");
    for (int i = 0; i < cards; i ++) {
        if(card[i] == 1) {
            printf("A");
        } else if (card[i] == 11) {
            printf("J");
        } else if (card[i] == 12) {
            printf("Q");
        } else if (card[i] == 13) {
            printf("K");
        } else {
            printf("%d", card[i]);
        }
        if(card[i] == card[cards - 1]) {
            printf(" ");
        } else {
            printf(", ");
        }
    }
    printf("]");
    return 0;
}