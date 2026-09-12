public class Troca {
  public static synchronized void troca(Tribo tribo1, Animal animal1, Indio cacique1, Tribo tribo2, Animal animal2,
      Indio cacique2) {
    tribo1.adicionarAninalNaTribo(cacique1, animal1);
    tribo1.removeAnimal(animal2);
    tribo2.adicionarAninalNaTribo(cacique2, animal2);
    tribo2.removeAnimal(animal1);
  }
}
