#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>

#define MEM_SIZE 8192

uint16_t R[16];        // Registradores R0–R15
uint16_t MEM[MEM_SIZE];
uint8_t Z = 0, C = 0;  // FLAGS
int running = 1;

/* Macros de extração */
#define OPCODE(i) ((i) & 0xF)
#define RD(i)     (((i) >> 12) & 0xF)
#define RM(i)     (((i) >> 8) & 0xF)
#define RN(i)     (((i) >> 4) & 0xF)
#define IMM8(i)   (((i) >> 4) & 0xFF)
#define IMM10(i)  (((i) >> 4) & 0x3FF)
#define IMM12(i)  (((i) >> 4) & 0xFFF)
#define IMM4(i)   (((i) >> 4) & 0xF)
#define COND(i)  (((i) >> 14) & 0xF)
/* Executa uma instrução */
void execute(uint16_t instr) {

    uint16_t opcode = OPCODE(instr);

    switch (opcode) {

        /* 0000 - JMP */
        case 0x0:
            R[15] += IMM12(instr);
            break;

        /* 0001 - JEQ / JNE / JLT / JGE */
        case 0x1: {
            uint8_t cond = COND(instr); // usa Rd como campo de condição
            int jump = 0;

            if (cond == 0x0 && Z == O) jump = 1;          // JEQ
            if (cond == 0x1 && Z == 1) jump = 1;          // JNE
            if (cond == 0x2 && Z == 0 && C == 1) jump = 1; // JLT
            if (cond == 0x3 && (Z == 1 || C == 0)) jump = 1; // JGE

            if (jump)
                R[15] += IMM10(instr);
            break;
        }

        /* 0010 - LDR */
        case 0x2:
            R[RD(instr)] = MEM[R[RM(instr)] + IMM4(instr)];
            break;

        /* 0011 - STR */
        case 0x3:
            MEM[R[RM(instr)] + RD(instr)] = R[RN(instr)];
            break;

        /* 0100 - MOV */
        case 0x4:
            R[RD(instr)] = IMM8(instr);
            break;

        /* 0101 - ADD */
        case 0x5: {
            uint32_t res = R[RM(instr)] + R[RN(instr)];
            R[RD(instr)] = res & 0xFFFF;
            Z = (R[RD(instr)] == 0);
            C = (res > 0xFFFF);
            break;
        }

        /* 0110 - ADDI */
        case 0x6: {
            uint32_t res = R[RM(instr)] + IMM4(instr);
            R[RD(instr)] = res & 0xFFFF;
            Z = (R[RD(instr)] == 0);
            C = (res > 0xFFFF);
            break;
        }

        /* 0111 - SUB */
        case 0x7: {
            int32_t res = R[RM(instr)] - R[RN(instr)];
            R[RD(instr)] = res & 0xFFFF;
            Z = (R[RD(instr)] == 0);
            C = (res < 0);
            break;
        }

        /* 1000 - SUBI */
        case 0x8: {
            int32_t res = R[RM(instr)] - IMM4(instr);
            R[RD(instr)] = res & 0xFFFF;
            Z = (R[RD(instr)] == 0);
            C = (res < 0);
            break;
        }

        /* 1001 - AND */
        case 0x9:
            R[RD(instr)] = R[RM(instr)] & R[RN(instr)];
            Z = (R[RD(instr)] == 0);
            break;

        /* 1010 - OR */
        case 0xA:
            R[RD(instr)] = R[RM(instr)] | R[RN(instr)];
            Z = (R[RD(instr)] == 0);
            break;

        /* 1011 - SHR */
        case 0xB:
            R[RD(instr)] = R[RM(instr)] >> IMM4(instr);
            Z = (R[RD(instr)] == 0);
            break;

        /* 1100 - SHL */
        case 0xC:
            R[RD(instr)] = R[RM(instr)] << IMM4(instr);
            Z = (R[RD(instr)] == 0);
            break;

        /* 1101 - CMP */
        case 0xD: {
            int32_t res = R[RM(instr)] - R[RN(instr)];
            Z = (res == 0);
            C = (res < 0);
            break;
        }

        /* 1110 - PUSH */
        case 0xE:
            MEM[R[14]--] = R[RN(instr)];
            break;

        /* 1111 - POP / HALT */
        case 0xF:
            if (instr == 0xFFFF) {
                running = 0; // HALT
            } else {
                R[RD(instr)] = MEM[++R[14]]; // POP
            }
            break;

        default:
            printf("Opcode invalido: %X\n", opcode);
            running = 0;
    }
}


/* Carrega programa do arquivo */
void loadProgram(const char *filename) {
    FILE *file = fopen(filename, "r");
    if (!file) {
        printf("Erro ao abrir o arquivo!\n");
        exit(1);
    }

    char line[256];
    unsigned int addr, instr;

    while (fgets(line, sizeof(line), file)) {

        // Ignora linhas vazias ou comentários puros
        if (line[0] == '\n' || line[0] == '/' || line[0] == '#')
            continue;

        // Lê apenas os dois primeiros valores hexadecimais
        if (sscanf(line, "%x %x", &addr, &instr) == 2) {
            MEM[addr] = (uint16_t)instr;
        }
    }

    fclose(file);
}


int main() {

    loadProgram("example3.hex");

    R[15] = 0;            // PC
    R[14] = MEM_SIZE;


    while (running) {
        uint16_t instr = MEM[R[15]];
        R[15]++;
        execute(instr);
    }

    printf("\nEstado final dos registradores:\n");
    for (int i = 0; i < 16; i++) {
        if (i == 15)
            printf("R%-2d (PC) = 0x%04X\n", i, R[i]);
        else if (i == 14)
            printf("R%-2d (SP) = 0x%04X\n", i, R[i]);
        else
            printf("R%-2d      = 0x%04X\n", i, R[i]);
    }

    printf("Z = %u\n", Z);
    printf("C = %u\n", C);

    return 0;
}