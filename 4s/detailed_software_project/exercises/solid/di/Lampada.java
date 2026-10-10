public class Lampada extends Acionavel {
  private int luminosidade;
  private Formato formato;
  private Cor cor;

  public Lampada(int luminosidade, Formato formato, Cor cor, Potencia pot) {
    this.luminosidade = luminosidade;
    this.formato = formato;
    this.cor = cor;
    this.pot = pot;
  }

  public void ligar() {
    this.estado = true;
  }

  public void desligar() {
    this.estado = false;
  }
}
