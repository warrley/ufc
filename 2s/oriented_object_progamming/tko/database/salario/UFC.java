package database.salario;
import java.util.*;

class UFC {
    private Map<String, Funcionario> funcionarios = new TreeMap<String, Funcionario>();

    @Override
    public String toString() {
        String s = "";
        for(Funcionario f : this.funcionarios.values()) {
            if(s.length() != 0) s += "\n";
            s += f;
        }
        
        return s;
    }
    
    private void FuncionarioExiste(String nome) throws MsgException {
        for(String nome_fm : this.funcionarios.keySet()) {
            if(nome_fm.equals(nome)) return;
        }
        throw new MsgException("fail: funcionario nao encontrado");
    }

    public Funcionario getFuncionario(String nome) throws MsgException {
        this.FuncionarioExiste(nome);
        return this.funcionarios.get(nome);
    }

    public void addFuncionario(Funcionario funcionario) {
        this.funcionarios.put(funcionario.getNome(), funcionario);
    }
    
    public void rmFuncionario(String nome) throws MsgException{
        this.FuncionarioExiste(nome);
        this.funcionarios.remove(nome);
    }

    //reparte o bonus para todos os funcionarios
    public void setBonus(int bonus) throws MsgException{
        if ( this.funcionarios.size() == 0 ) throw new MsgException("fail: sem funcionarios");
        
        for(Funcionario f : this.funcionarios.values()) {
            f.setBonus(bonus/this.funcionarios.size());
        }
    }
}