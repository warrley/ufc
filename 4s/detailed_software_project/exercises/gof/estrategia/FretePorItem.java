import java.util.ArrayList;

public class FretePorItem implements IFrete{

	@Override
	public double calculaFrete(ArrayList<Produto> produtos) {
		return produtos.size()*5;
	}

}
