#include <stdio.h>
#include <stdint.h>
#include <stdbool.h>

#define MEM_SIZE 8192 

uint16_t R[16];        
uint16_t MEM[MEM_SIZE] = {0};
bool visited[MEM_SIZE] = {0}; 
uint8_t Z = 0, C = 0;   
int running = 1;

// Breakpoints
int breakpoints[100];
int num_breakpoints = 0;

// Extração de dados
#define OPCODE(i) ((i) & 0xF)
#define RD(i)     (((i) >> 12) & 0xF) 
#define RM(i)     (((i) >> 8) & 0xF)  
#define RN(i)     (((i) >> 4) & 0xF)  
#define IMM4(i)   (((i) >> 4) & 0xF)  
#define IMM8(i)   (((i) >> 4) & 0xFF) 
#define COND(i)   (((i) >> 14) & 0x3)

// Extrações com sinal
#define SIMM10(i) (((int32_t)(((i) >> 4) & 0x3FF) << 22) >> 22)
#define SIMM12(i) (((int32_t)(((i) >> 4) & 0xFFF) << 20) >> 20)

// Imprime o estado do processador
void print_state() {
    for (int i = 0; i < 16; i++) {
        printf("R%d = 0x%04hX\n", i, R[i]);
    }
    printf("Z = %d\nC = %d\n", Z, C);

    for(int i = 0; i < MEM_SIZE; i++) {
        if (visited[i] && i < R[14]) {
            printf("[0x%04hX] = 0x%04hX\n", i, MEM[i]);
        }
    }
    if (R[14] < 0x2000) {
        for (int i = 0x1FFF; i >= R[14]; i--) {
            printf("[0x%04hX] = 0x%04hX\n", i, MEM[i]);
        }
    }
}
// executa instrução
void execute(uint16_t instr) {
    uint16_t opcode = OPCODE(instr);

    switch (opcode) {
        // JMP
        case 0x0: 
            R[15] += SIMM12(instr);
            break;
         // J<cond>
        case 0x1: {
            uint8_t cond = COND(instr);
            int jump = 0;
            // 00=JEQ 01=JNE 10=JLT 11=JGE
            if (cond == 0x0 && Z == 1) jump = 1;              
            if (cond == 0x1 && Z == 0) jump = 1;              
            if (cond == 0x2 && Z == 0 && C == 1) jump = 1;    
            if (cond == 0x3 && (Z == 1 || C == 0)) jump = 1;  
            if (jump) R[15] += SIMM10(instr);
            break;
        }
        // LDR
        case 0x2: { 
            uint16_t addr = R[RM(instr)] + IMM4(instr);
            
            if (addr == 0xF002) { // INT_IN 
                int val;
                scanf("%d", &val);
                R[RD(instr)] = (uint16_t)val;
            } 
            else if (addr == 0xF000) { // CHAR_IN 
                char val;
                scanf(" %c", &val); 
                R[RD(instr)] = (uint16_t)val;
            }
            else {
                if(addr < MEM_SIZE) {
                    R[RD(instr)] = MEM[addr];
                    visited[addr] = true; 
                }
            }
            break;
        }
        // STR
        case 0x3: { 
            uint16_t imm_str = (instr >> 12) & 0xF; 
            uint16_t rm = RM(instr);
            uint16_t rn = RN(instr); 
            uint16_t addr = R[rm] + imm_str;

            if (addr == 0xF003) { // INT_OUT 
                printf("%d\n", (int16_t)R[rn]); 
            }
            else if (addr == 0xF001) { 
                printf("%c\n", (char)R[rn]);
            }
            else {
                if (addr < MEM_SIZE) {
                    MEM[addr] = R[rn];
                    visited[addr] = true;
                }
            }
            break;
        }
        // MOV
        case 0x4: 
            R[RD(instr)] = IMM8(instr);
            break;
        // ADD
        case 0x5: { 
            uint32_t res = R[RM(instr)] + R[RN(instr)];
            R[RD(instr)] = res & 0xFFFF;
            Z = (R[RD(instr)] == 0);
            C = (res > 0xFFFF);
            break;
        }
        // ADDI
        case 0x6: { 
            uint32_t res = R[RM(instr)] + IMM4(instr);
            R[RD(instr)] = res & 0xFFFF;
            Z = (R[RD(instr)] == 0);
            C = (res > 0xFFFF);
            break;
        }
        // SUB
        case 0x7: { 
            int32_t res = R[RM(instr)] - R[RN(instr)];
            R[RD(instr)] = res & 0xFFFF;
            Z = (R[RD(instr)] == 0);
            C = (res < 0); 
            break;
        }
        // SUBI
        case 0x8: { 
            int32_t res = R[RM(instr)] - IMM4(instr);
            R[RD(instr)] = res & 0xFFFF;
            Z = (R[RD(instr)] == 0);
            C = (res < 0);
            break;
        }
        // AND
        case 0x9: 
            R[RD(instr)] = R[RM(instr)] & R[RN(instr)];
            Z = (R[RD(instr)] == 0);
            break;
        // OR
        case 0xA: 
            R[RD(instr)] = R[RM(instr)] | R[RN(instr)];
            Z = (R[RD(instr)] == 0);
            break;
        // SHR
        case 0xB: 
            R[RD(instr)] = R[RM(instr)] >> IMM4(instr);
            Z = (R[RD(instr)] == 0);
            break;
        // SHL
        case 0xC: 
            R[RD(instr)] = R[RM(instr)] << IMM4(instr);
            Z = (R[RD(instr)] == 0);
            break;
        // CMP
        case 0xD: { 
            int32_t res = R[RM(instr)] - R[RN(instr)];
            Z = (res == 0); 
            C = (res < 0);  
            break;
        }
        // PUSH
        case 0xE: 
            R[14]--;
            if (R[14] < MEM_SIZE) MEM[R[14]] = R[RN(instr)];
            break;
        // POP / HALT
        case 0xF: 
            if (instr == 0xFFFF) {
                running = 0;
            } else {
                if (R[14] < MEM_SIZE) R[RD(instr)] = MEM[R[14]];
                R[14]++;
            }
            break;

        default:
            running = 0;
    }
}

void loadProgram() {
    char line[256]; 

    // Leitura de breakpoints
    if (scanf("%d", &num_breakpoints) != 1) num_breakpoints = 0;
    for(int i = 0; i < num_breakpoints; i++) {
        scanf("%d", &breakpoints[i]); 
    }

    // Limpa o buffer
    int c;
    while ((c = getchar()) != '\n' && c != EOF);

    unsigned int addr, instr;
    while (fgets(line, sizeof(line), stdin)) {
        
        if (line[0] == '\n' || line[0] == '/' || line[0] == '#') continue;
        if (sscanf(line, "%x %x", &addr, &instr) == 2) {
            if (addr == 0x0000 && instr == 0x0000) break; 
            if (addr < MEM_SIZE) MEM[addr] = (uint16_t)instr;
        }
    }
}

int main() {
    loadProgram(); 

    R[15] = 0;       // PC
    R[14] = 0x2000;  // SP 

    while (running) {
       
        uint16_t pc_antigo = R[15];
        if (pc_antigo >= MEM_SIZE) break;
        uint16_t instr = MEM[pc_antigo];
        R[15]++; 

        execute(instr);

        for(int i = 0; i < num_breakpoints; i++) {
            if(pc_antigo == breakpoints[i]) {
                print_state();
            }
        }
    }
    return 0;
}