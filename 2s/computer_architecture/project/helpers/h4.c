#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>

#define MEM_SIZE 8192 

uint16_t R[16];        // Registradores R0–R15
uint16_t MEM[MEM_SIZE];
uint8_t Z = 0, C = 0;  // FLAGS
int running = 1;
int program_size = MEM_SIZE;
int n = 0;
uint16_t adrs[MEM_SIZE]={0};

/* ===== Macros de extração ===== */
#define OPCODE(i) ((i) & 0xF)
#define RD(i)     (((i) >> 12) & 0xF)
#define RM(i)     (((i) >> 8) & 0xF)
#define RN(i)     (((i) >> 4) & 0xF)
#define COND(i)   (((i) >> 14) & 0x3) // Bits 14-15
#define IMM4(i)   (((i) >> 4) & 0xF)
#define IMM8(i)   (((i) >> 4) & 0xFF)

#define SIMM8(i)  ((int8_t)(((i) >> 4) & 0xFF))

/* ===== Executa uma instrução ===== */
void execute(uint16_t instr) {

    uint16_t opcode = OPCODE(instr);

    switch (opcode) {

        /* 0000 - JMP */
        case 0x0:
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
                R[15] += SIMM8(instr);
            break;
        }

        /* 0010 - LDR Rd, [Rm, #imm] */
        case 0x2: {
            uint16_t addr = R[RM(instr)] + IMM4(instr);
            
            // MMIO - Entrada
            if (addr == 0xF002) { // INT_IN
                int val;
                printf("IN => ");
                scanf("%d", &val);
                R[RD(instr)] = (uint16_t)val;
            } 
            else if (addr == 0xF000) { // CHAR_IN
                char val;
                // Espaço antes do %c ignora whitespaces anteriores
                scanf(" %c", &val); 
                R[RD(instr)] = (uint16_t)val;
            }
            else {
                R[RD(instr)] = MEM[addr];
            }
            break;
        }

        /* 0011 - STR Rn, [Rm, #imm] */
        case 0x3: {
            // STR tem formato especial: Imm(12-15) Rm(8-11) Rn(4-7)
            uint16_t imm_str = (instr >> 12) & 0xF; 
            uint16_t rm = RM(instr);
            uint16_t rn = RN(instr); 
            
            uint16_t addr = R[rm] + imm_str;

            // MMIO - Saída
            if (addr == 0xF003) { // INT_OUT
                printf("OUT <= %d\n", (int16_t)R[rn]);
            }
            else if (addr == 0xF001) { // CHAR_OUT
                printf("OUT <= %c\n", (char)R[rn]);
            }
            else {
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
            C = (res < 0); 
            break;
        }

        /* 1110 - PUSH */
        case 0xE:
            // "SP--; MEM[SP] = Rn"
            // Nota: Rn está nos bits 4-7
            R[14]--;
            MEM[R[14]] = R[RN(instr)];
            adrs[n++] = R[14];
            break;

        /* 1111 - POP / HALT */
        case 0xF:
            if (instr == 0xFFFF) {
                running = 0;
            } else {
                // "Rd = MEM[SP]; SP++"
                R[RD(instr)] = MEM[R[14]];
                R[14]++;
                // adrs[n] = 0;
                n--;
            }
            break;

        default:
            printf("Opcode invalido: %X\n", opcode);
            running = 0;
    }
}

void loadProgram(const char *filename) {
    FILE *file = fopen(filename, "r");
    if (!file) {
        printf("Erro ao abrir arquivo %s\n", filename);
        exit(1);
    }

    char line[256];
    unsigned int addr, instr;

    while (fgets(line, sizeof(line), file)) {
        if (line[0] == '\n' || line[0] == '/' || line[0] == '#') continue;
        
        // Lê formato "ADDR INSTR" ignorando o resto da linha
        if (sscanf(line, "%x %x", &addr, &instr) == 2) {
            if (addr < MEM_SIZE) MEM[addr] = (uint16_t)instr;
            // O HALT (FFFF) marca o fim do bloco de instruções
        if (instr == 0xFFFF && program_size == MEM_SIZE) program_size = addr;
            if(addr > program_size) {
                adrs[n++] = addr; 
            }
        }
    }
    fclose(file);
}

int main(int ) {
    // Altere o nome do arquivo aqui para testar (ex1.hex, ex2.hex...)
    loadProgram("example2.hex"); 

    R[15] = 0;       // PC
    R[14] = 0x2000;  // SP (Pilha começa em 0x2000)

    // Loop principal
    while (running) {
        uint16_t instr = MEM[R[15]];
        R[15]++; 
        execute(instr);
    }

    // --- SAÍDA FORMATADA ---
    printf("\nEstado final dos registradores:\n");
    for (int i = 0; i < 16; i++) {
        char *desc = "";
        if(i==14) desc = " (SP)";
        if(i==15) desc = " (PC)";
        printf("R%-2d%s = 0x%04X\n", i, desc, R[i]);
    }
    printf("Z = %u\nC = %u\n", Z, C);

   // Imprime apenas posições de memória que NÃO são código (depois do HALT)
    // ou endereços específicos que foram alterados.
    // for (int i = 0; i < MEM_SIZE; i++) {
    //     // Filtro: Imprime se o endereço for maior que o endereço do HALT 
    //     // ou se estiver na região da pilha (0x1FF0 para cima)
    //     if (MEM[i] != 0 && (i > program_size || i >= 0x1FF0) && i < 0xF000) {
    //         printf("[ 0x%04X ] = 0x%04X\n", i, MEM[i]);
    //     }
    // }
    for(int i = 0; i < n; i++) {
        printf("[ 0x%04X ] = 0x%04X\n", adrs[i], MEM[adrs[i]]);
    }

    return 0;
}