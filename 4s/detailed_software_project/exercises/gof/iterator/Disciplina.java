import java.util.ArrayList;

public class Disciplina implements Agregador{
	
	ArrayList<Aluno> alunos = new ArrayList<Aluno>();
	public void adicionaAluno(Aluno aluno) {
		this.alunos.add(aluno);
	}

	public Iterator criaIterator(TipoIterador tipo) {
		Iterator iterator = null;
		switch (tipo) {
		case ORDEM_DE_ADICAO: 
			iterator = new IteradorOrdemDeAdicao(this.alunos);
			break;
		case AO_CONTRARIO:
			iterator = new IteradorAoContrario(this.alunos);
			break;
		case CIRCULAR:
			iterator = new IteradorCircular(this.alunos);
			break;
		default:
			iterator = new IteradorOrdemDeAdicao(this.alunos);
		}
		return iterator;
	}
}
