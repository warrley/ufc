public class Bazuca implements IArma{
	Missil missil = new Missil();
	public void atirar(){
		missil.efeito();
	}
}