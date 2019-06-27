package edu.CECAR.Maestro;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ImplementacionServidor extends UnicastRemoteObject implements IServidor {

	private static final long serialVersionUID = 100L;
	private ArrayList<String> servidoresEsclavos 
	                         = new ArrayList<String>();

	protected ImplementacionServidor() 
			throws RemoteException {
	}

	@Override
	public void adicionarServidorEsclavo(String direccionIP)
			throws RemoteException {
	
		servidoresEsclavos.add(direccionIP);
		
	}

	public ArrayList<String> getServidoresEsclavos() {
		return servidoresEsclavos;
	}
	
}