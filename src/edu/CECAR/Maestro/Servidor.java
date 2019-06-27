package edu.CECAR.Maestro;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import edu.CECAR.Esclavo.IEsclavo;
import edu.CECAR.Esclavo.Pagina;


public class Servidor{
	
	private JLabel jlNumeroServidoresEsclavos;
	private ImplementacionServidor implementacion;
	private ArrayList<Pagina> datos = new ArrayList<Pagina>();
	
	public Servidor() {
		
		JFrame jFrame = new JFrame("Servidor Maestro");
		jFrame.setSize(300, 200);
		jFrame.setLayout(null);
		jFrame.setLocationRelativeTo(null);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton jbIniciarAlgoritmoDistribuido = new JButton("Iniciar distribución");
		jbIniciarAlgoritmoDistribuido.setBounds(20, 120, 250, 20);
		jbIniciarAlgoritmoDistribuido.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				iniciardistribucion();
				
				
			}
		});
		jFrame.add(jbIniciarAlgoritmoDistribuido);
		
		
		JButton jbVerServidorEsclavos = new JButton("Servidores esclavos conectados");
		jbVerServidorEsclavos.setBounds(20, 20, 250, 20);
		jbVerServidorEsclavos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				jlNumeroServidoresEsclavos.setText("Numero de Esclavos Conectados: " + 
				           implementacion.getServidoresEsclavos().size());
				
				
			}
		});
		jFrame.add(jbVerServidorEsclavos);
		
		jlNumeroServidoresEsclavos = new JLabel("Numero de Esclavos Conectados: 0");
		jlNumeroServidoresEsclavos.setBounds(20, 80, 250, 20);
		jFrame.add(jlNumeroServidoresEsclavos);
		
		jFrame.setVisible(true);
		
		
		//Se lanza el objeto remoto
		try {
		       
	        LocateRegistry.createRegistry(1099); //Se monta el RMIRegistry sobre el puerto 1099
		  		 	        
			implementacion = new ImplementacionServidor(); //se instancia la implementacion del Objeto Remoto
			/*
			 *  Se agrega al RMIRegistry. 
			 *  ServidorFactorial: Nombre con el cual los cliente solictan servicios del Obejto remoto
			 */
		  	Naming.rebind("ServidorSpider", implementacion);
		  	
			System.out.println("Servidor Montado");
			
			//System.out.println(implementacion.getServidoresEsclavos().size());
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
	public void iniciardistribucion() {
		
		try {
			
			//System.out.println(implementacion.getServidoresEsclavos().get(0));
			ThreadGroup grupoHilos = new ThreadGroup("ResSpider");
			ArrayList<Integer> datos = new ArrayList<Integer>();
				
			/* 
			 * - El numero de paginas debe ser igual al numero de esclavos
			 * - Las urls, iran en el vector urls, ejemplo String urls[] = {"http://www.eltiempo.com/","http://www.nature.com/"}; 
			 */
			
			String urls[] = {"http://www.eltiempo.com/"};
			int indice =0;
			
			for (String direccionIP : implementacion.getServidoresEsclavos()) {
						    
				Thread thread = new Thread(grupoHilos, new HiloRespuestaEsclavo(direccionIP,urls[indice],this));
				thread.start();
				indice++;
	        				
			}
			
			
			
			/*String url = new String("rmi://localhost/ServidorEsclavo");
	        IEsclavo comm = (IEsclavo)Naming.lookup(url);
	        ArrayList<Integer> reply = comm.ObtenerPrimos(1,4);
	        */
	       /* for (Integer primo : datos) {
				System.out.println(primo);
			}*/
			while (grupoHilos.activeCount() > 0);
			  GuardarResultado();
			
	        
	        			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	public static void main(String...arg) {
		
		try {
		
			/*String url = new String("rmi://localhost/EsclavoPrimo");
	        IEsclavo comm = (IEsclavo)Naming.lookup(url);
	        ArrayList<Integer> reply = comm.ObtenerPrimos(1,4);
	        
	        for (Integer primo : reply) {
				System.out.println(primo);
			}*/
			new Servidor();
	        			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	public synchronized void adicionarRespuestaPrimoEsclavo(ArrayList<Pagina> respuesta) {

		datos.addAll(respuesta);
	}
	
	public void GuardarResultado(){
		/*try {
			//Escritura
			java.io.BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("NumerosPrimos.txt"));
			for(int i=0; i<datos.size();i++){
				bufferedWriter.append("# "+i+" : "+datos.get(i));
				bufferedWriter.flush();
				bufferedWriter.newLine();
			}
			JOptionPane.showMessageDialog(null,"Proceso finalizado");
 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		Connection con = null;
		String sURL = "jdbc:mysql://localhost:3306/bd_crawler";
		
		try {
			con = (Connection) DriverManager.getConnection(sURL,"root","");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try{
			PreparedStatement stmt;
			for(int i=0; i<datos.size();i++){
				stmt = (PreparedStatement) con.prepareStatement("insert into pagina_datos(titulo,texto,url) values(?,?,?)");
				stmt.setString(1, datos.get(i).getTitulo());
				stmt.setString(2, datos.get(i).getTexto());
				stmt.setString(3, datos.get(i).getUrl());
				stmt.executeUpdate();
				
			}	
			con.close();

		}catch (SQLException sqle) { 
			  System.out.println("Error en la ejecución:" 
			    + sqle.getErrorCode() + " " + sqle.getMessage()); 
		}
		JOptionPane.showMessageDialog(null,"Proceso finalizado");
	}

}