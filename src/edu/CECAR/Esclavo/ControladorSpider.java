package edu.CECAR.Esclavo;

import java.util.ArrayList;

import edu.CECAR.Maestro.HiloRespuestaEsclavo;
import edu.CECAR.utilidades.InformacionSistema;

public final class ControladorSpider {

	private ArrayList<Pagina> datoscrawleados = new ArrayList<Pagina>();
	private Thread thread;
	private String url;

	public ControladorSpider(String url) {
		this.url = url;
		crearHilos();
	}

	private void crearHilos() {
		int numeroNucleos = InformacionSistema.getNumeroNucleos();
		ThreadGroup grupoHilos = new ThreadGroup("HiloDatosPagina");

		 Spider ob = new Spider();
		 ob.getPageLinks(url);
		 
         /*int n = ob.getLinks().size()/numeroNucleos;
		 int rango_inicial = 0;
		 
		for (int i = 0; i < numeroNucleos; i++) {

			thread = new Thread(grupoHilos, new HiloSpider(this,rango_inicial,n,ob.getLinks()));
			thread.start();
			rango_inicial = n;
			n+=n;
		}*/
		 
			int tamanio = ob.getLinks().size();
			boolean espar = comprobarPar(tamanio);
			int control_ini =0;
			int control_sig =0;
			int division = tamanio / numeroNucleos;
			
			for (int i = 1; i <= numeroNucleos; i++) {
				//System.out.println("hola "+i);
				/*if(i!=numeroNucleos){
					Thread thread = new Thread(grupoHilos, new RealizarConsultaSegmento(this,1 , 1));
					thread.start();
				}*/
				
				if(espar){
					
					   control_sig += division;
						
						
					   Thread thread = new Thread(grupoHilos, new HiloSpider(this,control_ini , control_sig,ob.getLinks()));
					   thread.start();
						
						
						control_ini += division+1;
					
				}else{
					
					if(i!=numeroNucleos){
						control_sig += division;
						
						
						Thread thread = new Thread(grupoHilos, new HiloSpider(this,control_ini , control_sig,ob.getLinks()));
						   thread.start();
							
							
							control_ini += division+1;
					}else{
						   control_sig += division+1;
						   Thread thread = new Thread(grupoHilos, new HiloSpider(this,control_ini , control_sig,ob.getLinks()));
						   thread.start();	
					}
				
					
					
				}
				
				
			}
		
		// se bloquea a esperar que los hilos terminen
		while (grupoHilos.activeCount() > 0);
		System.out.println("Se ha ejecutado con " + numeroNucleos + "nucleos" + "Y se encontraron los siguientes primos:");
		
		/*for (Integer primo : numerosPrimosEncontrados) {
			System.out.println(primo);
		}*/
	}

	public synchronized void adicionarNumeroPrimo(Pagina pagina) {

		datoscrawleados.add(pagina);
	}

	public ArrayList<Pagina> getDatosCrawleados() {
		return datoscrawleados;
	}
	
	public boolean comprobarPar(int numero){
		
		boolean respuesta =false;
		
		if(numero%2==0){
             respuesta = true;
        }
		
		return respuesta;
		
	}
	
}
