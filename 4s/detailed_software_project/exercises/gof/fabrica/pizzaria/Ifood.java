import java.util.ArrayList;
import java.util.List;

public class Ifood {
  private FabricaPizzaria fabrica;
  private List<Pedido> history;
  private static Ifood instance;

  private Ifood() {
    this.history = new ArrayList<Pedido>();
  }

  public static synchronized Ifood getInstance() {
    if (instance == null) {
      instance = new Ifood();
    }
    return instance;
  }

  public void setFabrica(FabricaPizzaria fabrica) {
    this.fabrica = fabrica;
  }

  public Pizza vender(TipoPizza tipo) {
    if (this.fabrica == null) {
      throw new IllegalStateException("Nenhuma fabrica selecionada no Ifood!");
    }
    Pizza pizza = this.fabrica.criarPizza(tipo);
    if (pizza != null) {
      Pedido pedido = new Pedido(pizza); // Registra a pizza junto com o timestamp!
      this.history.add(pedido);
    }
    return pizza;
  }

  public List<Pedido> getHistory() {
    return this.history;
  }
}
