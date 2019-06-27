package edu.CECAR.Esclavo;

import java.net.Inet4Address;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

import edu.CECAR.Maestro.IServidor;


public class Esclavo {
	 public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
			
			try {
				
				//registrarse al servidor
				String url = new String("rmi://localhost/ServidorSpider");
				
				IServidor ih = 
						(IServidor) Naming.lookup(url);
				ih.adicionarServidorEsclavo(
						Inet4Address.getLocalHost().toString());
				
				// establecer acceso para tarea remota
				LocateRegistry.createRegistry(1100);
				ImplementacionEsclavo implementacionServidorEsclavo =
						new ImplementacionEsclavo();
				Naming.rebind("ServidorEsclavo", implementacionServidorEsclavo);
				System.out.println("Servidor Esclavo Montado");
					
			} catch (Exception e) {

				e.printStackTrace();
			} 
		 
	    }
}
