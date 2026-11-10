public abstract class Pizza {
  protected Float raio;
  protected Float massa;

  public Pizza(Float raio, Float massa) {
    this.raio = raio;
    this.massa = massa;
  }

  public Float getRaio() {
    return this.raio;
  }

  public Float getMassa() {
    return this.massa;
  }

  public String getDescricao() {
    return getClass().getSimpleName() + " (Raio: " + raio + "cm, Massa: " + massa + "g)";
  }
}
