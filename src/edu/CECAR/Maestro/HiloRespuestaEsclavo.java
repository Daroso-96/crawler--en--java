package edu.CECAR.Maestro;

import java.rmi.Naming;
import java.util.ArrayList;

import edu.CECAR.Esclavo.IEsclavo;

public class HiloRespuestaEsclavo  extends Thread{
	
	private String url;
	private String url_pagina;
	private Servidor servidor;
	
	
	
	public HiloRespuestaEsclavo(String url,String url_pagina, Servidor servidor) {
		super();
		this.url = url;
		this.url_pagina = url_pagina;
		this.servidor = servidor;
	}



	@Override
	public void run() {
		try {
			String[] ip = url.split("/");
			String url ="rmi://"+ ip[1] +"/ServidorEsclavo";
			System.out.println(url);
			IEsclavo comm = (IEsclavo)Naming.lookup(url);
			servidor.adicionarRespuestaPrimoEsclavo(comm.ObtenerDatosPagina(url_pagina));
			
        }catch (Exception e) {	
			e.printStackTrace();
		}
	}

}
