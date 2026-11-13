
public class Cliente {

	public static void main(String[] args) {
		Cliente cliente = new Cliente();
		cliente.inicia();
	}
	
	public void inicia() {
		// criar a disciplina
		Disciplina disciplina = new Disciplina();
		// adicionar alunos
		disciplina.adicionaAluno(new Aluno("Rui"));
		disciplina.adicionaAluno(new Aluno("Ari"));
		disciplina.adicionaAluno(new Aluno("Ana"));
		disciplina.adicionaAluno(new Aluno("Bia"));
		
		
		Iterator iterator = disciplina.criaIterator(TipoIterador.CIRCULAR);
		
		while(iterator.temProximo()) {
			System.out.println(iterator.proximo().nome);
		}
		System.out.println("Agora ao contrario");
		
		iterator = disciplina.criaIterator(TipoIterador.AO_CONTRARIO);
		
		while(iterator.temProximo()) {
			System.out.println(iterator.proximo().nome);
		}
	}

}
