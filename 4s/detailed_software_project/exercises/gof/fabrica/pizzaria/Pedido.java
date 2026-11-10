import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pedido {
  private Pizza pizza;
  private LocalDateTime dataHora;

  public Pedido(Pizza pizza) {
    this.pizza = pizza;
    this.dataHora = LocalDateTime.now();
  }

  public Pizza getPizza() {
    return this.pizza;
  }

  public LocalDateTime getDataHora() {
    return this.dataHora;
  }

  @Override
  public String toString() {
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    return "[" + this.dataHora.format(formato) + "] " + this.pizza.getDescricao();
  }
}
