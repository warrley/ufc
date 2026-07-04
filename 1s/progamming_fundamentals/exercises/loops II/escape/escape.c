#include <stdio.h>

// https://github.com/qxcodefup/arcade/blob/master/base/fuga/Readme.md
int main() {
    int helicopter, policial, fugitive, direction;

    printf("\nWhere is the helicopter?(0-15) ");
    scanf("%d", &helicopter);

    printf("\nWhere is the policial?(0-15) ");
    scanf("%d", &policial);

    printf("\nWhere is the fugitive?(0-15) ");
    scanf("%d", &fugitive);
    
    printf("\nWhich direction is he going?(-1 = clockwise) (1 = counterclockwise) ");
    scanf("%d", &direction);

    if(direction != 1 && direction != -1){
        printf("\nINSERT A VALID VALUES!!");
        return 1;
    }

    if(direction == -1) {
        if(policial < fugitive){
            printf("\nLose");
        } else {
            printf("\nWin");
        }
    } else if(direction == 1) {
        if(policial > fugitive) {
            printf("\nLose");
        } else {
            printf("\nWin");
        }
    }

    return 0;
}