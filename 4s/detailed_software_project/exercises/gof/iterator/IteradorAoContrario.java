import java.util.ArrayList;

public class IteradorAoContrario implements Iterator{
	Aluno alunos[];
	int indiceAtual;
	
	public IteradorAoContrario(ArrayList<Aluno> alunos) {
		this.alunos = new Aluno[alunos.size()];
		alunos.toArray(this.alunos);
		this.indiceAtual = alunos.size() - 1;
	}
	
	public boolean temProximo() {
		if(indiceAtual < 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public Aluno proximo(){
		Aluno aluno = this.alunos[indiceAtual];
		indiceAtual--;
		return aluno;
	}

}
