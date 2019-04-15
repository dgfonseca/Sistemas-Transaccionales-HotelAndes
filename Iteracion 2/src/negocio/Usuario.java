package negocio;

public class Usuario implements VOUsuario{
	

	
	
	private long identificacion;
	private String nombre;
	private String tipoIdentificacion;
	private long idTipo;
	private String correoElectronico;

	
	public Usuario()
	{
		this.identificacion=0;
		this.nombre="";
		this.tipoIdentificacion="";
		this.idTipo=0;
		this.correoElectronico="";
	}
	public Usuario(long pidentificacion, String pid,long ptipo,String pcorreo, String pNombre)
	{
		this.identificacion=pidentificacion;
		this.tipoIdentificacion=pid;
		this.idTipo=ptipo;
		this.correoElectronico=pcorreo;
		this.nombre=pNombre;
	}


	@Override
	public long getIdentificacion() {
		// TODO Auto-generated method stub
		return identificacion;
	}

	@Override
	public String getNombre() {
		// TODO Auto-generated method stub
		return nombre;
	}

	@Override
	public String getTipoIdentificacion() {
		// TODO Auto-generated method stub
		return tipoIdentificacion;
	}

	@Override
	public long getIdTipo() {
		// TODO Auto-generated method stub
		return idTipo;
	}

	@Override
	public String getCorreoElectronico() {
		// TODO Auto-generated method stub
		return correoElectronico;
	}
	public void setIdentificacion(long pid)
	{
		this.identificacion=pid;
	}
	public void setNombre(String pnombre)
	{
		this.nombre=pnombre;
	}
	public void setTipoIdentificacion(String ptipo)
	{
		this.tipoIdentificacion=ptipo;
	}
	public void setTipo(long ptipo)
	{
		this.idTipo=ptipo;
	}
	public void setCorreoElectronico(String pcorreo)
	{
		this.correoElectronico=pcorreo;
	}

}
