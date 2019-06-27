package edu.CECAR.Esclavo;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;


public class ImplementacionEsclavo extends UnicastRemoteObject implements IEsclavo {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ImplementacionEsclavo() throws RemoteException {
        super();

    }

    @Override
    public ArrayList<Pagina> ObtenerDatosPagina(String url) throws RemoteException {
    	
    	ControladorSpider ob = new ControladorSpider(url);

        return ob.getDatosCrawleados();
    	
    }

}