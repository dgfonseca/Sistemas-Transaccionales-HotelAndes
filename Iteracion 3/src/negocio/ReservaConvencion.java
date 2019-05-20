package negocio;

public class ReservaConvencion implements VOReservaConvencion {

	private long id;
	private long fechaInicio;
	private long fechaFin;
	private double costo;
	private long planId;
	private long convencionId;
	public ReservaConvencion() {
		this.id=0;
		this.fechaInicio=0;
		this.fechaFin=0;
		this.costo=0;
		this.planId=0;
		this.convencionId=0;
	}
	public ReservaConvencion(long pid, long pi, long pf, double pcosto, long pplan, long pconvencion)
	{
		this.id=pid;
		this.fechaInicio=pi;
		this.fechaFin=pf;
		this.costo=pcosto;
		this.planId=pplan;
		this.convencionId=pconvencion;
				
	}

	public void setPlanId(long pid)
	{
		this.planId=pid;
	}
	public void setConvencionId(long pid)
	{
		this.convencionId=pid;
	}
	@Override
	public long getConvencionId()
	{
		return convencionId;
	}
	@Override
	public long getPlanId()
	{
		return planId;
	}
	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public long getFechaInicio() {
		// TODO Auto-generated method stub
		return fechaInicio;
	}

	@Override
	public long getFechaFin() {
		// TODO Auto-generated method stub
		return fechaFin;
	}

	@Override
	public double getCosto() {
		// TODO Auto-generated method stub
		return costo;
	}
	public void setCosto(double pcosto)
	{
		this.costo=pcosto;
	}
	public void setFechaFin(long pfin)
	{
		this.fechaFin=pfin;
	}
	public void setFechaInicio(long pinicio)
	{
		this.fechaInicio=pinicio;
	}
	public void setId(long pid)
	{
		this.id=pid;
	}

}
