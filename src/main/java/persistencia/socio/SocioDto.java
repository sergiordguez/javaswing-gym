package persistencia.socio;

public class SocioDto {

	private String idSocio;
	private String nombre;
	private String apellido;
	private String infoContacto;
	private String dni;

	public SocioDto() {

	}

	public SocioDto(String idSocio, String nombre, String apellido, String infoContacto, String dni) {
		this.idSocio = idSocio;
		this.nombre = nombre;
		this.apellido = apellido;
		this.infoContacto = infoContacto;
		this.dni = dni;
	}

	public String getIdSocio() {
		return idSocio;
	}

	public void setIdSocio(String idSocio) {
		this.idSocio = idSocio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getInfoContacto() {
		return infoContacto;
	}

	public void setInfoContacto(String infoContacto) {
		this.infoContacto = infoContacto;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	@Override
	public String toString() {
		return nombre;
	}

}
