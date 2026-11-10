import java.util.ArrayList;

public class Pedido {

	ArrayList<Produto> produtos = new ArrayList<Produto>();
	IFrete frete;
	
	public void adicionaProduto(Produto produto) {
		this.produtos.add(produto);
	}
	
	public void escolherFrete(IFrete frete) {
		this.frete = frete;
	}
	
	public double leValorFrete() {
		return frete.calculaFrete(produtos);
	} 
}
