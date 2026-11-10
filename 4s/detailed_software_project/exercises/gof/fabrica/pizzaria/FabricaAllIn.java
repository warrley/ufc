public class FabricaAllIn implements FabricaPizzaria {
  @Override
  public Pizza criarPizza(TipoPizza tipo) {
    if (tipo == TipoPizza.CALABRESA) {
      return new PizzaCalabresaAllIn();
    } else if (tipo == TipoPizza.FRANGO) {
      return new PizzaFrangoAllIn();
    } else if (tipo == TipoPizza.QUEIJO) {
      return new PizzaQueijoAllIn();
    }
    return null;
  }
}
