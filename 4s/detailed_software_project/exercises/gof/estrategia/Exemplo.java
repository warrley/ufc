
public class Exemplo {

	public static void main(String[] args) {
		Exemplo exemplo = new Exemplo();
		exemplo.inicia();
	}
	
	public void inicia() {
		// criar um pedido
		Pedido pedido = new Pedido();
		//adicionar produtos ao pedido
		pedido.adicionaProduto(new Produto("sabonete",0.05));
		pedido.adicionaProduto(new Produto("ventilador",1));
		pedido.adicionaProduto(new Produto("geladeira",48));
		// criar uma instancia do frete escolhido
		IFrete frete = new FreteFixo();
		pedido.escolherFrete(frete);
		System.out.println("O valor do frete fixo é: " + pedido.leValorFrete());
		frete = new FretePorPeso(1.5);
		pedido.escolherFrete(frete);
		System.out.println("O valor do frete por peso é: " + pedido.leValorFrete());
		frete = new FretePorItem();
		pedido.escolherFrete(frete);
		System.out.println("O valor do frete por item é: " + pedido.leValorFrete());
	}

}
