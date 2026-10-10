public class Exemplo {
  public static void main(String[] args) {
    Exemplo ex = new Exemplo();

    Veiculo c1 = VeiculoFactory.createVeiculo("carro", "uno");
    Moto m1 = VeiculoFactory.createVeiculo("moto", "150cc");
  }
}
