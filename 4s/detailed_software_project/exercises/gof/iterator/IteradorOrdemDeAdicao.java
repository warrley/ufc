import java.util.ArrayList;

public class IteradorOrdemDeAdicao implements Iterator{
	Aluno alunos[];
	int indiceAtual;
	
	public IteradorOrdemDeAdicao(ArrayList<Aluno> alunos) {
		this.alunos = new Aluno[alunos.size()];
		alunos.toArray(this.alunos);
		this.indiceAtual = 0;
	}
	
	public boolean temProximo() {
		if(indiceAtual >= alunos.length) {
			return false;
		} else {
			return true;
		}
	}
	
	public Aluno proximo(){
		Aluno aluno = this.alunos[indiceAtual];
		indiceAtual++;
		return aluno;
	}

}
