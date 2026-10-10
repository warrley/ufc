public class Canhao implements IArma {
  BolaDeFerro bolaDeFerro = new BolaDeFerro();

  public Canhao() {
  }

  @Override
  public void atirar() {
    this.bolaDeFerro.efeito();
  }
}
