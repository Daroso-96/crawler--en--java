package edu.CECAR.Esclavo;

import java.io.Serializable;

public class Pagina implements Serializable {

	String titulo, texto, url;
	
	public Pagina(String titulo, String texto, String url) {
		super();
		this.titulo = titulo;
		this.texto = texto;
		this.url = url;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
