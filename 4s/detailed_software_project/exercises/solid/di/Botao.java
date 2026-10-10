public class Botao {
  private Acionavel acionavel;

  public Botao(Acionavel ac) {
    this.acionavel = ac;
  }

  public void Ligar() {
    acionavel.ligar();
  }

  public void Desligar() {
    acionavel.desligar();
  }

  public void setAcionavel(Acionavel ac) {
    this.acionavel = ac;
  }
}
