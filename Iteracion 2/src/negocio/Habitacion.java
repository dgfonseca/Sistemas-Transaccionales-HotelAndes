package negocio;

public class Habitacion implements VOHabitacion{

	private int capacidad;
	private int numeroHabitacion;
	private double costo;
	private String descripcion;
	private char disponible;
	
	public Habitacion()
	{
		this.capacidad=0;
		this.numeroHabitacion=0;
		this.costo=0;
		this.descripcion="";
		this.disponible='T';
	}
	public Habitacion(int pcapacidad, int pnumerohabitacion, double pcosto, String pdescripcion, char pdisponible)
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
	public double getCosto() {
		// TODO Auto-generated method stub
		return costo;
	}

	@Override
	public String getDescripcion() {
		// TODO Auto-generated method stub
		return descripcion;
	}

	@Override
	public char isDisponible() {
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
	public void setCosto(double pcosto)
	{
		this.costo=pcosto;
	}
	public void setDescripcion(String pDescripcion)
	{
		this.descripcion=pDescripcion;
	}
	public void setDisponible(char pdisponible)
	{
		this.disponible=pdisponible;
	}
	
	
	public String toString()
	{
		return "Habitacion [capacidad=" + capacidad + ", numeroHabitacion=" + numeroHabitacion + ", costo=" + costo +", descripcion"+descripcion+", disponible"+disponible+ "]";
	}

}
