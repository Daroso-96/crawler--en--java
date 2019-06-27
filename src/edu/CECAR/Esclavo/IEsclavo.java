package edu.CECAR.Esclavo;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface  IEsclavo extends Remote  
{
    public ArrayList<Pagina> ObtenerDatosPagina(String url) throws RemoteException; 

}