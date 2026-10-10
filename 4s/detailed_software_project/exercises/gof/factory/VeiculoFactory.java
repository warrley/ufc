public class VeiculoFactory {
  public static Veiculo createVeiculo(String type, String modelo) {
    if (type.equals("moto")) {
      return Moto(modelo);
    } else if (type.equals("carro")) {
      return new Carro(modelo);
    } else if (type.equals("caminhao")) {
      return new Caminhao(modelo);
    }
  }
}
