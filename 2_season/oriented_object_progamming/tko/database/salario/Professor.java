package database.salario;

class Professor extends Funcionario {
    protected ClasseProfessor classe;
    
    enum ClasseProfessor {
        A(3000), B(5000), C(7000), D(9000), E(11000);
        
        private final int salarioBase;
        
        ClasseProfessor(int salarioBase) {
            this.salarioBase = salarioBase;
        }
        
        public int getSalarioBase() {
            return salarioBase;
        }
    }


    //inicializa classe e muda maxDiarias para 2
    public Professor(String nome, String classeStr) {
        super(nome);
        super.maxDiarias = 2;
        
        this.classe = ClasseProfessor.valueOf(classeStr.toUpperCase());
    }

    public ClasseProfessor getClasse() {
        return classe;
    }
    
    //lógica do salário do professor
    //usa o super.getSalario() para pegar bonus e diarias
    @Override
    public int getSalario() {
        return super.getSalario() + this.classe.getSalarioBase();
    }

    @Override
    public String toString() {
        return String.format("prof:%s:%s:%d", this.nome, this.classe.name(), this.getSalario());
    }
}

