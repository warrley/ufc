import java.util.ArrayList;

public class Tribo {
  private ArrayList<Animal> animais;
  private ArrayList<Indio> indios;
  private Indio cacique;

  public Tribo() {
    animais = new ArrayList<Animal>();
  }

  private void adicionaAnimal(Animal animal) {
    animais.add(animal);
  }

  public int getNumPatas() {
    int result = 0;
    for (Animal a : animais) {
      result += a.getNumPatas();
    }
    return result;
  }

  public ArrayList<Animal> getAnimais() {
    return animais;
  }

  public boolean animalEstaNaTribo(Animal animal) {
    for (Animal a : this.animais) {
      if (a.equals(animal)) {
        return true;
      }
    }
    return false;
  }

  public void setAnimais(ArrayList<Animal> animais) {
    this.animais = animais;
  }

  public void removeAnimal(Animal animal) {
    this.animais.remove(animal);
  }

  public void addIndio(Indio indio) {
    this.indios.add(indio);
  }

  public ArrayList<Indio> getIndios() {
    return indios;
  }

  public void setCacique(Indio cacique) {
    this.cacique = cacique;
  }

  public Indio getCacique() {
    return this.cacique;
  }

  public void adicionarAninalNaTribo(Indio possivelCacique, Animal animal) {
    if (possivelCacique.getNome().equals(this.cacique.getNome())) {
      this.adicionaAnimal(animal);
    }
  }
}
