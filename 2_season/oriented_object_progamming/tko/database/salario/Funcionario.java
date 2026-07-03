package database.salario;

abstract class Funcionario {
    protected String nome;
    protected int bonus;
    protected int diarias;
    protected int maxDiarias;

    public Funcionario(String nome) {
        this.nome = nome;
        this.diarias = 0;
    }

    public String getNome() {
        return this.nome;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    //se atingiu o máximo, lance uma MsgException
    //se não atingiu o máximo, adicione mais uma diária
    public void addDiaria() throws MsgException {
        if(this instanceof Terceirizado) throw new MsgException("fail: terc nao pode receber diaria");
        if(this.diarias == this.maxDiarias) throw new MsgException("fail: limite de diarias atingido");
        this.diarias++;
    }

    //retorna bonus + diarias * 100
    public int getSalario() {
        return this.bonus + this.diarias*100;
    }
}

