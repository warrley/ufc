package database.salario;

class STA extends Funcionario {
    protected int nivel;

    //inicializa nivel e muda maxDiarias para 1
    public STA(String nome, int nivel) {
        super(nome);
        super.maxDiarias = 1;
        this.nivel = nivel;
    }
    
    public int getNivel() {
        return this.nivel;
    }

    //lógica do salário do sta
    //usa o super.getSalario() para pegar bonus e diarias
    @Override
    public int getSalario() {
        return super.getSalario() + 3000 + 300*this.nivel;
    }

    @Override
    public String toString() {
        return String.format("sta:%s:%d:%d", this.nome, this.nivel, this.getSalario());
    }
}

