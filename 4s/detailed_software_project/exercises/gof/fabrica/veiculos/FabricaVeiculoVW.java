
public class FabricaVeiculoVW implements IFabricaVeiculo{
	
	private static FabricaVeiculoVW fabricaVeiculoVW;
	
	private FabricaVeiculoVW(){}
	
	public static FabricaVeiculoVW getInstance() {
		if(fabricaVeiculoVW == null) {
			fabricaVeiculoVW = new FabricaVeiculoVW();
		}
		return fabricaVeiculoVW;
	}
	
	
	public IVeiculo criaVeiculo(TipoVeiculo tipoVeiculo) {
		IVeiculo veiculo = null;
		
		switch(tipoVeiculo) {
		case CARRO: veiculo = new CarroVW();
			break;
		case MOTO: veiculo = new MotoVW();
		    break;
		case CAMINHAO: veiculo = new CaminhaoVW();
		    break;
		default: veiculo = new CarroVW();
		}
		
		return veiculo;
	}

}
