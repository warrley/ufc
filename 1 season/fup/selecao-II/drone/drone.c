#include <stdio.h>
#include <math.h>

// https://github.com/qxcodefup/arcade/blob/master/base/drone/Readme.md

int main() {
    int wbox, hbox, lbox;
    int hwin, wwin;

    //box

    printf("\nEnter the width of box:  ");
    scanf("%d", &wbox);

    printf("\nEnter the height of box:  ");
    scanf("%d", &hbox);

    printf("\nEnter the length of box:  ");
    scanf("%d", &lbox);

    //window

    printf("\nEnter the width of window:  ");
    scanf("%d", &wwin);

    printf("\nEnter the height of window:  ");
    scanf("%d", &hwin);

    if((wbox <= wwin && hbox <= hwin) || (wbox <= hwin && hbox <= wwin) ||
       (wbox <= wwin && lbox <= hwin) || (wbox <= hwin && lbox <= wwin) ||
       (hbox <= wwin && lbox <= hwin) || (hbox <= hwin && lbox <= wwin)) {
        printf("Passed\n");
    } else {
        printf("Not passed\n");
    }

    return 0;
}