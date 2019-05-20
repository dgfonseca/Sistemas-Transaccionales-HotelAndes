package negocio;

public class HabitacionMantenimiento implements VOHabitacionMantenimiento {
	private long idMantenimiento;
	private int numeroHabitacion;
	public HabitacionMantenimiento() {
		this.numeroHabitacion=0;
		this.idMantenimiento=0;
	}
	public HabitacionMantenimiento(long id, int numero)
	{
		this.idMantenimiento=id;
		this.numeroHabitacion=numero;
	}

	public void setIdMantenimiento(long pid)
	{
		this.idMantenimiento=pid;
	}
	public void setNumeroHabitacion(int num)
	{
		this.numeroHabitacion=num;
	}
	@Override
	public long getIdMantenimiento() {
		// TODO Auto-generated method stub
		return idMantenimiento;
	}

	@Override
	public int getNumeroHabitacion() {
		// TODO Auto-generated method stub
		return numeroHabitacion;
	}

}
