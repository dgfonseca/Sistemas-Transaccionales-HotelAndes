package negocio;

public class Plan implements VOPlan{

	private long id;
	private String descripcion;
	private String nombre;
	
	public Plan()
	{
		this.id=0;
		this.descripcion="";
		this.nombre="";
	}
	public Plan(long pid,String pdescripcion, String pnombre)
	{
		this.id=pid;
		this.descripcion=pdescripcion;
		this.nombre=pnombre;
	}
	@Override
	public long geId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String getDescripcion() {
		// TODO Auto-generated method stub
		return descripcion;
		
	}
	
	public String getNombre()
	{
		return nombre;
	}
	public void setNombre(String pnombre)
	{
		this.nombre=pnombre;
	}
	public void setId(long pid)
	{
		this.id=pid;
	}
	public void setDescripcion(String pdescripcion)
	{
		this.descripcion=pdescripcion;
	}

	public String toString()
	{
		return "plan [id=" + id + ", descripcion=" + descripcion + "]";
	}
}
