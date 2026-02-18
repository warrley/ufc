#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

/* IMAS memory size (8K X 2) */
#define IMAS_MEM_SIZE 		(8192)

/* IMAS instructions */
#define RISC_JMP			(0x0) 	/* HALT 			| Stops processor */
#define RISC_LDR			(0x2)	/* LOAD M(X) 		| AC <= MEMORY[X] */
#define IMAS_STOR_M			(0x3)	/* STOR M(X) 		| MEMORY[X] <= AC */
#define RISC_ADD			(0x5) 	/* STA M(X) 		| MEMORY[X](11:0) = AC */
#define RISC_ADDI			(0x6)	/* ADD M(X) 		| AC <= AC + MEMORY[X] */
#define RISC_SUBI			(0x8)	/* SUB M(X) 		| AC <= AC - MEMORY[X] */
#define RISC_SUB            (0x7)
#define RISC_AND			(0x9)	/* MUL M(X) 		| AC(31:16):MQ(15:0) <= MQ * MEMORY[X] */
#define RISC_OR			    (0xA)	/* DIV M(X) 		| MQ <= AC / MEMORY[X], AC <= AC % MEMORY[X] */
#define RISC_SHR			(0xB) 	/* JZ M(X) 			| PC = (AC == 0) ? X : PC */
#define RISC_SHL			(0xC) 	/* JNZ M(X) 		| PC = (AC != 0) ? X : PC */
#define RISC_CMP			(0xD) 	/* JPOS M(X) 		| PC = (AC >= 0) ? X : PC */
#define RISC_PUSH		    (0xE) 	/* IN 				| AC <= IO */
#define RISC_POP			(0xF) 	/* OUT 				| IO <= AC */

/* IMAS registers and memory definitions */
typedef struct   {
	/* UC */
	uint16_t pc;	/* Program Counter */
	uint16_t mar;	/* Memory Address Register */
	uint16_t ibr;	/* Instruction Buffer Register */
	uint16_t ir;	/* Instruction Register */

	/* ULA */
	int16_t mbr;	/* Memory Buffer Register */
	int16_t ac;		/* Accumulator */
	int16_t mq;		/* Multiplier Quocient */

	/* MEMORY */
	uint16_t memory[IMAS_MEM_SIZE];
} imas_t;

/* Executes a read from memory */
void memory_read(imas_t *imas) {
	// TODO
    int16_t address_mar = imas->mar & 0x0FFF;
    if (address_mar >= IMAS_MEM_SIZE){
        fprintf(stderr, "FAIL: Endereço 0x%03x não encontrado na Memória\n", address_mar);
        imas->mbr = 0;
    }
    else {
        imas->mbr = (int16_t) imas->memory[address_mar];
    }
}

/* Executes a write into memory */
void memory_write(imas_t *imas, bool modify_address) {
    uint16_t address_mar = imas->mar & 0x0FFF;
	if(modify_address) {
		// TODO: Write only operand address field
        uint16_t old = imas->memory[address_mar];
        uint16_t opcode_field = old & 0xF000;               
        uint16_t new_address_field = ((uint16_t)imas->mbr) & 0x0FFF; 
        imas->memory[address_mar] = opcode_field | new_address_field;
	}
	else {
		// TODO
        imas->memory[address_mar] = (uint16_t) ((uint16_t)imas->mbr & 0xFFFF);
	}
}

/* Reads an integer from user */
void io_read(imas_t *imas) {
	printf("IN => "); 
	// TODO: scanf("%hd", &<?>);
    int tmp;
    if (scanf("%hd", &tmp) != 1) {
        imas->mbr = 0;
    } else {
        imas->mbr = (int16_t) tmp;
    }
    imas->ac = imas->mbr;   
}

/* Outputs an integer to user */
void io_write(imas_t *imas) {
	// TODO: printf("OUT => %hd\n", <?>);
    printf("OUT => %hd\n", imas->ac);
}

int main(int argc, char *argv[]) {
	/* Check arguments */
	if(argc < 2) {
		printf("IMAS expects at least 2 arguments.\n");
		return 1;
	}

	/* Open input file */
	FILE *input_file = fopen(argv[1], "r");
	if(!input_file) {
		printf("Error opening %s!\n", argv[1]);
		return 1;
	}

	/* Set breakpoints */
	bool breakpoints[IMAS_MEM_SIZE] = {false};
	for(int i = 2; i < argc; i++) {
		int address = strtol(argv[i], NULL, 16);
		breakpoints[address] = true;
	}
	
	/* Initiate IMAS registers as zero */
	imas_t imas = {0};

	/* Zero IMAS memory */
	memset(&imas.memory, 0x0000, IMAS_MEM_SIZE);

	/* Fill IMAS memory */
	uint16_t address, buffer;
    while(fscanf(input_file, "%hX %hX%*[^\n]", &address, &buffer) == 2) {
		imas.memory[address] = buffer;
    }

	/* Processor running */
	bool imas_halt = false;
	do {
		/* PC before modifications */
		uint16_t original_pc = imas.pc;

		/* Fetch subcycle */
		// TODO: Fetch instruction from memory (like in IAS)
        imas.mar = imas.pc & 0x0FFF;
        memory_read(&imas);
        imas.ibr = (uint16_t) imas.mbr;
        imas.pc = (uint16_t) ((imas.pc + 1) & 0x0FFF);

		/* Decode subcycle */
		// TODO: Put instruction fields in registers
        imas.ir = (uint16_t) ((imas.ibr >> 12) & 0xF);
        imas.mar = (uint16_t) (imas.ibr & 0x0FFF);

		/* Execute subcycle */
		switch(imas.ir) {
		case RISC_JMP:
			imas_halt = true; // caso seja lido um HALT, acabará como loop -> condição que está no final
			break;
		case RISC_LDR:
			memory_read(&imas); // ler o valor da memória no endereço
			imas.ac = imas.mbr; // pega o valor que tá no mbr e joga para o acumulador
			break;
		case IMAS_LOAD_MQ: // copia o valor do MQ para o acumulador
			imas.ac = imas.mq;
			break;
		case IMAS_LOAD_MQ_M: // passa o dado da memória para o MQ
			memory_read(&imas);
			imas.mq = imas.mbr; // copia o valor lido para o MQ
			break;
		case IMAS_STOR_M:
			memory_write(&imas, false); // escreve o valor que está no acumulador no endereço de memória especificado
			break;
		case RISC_ADD: // modifica o campo de endereço de uma instrução na memória
			memory_write(&imas, true); // true porque há uma condição que caso seja true indicará que irá ocorrer uma mudança de endereço
			break;
		case RISC_ADDI:
			memory_read(&imas);  // le o dado contido na memoria
			imas.ac += imas.mbr; // soma o dado com o acumulador
			break;
		case RISC_SUBI:
			memory_read(&imas); // le o dado contido na memoria
			imas.ac -= imas.mbr; // subtrai o dado com o acumulador
			break;
		case IMAS_MUL_M:
			memory_read(&imas);
			int32_t result = (int32_t) imas.mq * (int32_t) imas.mbr; // int32_t -> para um resultado de 32 bits ( é o máximo de bits suportado )
			imas.mq = result & 0xFFFF; // o MQ recebe os 16 bits menos significativos
			imas.ac = (int16_t) ( result >> 16 ) & 0xFFFF;  // o AC recebe os 16 bits mais significativos
			break;
		case IMAS_DIV_M:
			memory_read(&imas);// le o dado contido na memoria que será o divisor da divisão
			if ( imas.mbr != 0 ) { // se o valor contido na memoria é diferente de 0
				imas.mq = (int16_t) (imas.ac / imas.mbr); // o mq recebe o quociente/resultado
				imas.ac = (int16_t) imas.ac % imas.mbr; // o acumulador recebe o resto
			} else { // se a divisao for por  0 
				imas_halt = true; // irá parar o loop
			}
			break;
		case IMAS_JMP_M: // salto incondicional
			memory_read(&imas); // lê o endereço de destino da memória
			imas.pc = imas.mbr; // define o PC = valor lido da memória
			break;
		case IMAS_JZ_M: // salta se o acumulador for igual a 0 
			memory_read(&imas); // lê o endereço de destino da memória
			if ( imas.ac == 0 ) { // verifica se o acumulador é igual a 0
				imas.pc = imas.mbr; //  se for o PC = valor lido da memmória
			}
			break;
		case IMAS_JNZ_M: // salta se o acumulador for diferente de 0
			memory_read(&imas); // lê o endereço de destino da memória
			if ( imas.ac != 0 ) { // verifica se o valor do AC é diferente de 0 
				imas.pc = imas.mbr; //  se for, o PC = valor lido na memória
			}
			break;
		case IMAS_JPOS_M: // salto se acumulador >= 0
			memory_read(&imas);// lê o endereço de destino da memória
			if ( imas.ac >= 0 ) { // verifica se o valor do AC é >= 0 
				imas.pc = imas.mbr; // se for, o PC = valor lido na memória
			}
			break;
		case IMAS_IN: 
			io_read(&imas); // le a entrada do usuario para o acumulador
			break;
		case IMAS_OUT:
			io_write(&imas); // printa o valor do acumulador para o usuario
			break;
		default:
			printf("Invalid instruction %04X!\n", imas.ibr);
			imas_halt = true;
			break;
		}

		/* Breakpoint subcycle */
		if(breakpoints[original_pc]) {
			printf("<== IMAS Registers ==>\n");
			printf("PC = 0x%04hX\n", original_pc);
			printf("PC+ = 0x%04hX\n", imas.pc);
			printf("MAR = 0x%04hX\n", imas.mar);
			printf("IBR = 0x%04hX\n", imas.ibr);
			printf("IR = 0x%04hX\n", imas.ir);
			printf("MBR = 0x%04hX\n", imas.mbr);
			printf("AC = 0x%04hX\n", imas.ac);
			printf("MQ = 0x%04hX\n", imas.mq);
		}

	} while(!imas_halt);

	return 0;
}