#include <stdio.h>
#include <math.h>

// https://github.com/qxcodefup/arcade/blob/master/base/cobra/Readme.md

int main() {
    int board, x, y, time, xx, yy;
    char direction;
    
    printf("\nEnter the board size: ");
    scanf("\n%d", &board);

    printf("\nEnter the x initial: ");
    scanf("\n%d", &x);

    printf("\nEnter the y initial: ");
    scanf("\n%d", &y);

    printf("\nEnter the snake direction (U = Up), (D = Down), (L = Left), (R = Right): ");
    scanf("\n%c", &direction);

    printf("\nEnter the distraction time: ");
    scanf("\n%d", &time);

    if(board < 0 || board > 1000 || x < 0 || x > 1000 || y < 0 || y > 1000 || time < 0 || time > 1000) {
        printf("\nEnter a valid values");
        return 1;
    }

    if(direction == 'D'){
        yy = y + time;
        xx = x;
        if(yy > board) {
            yy = yy - board;
        }
    } else if(direction == 'U') {
        yy = y - time;
        xx = x;
        if(yy < 0) {
            yy = board - fabs(yy);
        }
    } else if(direction == 'L') {
        yy = y;
        xx = x - time;
        if(xx < 0) {
            xx = board - fabs(xx);
        }
    } else if(direction == 'R') {
        yy = y;
        xx = x + time;
        if(xx > board) {
            xx = xx - board;
        }
    }

    printf("\nThe final position is (%d,%d)", xx, yy);

    return 0;
}