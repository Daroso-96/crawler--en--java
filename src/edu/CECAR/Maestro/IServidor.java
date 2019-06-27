package edu.CECAR.Maestro;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface  IServidor extends Remote  
{
	public void adicionarServidorEsclavo(String direccionIP) throws RemoteException;
}