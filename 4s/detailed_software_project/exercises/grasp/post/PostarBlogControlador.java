package ufc.quixada.es.pds.grasp.controlador;

public class PostarBlogControlador {

    private RepositorioPosts repositorioPosts;

    public PostarBlogControlador(RepositorioPosts repositorioPosts) {
        this.repositorioPosts = repositorioPosts;
    }

    public void publicarPost(String titulo, String corpoTexto) {
        repositorioPosts.adicionar(titulo, corpoTexto);
    }
}
