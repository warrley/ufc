public class Veiculo {
  // private String montadora;
  private String modelo;
  protected int rodas;

  public Veiculo(String modelo) {
    // this.montadora = montadora;
    this.modelo = modelo;
  }

  public String getModelo() {
    return this.modelo;
  }
}
