package ufc.quixada.es.pds.grasp.controlador;

public interface Predicado<T> {
	boolean aplicarA(T type, Object criteria);
}
