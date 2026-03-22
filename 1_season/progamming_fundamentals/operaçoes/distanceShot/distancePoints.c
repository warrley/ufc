#include <stdio.h>
#include <math.h>

int main() {

    double x1;
    double x2;
    double y1;
    double y2;

    printf("\nType a x1 cordenate\n");
    scanf("%lf", &x1);

    printf("\nType a y1 cordenate\n");
    scanf("%lf", &y1);

    printf("\nType a x2 cordenate\n");
    scanf("%lf", &x2);

    printf("\nType a y2 cordenate\n");
    scanf("%lf", &y2);

    double result = sqrt(pow((x2 - x1), 2) + pow((y2 - y1), 2));

    printf("The distance is:  %.2lf", result);

    return 0;
}