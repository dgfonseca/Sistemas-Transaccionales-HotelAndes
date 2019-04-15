package negocio;

public class Tipo implements VOTipo {

	private long id;
	private String nombre;
	public Tipo() {
		this.id=0;
		this.nombre="";
		// TODO Auto-generated constructor stub
	}
	public Tipo(long pid,String pnombre)
	{
		this.id=pid;
		this.nombre=pnombre;
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
	public void setId(long pid)
	{
		this.id=pid;
		
	}
	public void setNombre(String pnombre)
	{
		this.nombre=pnombre;
	}

}
