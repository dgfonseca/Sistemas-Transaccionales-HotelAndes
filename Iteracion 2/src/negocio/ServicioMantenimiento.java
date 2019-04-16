package negocio;

public class ServicioMantenimiento implements VOServicioMantenimiento {

	private long idMantenimiento;
	private long idServicio;
	public ServicioMantenimiento() {
		this.idMantenimiento=0;
		this.idServicio=0;
		}
	public ServicioMantenimiento(long pidm, long pids) {
		this.idMantenimiento=pidm;
		this.idServicio=pids;
		}

	public void setIdMantenimiento(long pid)
	{
		this.idMantenimiento=pid;
	}
	public void setIdServicio(long pid)
	{
		this.idServicio=pid;
	}
	@Override
	public long getIdMantenimiento() {
		// TODO Auto-generated method stub
		return idMantenimiento;
	}

	@Override
	public long getIdServicio() {
		// TODO Auto-generated method stub
		return idServicio;
	}

}
