#include <stdio.h>
#include <ctype.h>

// Função para determinar o vencedor
char determineWinner(char p1, char p2) {
    if (p1 == p2) {
        return 'D'; // Draw (Empate)
    }
    
    // Condições de vitória do jogador 2
    if ((p1 == 'S' && p2 == 'R') ||
        (p1 == 'R' && p2 == 'P') ||
        (p1 == 'P' && p2 == 'S')) {
        return '2'; // Player 2 wins
    }

    return '1'; // Player 1 wins
}

// Função para obter e validar a entrada do usuário
char getValidInput(int playerNumber) {
    char choice;
    while (1) {
        printf("Jogador %d, escolha (S) para tesoura, (R) para pedra, (P) para papel: ", playerNumber);
        scanf(" %c", &choice);
        choice = toupper(choice);

        if (choice == 'S' || choice == 'R' || choice == 'P') {
            return choice;
        }
        printf("Entrada inválida! Tente novamente.\n");
    }
}

int main() {
    char guess1 = getValidInput(1);
    char guess2 = getValidInput(2);

    char result = determineWinner(guess1, guess2);

    if (result == 'D') {
        printf("O jogo terminou em empate!\n");
    } else {
        printf("O Jogador %c venceu!\n", result);
    }

    return 0;
}
