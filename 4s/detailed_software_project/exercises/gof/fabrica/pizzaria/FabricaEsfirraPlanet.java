public class FabricaEsfirraPlanet implements FabricaPizzaria {
  @Override
  public Pizza criarPizza(TipoPizza tipo) {
    if (tipo == TipoPizza.CALABRESA) {
      return new PizzaCalabresaEsfirraPlanet();
    } else if (tipo == TipoPizza.FRANGO) {
      return new PizzaFrangoEsfirraPlanet();
    } else if (tipo == TipoPizza.QUEIJO) {
      return new PizzaQueijoEsfirraPlanet();
    }
    return null;
  }
}
