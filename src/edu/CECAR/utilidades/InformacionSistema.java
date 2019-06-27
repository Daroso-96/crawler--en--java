/** 
 * Clase: InformacionSistema
 *
 *
 * Version: 1.0
 *
 *
 * Fecha : 2014-02-26
 *
 *
 * Autor: Ing. Jhon Jaime Mendez
 *
 *
 * Copyrigth: CECAR
 *
 **/

package edu.CECAR.utilidades;

/**
 * Esta clase permite obtener información del numero de procesadores y nucleo con los que cuenta
 * una plataforma de hardware
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class InformacionSistema {

	/**
	 * 
	 * @return Número de procesadores
	 * 
	 *         Tomado de la solución presentada por Jorge Castro
	 */
	public static String getNumeroProcesadores() {

		String retorno = "";

		try {

			String command = "systeminfo";
			Process proceso = Runtime.getRuntime().exec(command);

			BufferedReader in = new BufferedReader(new InputStreamReader(proceso.getInputStream()));

			StringBuffer resultado = new StringBuffer();
			String line = null;
			while ((line = in.readLine()) != null) {
				resultado.append(line + "\n");
			}

			// System.out.println(resultado.toString());

			String aux = resultado.toString().substring(resultado.toString().indexOf("Procesador"));
			aux = aux.substring(aux.indexOf(" "));
			aux = aux.substring(0, aux.indexOf("\n")).trim();
			retorno = aux;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return retorno;

	}

	/**
	 * 
	 * @return Número de nucleos
	 * 
	 *         Tomado de la solución presentada por Rosa y lina Molina
	 */
	public static int getNumeroNucleos() {
		return Runtime.getRuntime().availableProcessors();
	}
}
