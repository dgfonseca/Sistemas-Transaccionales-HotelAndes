package uniandes.isis2304.hotelandes.test;

import static org.junit.Assert.fail;

import java.io.FileReader;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import negocio.HotelAndes;

public class ConexionTest {
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecuci�n
	 */
	private static Logger log = Logger.getLogger(ConexionTest.class.getName());
	
	/**
	 * Ruta al archivo de configuraci�n de los nombres de tablas de la base de datos: La unidad de persistencia existe y el esquema de la BD tambi�n
	 */
	private static final String CONFIG_TABLAS_A = "./src/resources/config/TablasBD_A.json"; 
	
	/**
	 * Ruta al archivo de configuraci�n de los nombres de tablas de la base de datos: La unidad de persistencia NO existe
	 */
	private static final String CONFIG_TABLAS_ERR_DS = "./src/resources/config/TablasBD_ErrorDataStore.json"; 
	
	/**
	 * Ruta al archivo de configuraci�n de los nombres de tablas de la base de datos: La unidad de persistencia NO existe
	 */
	private static final String CONFIG_TABLAS_ERR_USER = "./src/resources/config/TablasBD_ErrorInvalidUser.json"; 
	
	/**
	 * La clase que se quiere probar
	 */
    private HotelAndes parranderos;
	
	/* ****************************************************************
	 * 			M�todos de prueba de acceso a la BD
	 *****************************************************************/  
    /**
     * M�todo de prueba para acceso correcto a una base de datos
     */
    @Test
    public void normalAccessTest ()
  	{
  	  	try
		{
			log.info ("Probando el acceso a la base de datos con datos v�lidos (BD, credenciales, esquema");
			parranderos = new HotelAndes (openConfig (CONFIG_TABLAS_A));
			log.info ("Conexi�n realizada correstamente");
			log.info ("Cerrando la conexi�n");
			
			parranderos.cerrarUnidadPersistencia ();
			log.info ("Conexi�n cerrada");
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de acceso normal FALL� !!. La excepci�n generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de acceso normal a la base de datos fall� !! Revise persistence.xml.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepci�n";
//			System.out.println (msg);
			fail (msg);
		}
  	}
  	  
    /**
     * M�todo que prueba el intento de acceso a una base de datos inaccesible, por alguna de las siguientes causas:
     * 1. No existe la unidad de persistencia
     * 2. La unidad de persistencia est� caida
     */
    @Test
    public void baseDatosInaccesible ()
    {
		try
		{
	    	log.info ("Probando el acceso a la base de datos con una base de datos que no existe");
			parranderos = new HotelAndes (openConfig (CONFIG_TABLAS_ERR_DS));
			fail ("Deber�a fallar. La base de datos no existe !!");
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba realizada exitosamente. La excepci�n generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de base de datos inaccesible correcta.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepci�n";
			System.out.println (msg);
		}
    }
    
    /**
     * M�todo que prueba el intento de acceso a una base de datos inaccesible, por causa:
     * 1. Credenciales de usuario inv�lidas (nombre de usuario / contrase�a)
     */
    @Test
    public void usuarioInvalidoTest ()
    {
		try
		{
	    	log.info ("Probando el acceso a la base de datos con datos de usuario incorrectos");
			parranderos = new HotelAndes (openConfig (CONFIG_TABLAS_ERR_USER));
			fail ("Deber�a fallar. Las credenciales del usuario no son v�lidas");
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba realizada exitosamente. La excepci�n generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de credenciales incorrectas correcta.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepci�n";
			System.out.println (msg);
		}
    }

   

	/* ****************************************************************
	 * 			M�todos de configuraci�n
	 *****************************************************************/
    /**
     * Lee datos de configuraci�n para la aplicaci�n, a partir de un archivo JSON o con valores por defecto si hay errores.
     * @param tipo - El tipo de configuraci�n deseada
     * @param archConfig - Archivo Json que contiene la configuraci�n
     * @return Un objeto JSON con la configuraci�n del tipo especificado
     * 			NULL si hay un error en el archivo.
     */
    private JsonObject openConfig (String archConfig)
    {
    	JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontr� un archivo de configuraci�n de tablas v�lido");
		} 
		catch (Exception e)
		{
//			e.printStackTrace ();
			log.info ("NO se encontr� un archivo de configuraci�n v�lido");			
			JOptionPane.showMessageDialog(null, "No se encontr� un archivo de configuraci�n de tablas v�lido: ", "TipoBebidaTest", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }	
}
