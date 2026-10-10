public class Televisao extends Acionavel {
  private float tamanho;
  private string resolucao;

  public void ligar() {
    this.estado = true;
  }

  public void desligar() {
    this.estado = false;
  }
}
