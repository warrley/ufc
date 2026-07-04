#include <stdio.h>
#include <math.h>

// https://github.com/qxcodefup/arcade/blob/master/base/goiabas/Readme.md

int main() {
    int capacity, banana, apple, lemon, minutes;
    
    printf("\nWhat the capacity of basket?:  ");
    scanf("%d", &capacity);

    printf("\nHow many bananas?:  ");
    scanf("%d", &banana);

    printf("\nHow many apples?  ");
    scanf("%d", &apple);

    printf("\nHow many lemons?  ");
    scanf("%d", &lemon);

    minutes = ceil((double) (apple + banana + lemon) / capacity);
    printf("\nIt takes %d minutes", minutes);

    return 0;
}