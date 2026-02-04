#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>

#define MEM_SIZE 65536 // Aumentado para suportar endereçamento de 16-bit completo se necessário, mas usaremos lógica de IO

uint16_t R[16];        // Registradores R0–R15
uint16_t MEM[MEM_SIZE];
uint8_t Z = 0, C = 0;  // FLAGS
int running = 1;
uint8_t halt_end = 0x0;
/* ===== Macros de extração (CORRIGIDAS) ===== */
#define OPCODE(i) ((i) & 0xF)
#define RD(i)     (((i) >> 12) & 0xF)
#define RM(i)     (((i) >> 8) & 0xF)
#define RN(i)     (((i) >> 4) & 0xF)
// CORREÇÃO: COND pega bits 14-15
#define COND(i)   (((i) >> 14) & 0x3) 
#define IMM4(i)   (((i) >> 4) & 0xF)
#define IMM8(i)   (((i) >> 4) & 0xFF)
#define SIMM8(i)  ((int8_t)(((i) >> 4) & 0xFF))

/* ===== Executa uma instrução ===== */
void execute(uint16_t instr) {

    uint16_t opcode = OPCODE(instr);

    switch (opcode) {

        /* 0000 - JMP */
        case 0x0:
            // CORREÇÃO: Removemos o -1. Salto relativo ao PC já incrementado
            R[15] += SIMM8(instr);
            break;

        /* 0001 - JEQ / JNE / JLT / JGE */
        case 0x1: {
            uint8_t cond = COND(instr);
            int jump = 0;

            // 00=JEQ, 01=JNE, 10=JLT, 11=JGE
            if (cond == 0 && Z == 1) jump = 1;              // JEQ
            if (cond == 1 && Z == 0) jump = 1;              // JNE
            if (cond == 2 && Z == 0 && C == 1) jump = 1;    // JLT
            if (cond == 3 && (Z == 1 || C == 0)) jump = 1;  // JGE

            if (jump)
                R[15] += SIMM8(instr); // CORREÇÃO: Sem -1
            break;
        }

        /* 0010 - LDR Rd, [Rm, #imm] (COM IO MAPPED) */
        case 0x2: {
            uint16_t addr = R[RM(instr)] + IMM4(instr);
            
            // Lógica de MMIO (Entrada)
            if (addr == 0xF002) { // INT_IN
                int val;
                printf("IN => ");
                scanf("%d", &val);
                R[RD(instr)] = (uint16_t)val;
            } 
            else if (addr == 0xF000) { // CHAR_IN
                char val;
                scanf(" %c", &val);
                R[RD(instr)] = (uint16_t)val;
            }
            else {
                // Memória normal
                R[RD(instr)] = MEM[addr];
            }
            break;
        }

        /* 0011 - STR Rn, [Rm, #imm] (COM IO MAPPED E CORREÇÃO DE CAMPOS) */
        case 0x3: {
            // CORREÇÃO: Decodificação manual do STR
            uint16_t imm_str = (instr >> 12) & 0xF; 
            uint16_t rm = RM(instr);
            uint16_t rn = RN(instr); 
            
            uint16_t addr = R[rm] + imm_str;

            // Lógica de MMIO (Saída)
            if (addr == 0xF003) { // INT_OUT
                printf("OUT <= %d\n", (int16_t)R[rn]);
            }
            else if (addr == 0xF001) { // CHAR_OUT
                printf("OUT <= %c\n", (char)R[rn]);
            }
            else {
                // Memória normal
                MEM[addr] = R[rn];
            }
            break;
        }

        /* 0100 - MOV Rd, #imm */
        case 0x4:
            R[RD(instr)] = IMM8(instr);
            break;

        /* 0101 - ADD Rd, Rm, Rn */
        case 0x5: {
            uint32_t res = R[RM(instr)] + R[RN(instr)];
            R[RD(instr)] = res & 0xFFFF;
            Z = (R[RD(instr)] == 0);
            C = (res > 0xFFFF);
            break;
        }

        /* 0110 - ADDI Rd, Rm, #imm */
        case 0x6: {
            uint32_t res = R[RM(instr)] + IMM4(instr);
            R[RD(instr)] = res & 0xFFFF;
            Z = (R[RD(instr)] == 0);
            C = (res > 0xFFFF);
            break;
        }

        /* 0111 - SUB Rd, Rm, Rn */
        case 0x7: {
            int32_t res = R[RM(instr)] - R[RN(instr)];
            R[RD(instr)] = res & 0xFFFF;
            Z = (R[RD(instr)] == 0);
            C = (res < 0);
            break;
        }

        /* 1000 - SUBI Rd, Rm, #imm */
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
            C = (res < 0); // C=1 se Rm < Rn (resultado negativo)
            break;
        }

        /* 1110 - PUSH (Usando Rn bits 4-7) */
        case 0xE:
            R[14]--; 
            MEM[R[14]] = R[RN(instr)];
            break;

        /* 1111 - POP / HALT */
        case 0xF:
            if (instr == 0xFFFF) {
                halt_end = 
                running = 0;
            } else {
                R[RD(instr)] = MEM[R[14]];
                R[14]++;
            }
            break;

        default:
            printf("Opcode invalido: %X\n", opcode);
            running = 0;
    }
}

/* ===== Carrega programa ===== */
void loadProgram(const char *filename) {
    FILE *file = fopen(filename, "r");
    if (!file) {
        printf("Erro ao abrir o arquivo!\n");
        exit(1);
    }

    char line[256];
    unsigned int addr, instr;

    while (fgets(line, sizeof(line), file)) {
        // Ignora comentários e linhas vazias
        if (line[0] == '\n' || line[0] == '/' || line[0] == '#')
            continue;

        // Tenta ler formato "ADDR INSTRUCAO"
        if (sscanf(line, "%x %x", &addr, &instr) == 2) {
            if (addr < MEM_SIZE) {
                MEM[addr] = (uint16_t)instr;
                if(MEM[addr] = 0xFFFF){
                    halt_end = addr;
                }
            }
            
        }

        
    }

    fclose(file);
}

int main() {

    // Certifique-se de salvar o exemplo 4 como "example4.hex"
    loadProgram("example2.hex");

    R[15] = 0;       // PC
    R[14] = 0x2000;  // SP (Pilha começa em 0x2000 no exemplo)

    // Preenche a memória base para evitar lixo se acessar fora do codigo
    // No exemplo 4, o valor 0xF000 é carregado de [R0, #0] onde R0=0xB.
    // O arquivo .hex deve ter a linha: "000B F000" para isso funcionar.
    // Caso o seu .hex não tenha essa linha de dados no final, descomente abaixo:
    // MEM[0x000B] = 0xF000; 

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

    // Mostra o dado armazenado na posição 000B para conferência
     for (int addr = halt_end + 1; addr <= 0x2000; addr++) {
        if(MEM[addr]!=0){
            printf("[0x%04X] = 0x%04X\n", addr, MEM[addr]);
        }
    }

    return 0;
}