package database.salario;

class Terceirizado extends Funcionario {
    protected int horas;
    protected boolean isSalubre;

    public Terceirizado(String nome, int horas, String isSalubre) {
        super(nome);
        super.maxDiarias = 0;
        this.horas = horas;
        this.isSalubre = true;
    }

    public int getHoras() {
    
        return this.horas;
    }

    public String getIsSalubre() {
        return this.isSalubre ? "sim" : "nao";
    }

    //lógica do salário do terceirizado
    //usa o super.getSalario() para pegar bonus e diarias
    @Override
    public int getSalario() {
        int ds = (this.isSalubre) ? 500 : 0;
        return super.getSalario() + this.horas*4 + ds;
    }

    @Override
    public String toString() {
        return String.format("ter:%s:%d:%s:%d", this.nome, this.horas, this.getIsSalubre() ,this.getSalario());
    }
}
