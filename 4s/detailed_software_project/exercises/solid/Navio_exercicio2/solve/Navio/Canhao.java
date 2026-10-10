public class Canhao implements IArma{
	IMunicao municao;
	//injecao no construtor
	public Canhao(IMunicao municao){
		this.municao = municao;
	}
	
	//injecao pelo metodo set
	public void setMunicao(IMunicao municao){
		this.municao = municao;
	}
	
	public void atirar(){
		municao.efeito();
	}
}