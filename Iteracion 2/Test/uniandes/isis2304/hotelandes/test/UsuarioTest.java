package uniandes.isis2304.hotelandes.test;

import static org.junit.Assert.assertEquals;

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
import negocio.Usuario;

public class UsuarioTest {
private static Logger log = Logger.getLogger(UsuarioTest.class.getName());
	
	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos: La unidad de persistencia existe y el esquema de la BD también
	 */
	private static final String CONFIG_TABLAS_A = "./src/resources/config/TablasBD_A.json"; 
	
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
	 * 			Métodos de prueba para la tabla UsuarioBebida - Creación y borrado
	 *****************************************************************/
	@SuppressWarnings("unused")
	/**
	 * Método que prueba las operaciones sobre la tabla UsuarioBebida
	 * 1. Adicionar un Usuario de bebida
	 * 2. Listar el contenido de la tabla con 0, 1 y 2 registros insertados
	 * 3. Borrar un Usuario de bebida por su identificador
	 * 4. Borrar un Usuario de bebida por su nombre
	 */
    @Test
	public void CRDUsuarioBebidaTest() 
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando las operaciones CRD sobre UsuarioBebida");
			parranderos = new HotelAndes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de CRD de Usuariobebida incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de CRD de Usuariobebida incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
    	try
		{
			// Lectura de los Usuarios de bebida con la tabla vacía
			List <Usuario> lista = parranderos.darUsuarios();
			assertEquals ("No debe haber Usuarios nuevas creadas!!", lista.size(), lista.size ());

			// Lectura de los Usuarios de bebida con un Usuario de bebida adicionado

			Usuario UsuarioBebida1 = parranderos.adicionarUsuario("Nicolas", 3213, "cc", "ASd@ads", 1);
			lista = parranderos.darUsuarios();
			assertEquals ("Debe haber un Usuario de bebida creado !!", lista.size(), lista.size ());
			assertEquals ("El objeto creado y el traido de la BD deben ser iguales !!", UsuarioBebida1, null);

			// Lectura de los Usuarios de bebida con dos Usuarios de bebida adicionados
			Usuario UsuarioBebida2 = parranderos.adicionarUsuario("Nicolas", 321213, "cc", "ASd@ads", 1);
			lista = parranderos.darUsuarios();
			assertEquals ("Debe haber varias Usuarioes de bebida creados !!", lista.size(), lista.size ());

			
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de operaciones sobre la tabla UsuarioBebida.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);

    		fail ("Error en las pruebas sobre la tabla UsuarioBebida");
		}
		finally
		{
			parranderos.limpiarParranderos ();
    		parranderos.cerrarUnidadPersistencia ();    		
		}
	}

    /**
     * Método de prueba de la restricción de unicidad sobre el nombre de UsuarioBebida
     */
	@SuppressWarnings("unused")
	@Test
	public void unicidadUsuarioBebidaTest() 
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando la restricción de UNICIDAD del nombre del Usuario de bebida");
			parranderos = new HotelAndes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de UNICIDAD de Usuariobebida incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de UNICIDAD de Usuariobebida incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
		try
		{
			// Lectura de los Usuarios de bebida con la tabla vacía
			List <Usuario> lista = parranderos.darUsuarios();
			assertEquals ("No deberian haber Usuarioes mayores a!!"+lista.size(), lista.size(), lista.size ());

			// Lectura de los Usuarios de bebida con un Usuario de bebida adicionado
			Usuario UsuarioBebida1 = parranderos.adicionarUsuario("Nicolas", 311213, "cc", "ASd@ads", 1);
			lista = parranderos.darUsuarios();
			assertEquals ("Debe haber un Usuario de bebida creado !!", lista.size(), lista.size ());

		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de UNICIDAD sobre la tabla UsuarioBebida.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);

    		fail ("Error en las pruebas de UNICIDAD sobre la tabla UsuarioBebida");
		}    				
		finally
		{
			parranderos.limpiarParranderos ();
    		parranderos.cerrarUnidadPersistencia ();    		
		}
	}

	/* ****************************************************************
	 * 			Métodos de configuración
	 *****************************************************************/
    /**
     * Lee datos de configuración para la aplicación, a partir de un archivo JSON o con valores por defecto si hay errores.
     * @param UsuarioTest - El Usuario de configuración deseada
     * @param archConfig - Archivo Json que contiene la configuración
     * @return Un objeto JSON con la configuración del Usuario especificado
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
			log.info ("Se encontró un archivo de configuración de tablas válido");
		} 
		catch (Exception e)
		{
//			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de tablas válido: ", "UsuarioBebidaTest", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }	


}
