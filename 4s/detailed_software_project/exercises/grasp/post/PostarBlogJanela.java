package ufc.quixada.es.pds.grasp.controlador;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class PostarBlogJanela extends JFrame implements ActionListener {

	private JButton publicar;
	private JTextField titulo;
	private JTextArea corpo;
	private PostarBlogControlador controlador;
	
	public PostarBlogJanela(PostarBlogControlador controlador) {
		this.controlador = controlador;

		titulo = new JTextField("Digite o título...");
		this.add(titulo, BorderLayout.NORTH);
		
		corpo = new JTextArea("Corpo de texto do post...");
		this.add(corpo, BorderLayout.CENTER);
		
		publicar = new JButton("Publicar Post");
		publicar.addActionListener(this);
		this.add(publicar, BorderLayout.SOUTH);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args){
		RepositorioPosts repositorio = new RepositorioPosts();
		PostarBlogControlador controlador = new PostarBlogControlador(repositorio);
		PostarBlogJanela postarBlogJanela = new PostarBlogJanela(controlador);
		postarBlogJanela.mostrar();
	}

	private void mostrar() {
		this.setSize(400, 400);
		this.setLocation(100, 100);
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent publicarPost) {
		controlador.publicarPost(this.titulo.getText(), this.corpo.getText());
	}
}
