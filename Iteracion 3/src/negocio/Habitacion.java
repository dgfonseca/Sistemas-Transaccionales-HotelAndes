package negocio;

public class Habitacion implements VOHabitacion{

	private int capacidad;
	private int numeroHabitacion;
	private int costo;
	private String descripcion;
	private String disponible;
	
	public Habitacion()
	{
		this.capacidad=0;
		this.numeroHabitacion=0;
		this.costo=0;
		this.descripcion="";
		this.disponible="T";
	}
	public Habitacion(int pcapacidad, int pnumerohabitacion, int pcosto, String pdescripcion, String pdisponible)
	{
		this.capacidad=pcapacidad;
		this.numeroHabitacion=pnumerohabitacion;
		this.costo=pcosto;
		this.descripcion=pdescripcion;
		this.disponible=pdisponible;
	}
	

	
	@Override
	public int getCapacidad() {
		// TODO Auto-generated method stub
		return capacidad;
	}

	@Override
	public int getNumeroHabitacion() {
		// TODO Auto-generated method stub
		return numeroHabitacion;
	}

	@Override
	public int getCosto() {
		// TODO Auto-generated method stub
		return costo;
	}

	@Override
	public String getDescripcion() {
		// TODO Auto-generated method stub
		return descripcion;
	}

	@Override
	public String isDisponible() {
		// TODO Auto-generated method stub
		return disponible;
	}
	public void setCapacidad(int pcapacidad)
	{
		this.capacidad=pcapacidad;
	}
	public void setNumeroHabitacion(int pnumero)
	{
		this.numeroHabitacion=pnumero;
	}
	public void setCosto(int pcosto)
	{
		this.costo=pcosto;
	}
	public void setDescripcion(String pDescripcion)
	{
		this.descripcion=pDescripcion;
	}
	public void setDisponible(String pdisponible)
	{
		this.disponible=pdisponible;
	}
	
	
	public String toString()
	{
		return "Habitacion [capacidad=" + capacidad + ", numeroHabitacion=" + numeroHabitacion + ", costo=" + costo +", descripcion"+descripcion+", disponible"+disponible+ "]";
	}

}
