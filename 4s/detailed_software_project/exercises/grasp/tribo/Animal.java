public class Animal implements Andar {
  private String nome;
  protected int numPatas = 0;

  public Animal(String aNome) {
    nome = aNome;
  }

  public int getNumPatas() {
    return this.numPatas;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }
}
