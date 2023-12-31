package persistencia.administrador;

public class ReservaDto {
	private String idSocio;
	private String idActividad;

	public ReservaDto() {

	}

	public ReservaDto(String idSocio, String idActividad) {
		this.idSocio = idSocio;
		this.idActividad = idActividad;
	}

	public String getIdSocio() {
		return idSocio;
	}

	public void setIdSocio(String idSocio) {
		this.idSocio = idSocio;
	}

	public String getIdActividad() {
		return idActividad;
	}

	public void setIdActividad(String idActividad) {
		this.idActividad = idActividad;
	}

	@Override
	public String toString() {
		return idActividad;
	}
}
