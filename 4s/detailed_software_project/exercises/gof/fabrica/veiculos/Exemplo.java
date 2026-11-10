
public class Exemplo {

	public static void main(String[] args) {
		Exemplo exemplo = new Exemplo();
		exemplo.inicia();
	}
	
	public void inicia() {
		// criar a primeira fábrica
		IFabricaVeiculo fabricaVeiculo = new FabricaVeiculoFiat();
		// criar um carro usando a fabrica
		IVeiculo veiculo = fabricaVeiculo.criaVeiculo(TipoVeiculo.CARRO);
		System.out.println(veiculo.getNome());
		// criar um moto usando a fabrica
		veiculo = fabricaVeiculo.criaVeiculo(TipoVeiculo.MOTO);
		System.out.println(veiculo.getNome());
		// criar um caminhao usando a fabrica
		veiculo = fabricaVeiculo.criaVeiculo(TipoVeiculo.CAMINHAO);
		System.out.println(veiculo.getNome());
		
		// criando segunda fabrica e seus veiculos
		fabricaVeiculo = FabricaVeiculoVW.getInstance();
		// criar um carro usando a fabrica
		veiculo = fabricaVeiculo.criaVeiculo(TipoVeiculo.CARRO);
		System.out.println(veiculo.getNome());
		// criar um moto usando a fabrica
		veiculo = fabricaVeiculo.criaVeiculo(TipoVeiculo.MOTO);
		System.out.println(veiculo.getNome());
		// criar um caminhao usando a fabrica
		veiculo = fabricaVeiculo.criaVeiculo(TipoVeiculo.CAMINHAO);
		System.out.println(veiculo.getNome());
		
	}

}
