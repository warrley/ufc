public class Navio{
	IArma arma;
	
	//injecao no construtor
	public Navio(IArma arma){
		this.arma = arma;
	}
	
	//injecao no metodo set
	public void setArma(IArma arma){
		this.arma = arma;
	}
	
	public void atacar(){
		arma.atirar();
	}
}