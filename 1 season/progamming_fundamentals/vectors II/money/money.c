#include <stdio.h>


int main() {
    double money, values[]={100, 50, 20, 10, 5, 2, 1, 0.5, 0.25, 0.1, 0.05};
    int quantity = 0;

    printf("\nHow much money?: ");
    scanf("%lf", &money);

    for (int i = 0; i < 11; i++) {
        quantity = (int)(money / values[i]);
        (quantity > 0) && (printf("\n%d de %.2lf",quantity, values[i]));
        money = money - (values[i] * quantity);
        quantity = 0; 
    }

    (money <= 0.01) && (printf("\nMissing %.2lf", money));

    return 0;
}