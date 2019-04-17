package uniandes.isis2304.hotelandes.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileReader;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import negocio.HotelAndes;
import negocio.Servicio;

public class ServicioTest {
	/**
	 * Logger para escribir la traza de la ejecuci�n
	 */
	private static Logger log = Logger.getLogger(Servicio.class.getName());
	
	/**
	 * Ruta al archivo de configuraci�n de los nombres de tablas de la base de datos: La unidad de persistencia existe y el esquema de la BD tambi�n
	 */
	private static final String CONFIG_TABLAS_A = "./src/main/resources/config/TablasBD_A.json"; 
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
    /**
     * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
     */
    
	/**
	 * La clase que se quiere probar
	 */
    private HotelAndes parranderos;
	
    /* ****************************************************************
	 * 			M�todos de prueba para la tabla ServicioBebida - Creaci�n y borrado
	 *****************************************************************/
	/**
	 * M�todo que prueba las operaciones sobre la tabla ServicioBebida
	 * 1. Adicionar un Servicio de bebida
	 * 2. Listar el contenido de la tabla con 0, 1 y 2 registros insertados
	 * 3. Borrar un Servicio de bebida por su identificador
	 * 4. Borrar un Servicio de bebida por su nombre
	 */
    @Test
	public void CRDServicioBebidaTest() 
	{
    	// Probar primero la conexi�n a la base de datos
		try
		{
			log.info ("Probando las operaciones CRD sobre ServicioBebida");
			parranderos = new HotelAndes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de CRD de Serviciobebida incompleta. No se pudo conectar a la base de datos !!. La excepci�n generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de CRD de Serviciobebida incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepci�n";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
    	try
		{
			// Lectura de los Servicios de bebida con la tabla vac�a
			List <Servicio> lista = parranderos.darServicios();
			assertEquals ("No debe haber Servicios de bebida creados!!", 0, lista.size ());

			// Lectura de los Servicios de bebida con un Servicio de bebida adicionado
			Servicio ServicioBebida1 = parranderos.adicionarServicio(20, 10, 0700, 2000, 30, "HOla", "descripcion") ;
			lista = parranderos.darServicios();
			assertEquals ("Debe haber un Servicio de bebida creado !!", 1, lista.size ());
			assertEquals ("El objeto creado y el traido de la BD deben ser iguales !!", ServicioBebida1, lista.get (0));

			// Lectura de los Servicios de bebida con dos Servicios de bebida adicionados
			Servicio ServicioBebida2 = parranderos.adicionarServicio(222, 10, 0700, 2000, 30, "HOla", "descripcion") ;
			lista = parranderos.darServicios();
			assertEquals ("Debe haber dos Servicios de bebida creados !!", 2, lista.size ());
			assertTrue ("El primer Servicio de bebida adicionado debe estar en la tabla", ServicioBebida1.equals (lista.get (0)) || ServicioBebida1.equals (lista.get (1)));
			assertTrue ("El segundo Servicio de bebida adicionado debe estar en la tabla", ServicioBebida2.equals (lista.get (0)) || ServicioBebida2.equals (lista.get (1)));

			
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecuci�n de las pruebas de operaciones sobre la tabla ServicioBebida.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepci�n";
			System.out.println (msg);

    		fail ("Error en las pruebas sobre la tabla ServicioBebida");
		}
		finally
		{
			parranderos.limpiarParranderos ();
    		parranderos.cerrarUnidadPersistencia ();    		
		}
	}

    /**
     * M�todo de prueba de la restricci�n de unicidad sobre el nombre de ServicioBebida
     */
	@Test
	public void unicidadServicioBebidaTest() 
	{
    	// Probar primero la conexi�n a la base de datos
		try
		{
			log.info ("Probando la restricci�n de UNICIDAD del nombre del Servicio de bebida");
			parranderos = new HotelAndes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de UNICIDAD de Serviciobebida incompleta. No se pudo conectar a la base de datos !!. La excepci�n generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de UNICIDAD de Serviciobebida incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepci�n";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
		try
		{
			// Lectura de los Servicios de bebida con la tabla vac�a
			List <Servicio> lista = parranderos.darServicios();
			assertEquals ("No debe haber Servicios de bebida creados!!", 0, lista.size ());

			// Lectura de los Servicios de bebida con un Servicio de bebida adicionado
			Servicio ServicioBebida1 = parranderos.adicionarServicio(2220, 10, 0700, 2000, 30, "HOla", "descripcion") ;
			lista = parranderos.darServicios();
			assertEquals ("Debe haber un Servicio de bebida creado !!", 1, lista.size ());

			Servicio ServicioBebida2 = parranderos.adicionarServicio(2340, 10, 0700, 2000, 30, "HOla", "descripcion") ;
			assertNull ("No puede adicionar dos Servicios de bebida con el mismo nombre !!", ServicioBebida2);
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecuci�n de las pruebas de UNICIDAD sobre la tabla ServicioBebida.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepci�n";
			System.out.println (msg);

    		fail ("Error en las pruebas de UNICIDAD sobre la tabla ServicioBebida");
		}    				
		finally
		{
			parranderos.limpiarParranderos ();
    		parranderos.cerrarUnidadPersistencia ();    		
		}
	}

	/* ****************************************************************
	 * 			M�todos de configuraci�n
	 *****************************************************************/
    /**
     * Lee datos de configuraci�n para la aplicaci�n, a partir de un archivo JSON o con valores por defecto si hay errores.
     * @param Servicio - El Servicio de configuraci�n deseada
     * @param archConfig - Archivo Json que contiene la configuraci�n
     * @return Un objeto JSON con la configuraci�n del Servicio especificado
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
			JOptionPane.showMessageDialog(null, "No se encontr� un archivo de configuraci�n de tablas v�lido: ", "ServicioBebidaTest", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }	

}
