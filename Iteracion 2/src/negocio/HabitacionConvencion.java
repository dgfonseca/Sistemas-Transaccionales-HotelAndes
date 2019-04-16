package negocio;

public class HabitacionConvencion implements VOHabitacionConvencion {

	private long idReserva;
	private int numeroHabitacion;
	public HabitacionConvencion() {
		this.idReserva=0;
		this.numeroHabitacion=0;
	}
	public HabitacionConvencion(long pid, int x) {
		this.idReserva=pid;
		this.numeroHabitacion=x;
	}

	public void setIdReserva(long pid)
	{
		this.idReserva=pid;
	}
	public void setNumeroHabitacion(int pid)
	{
		this.numeroHabitacion=pid;
	}
	@Override
	public long getIdReserva() {
		// TODO Auto-generated method stub
		return idReserva;
	}

	@Override
	public int getNumeroHabitacion() {
		// TODO Auto-generated method stub
		return numeroHabitacion;
	}

}
