package negocio;

public class Convencion implements VOConvencion {

	public long id;
	public String nombre;
	public String descripcion;
	public Convencion() {
		this.id=0;
		this.nombre="";
		this.descripcion="";
	}
	public Convencion(long pid, String pnombre, String pdescripcion) {
		this.id=pid;
		this.nombre=pnombre;
		this.descripcion=pdescripcion;
	}

	public void setId(long pid)
	{
		this.id=pid;
	}
	public void setNombre(String pnombre)
	{
		this.nombre=pnombre;
	}
	public void setDescripcion(String pdescripcion)
	{
		this.descripcion=pdescripcion;
	}
	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String getNombre() {
		// TODO Auto-generated method stub
		return nombre;
	}

	@Override
	public String getDescripcion() {
		// TODO Auto-generated method stub
		return descripcion;
	}

}
