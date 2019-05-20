package negocio;

public class ServiciosConvencion implements VOServiciosConvencion {

	private long idReserva;
	private long idServicio;

	public ServiciosConvencion() {
		this.idReserva=0;
		this.idServicio=0;
	}
	public ServiciosConvencion(long pidr,long pids)
	{
		this.idReserva=pidr;
		this.idServicio=pids;
	}

	public void setIdReserva(long pid)
	{
		this.idReserva=pid;
	}
	public void setIdServicio(long pid)
	{
		this.idServicio=pid;
	}
	@Override
	public long getIdReserva() {
		// TODO Auto-generated method stub
		return idReserva;
	}

	@Override
	public long getIdServicio() {
		// TODO Auto-generated method stub
		return idServicio;
	}

}
