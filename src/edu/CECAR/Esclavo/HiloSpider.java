package edu.CECAR.Esclavo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HiloSpider extends Thread {

	private ControladorSpider controladorSpider;
	private int inicio;
	private int final_d;
	private ArrayList<String> urls = new ArrayList<String>();

	public HiloSpider(ControladorSpider controladorSpider, int inicio,
			int final_d, HashSet<String> urls) {
		
		System.out.println("Inicio: "+inicio+ " Fin: "+final_d);
		this.controladorSpider = controladorSpider;
		this.inicio = inicio;
		this.final_d = final_d;
		this.urls.addAll(urls);
	}






	@Override
	public void run() {

		for (int i=inicio; i<final_d;i++) {
			try {
				Document document =  Jsoup.connect(urls.get(i)).get();
				String titulo = document.title();
				String texto = document.body().text();
				Pagina pagina = new Pagina(titulo, texto, urls.get(i));
				controladorSpider.adicionarNumeroPrimo(pagina);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		}
	}
}
