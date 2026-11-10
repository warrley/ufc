
public class FabricaVeiculoFiat implements IFabricaVeiculo{
	
	public IVeiculo criaVeiculo(TipoVeiculo tipoVeiculo) {
		IVeiculo veiculo = null;
		
		switch(tipoVeiculo) {
		case CARRO: veiculo = new CarroFiat();
			break;
		case MOTO: veiculo = new MotoFiat();
		    break;
		case CAMINHAO: veiculo = new CaminhaoFiat();
		    break;
		default: veiculo = new CarroFiat();
		}
		
		return veiculo;
	}

}
