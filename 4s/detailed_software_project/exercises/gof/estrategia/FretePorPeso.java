import java.util.ArrayList;

public class FretePorPeso implements IFrete
{
	double precoPorQuilo;
	
	public FretePorPeso(double precoPadraoPorQuilo) {
		precoPorQuilo = precoPadraoPorQuilo;
	}
	
	public void definePrecoPorQuilo (double precoPorQuilo) {
		precoPorQuilo = precoPorQuilo;
	}
	
	@Override
	public double calculaFrete(ArrayList<Produto> produtos) {
		double frete = 0;
		for (Produto produto : produtos) {
			frete = frete + produto.getPeso();
		}
		return frete * precoPorQuilo;
	}

}
