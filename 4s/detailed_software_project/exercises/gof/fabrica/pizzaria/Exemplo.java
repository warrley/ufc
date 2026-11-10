public class Exemplo {
  public static void main(String[] args) throws InterruptedException {
    Ifood ifood = Ifood.getInstance();

    System.out.println("=== COMPRANDO NA ALL IN ===");
    ifood.setFabrica(new FabricaAllIn());
    Pizza p1 = ifood.vender(TipoPizza.CALABRESA);
    Pizza p2 = ifood.vender(TipoPizza.QUEIJO);
    System.out.println("Feito: " + p1.getDescricao());
    System.out.println("Feito: " + p2.getDescricao());

    Thread.sleep(1000); // 1 segundo de intervalo para ver a mudança no timestamp

    System.out.println("\n=== COMPRANDO NA ESFIRRA PLANET ===");
    ifood.setFabrica(new FabricaEsfirraPlanet());
    Pizza p3 = ifood.vender(TipoPizza.FRANGO);
    Pizza p4 = ifood.vender(TipoPizza.QUEIJO);
    System.out.println("Feito: " + p3.getDescricao());
    System.out.println("Feito: " + p4.getDescricao());

    System.out.println("\n=== HISTÓRICO COM TIMESTAMP DO IFOOD ===");
    for (Pedido pedido : ifood.getHistory()) {
      System.out.println(pedido);
    }
  }
}
