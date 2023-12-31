package persistencia.administrador;

public class AdministradorDto {
	private String idAdministrador;

	public AdministradorDto() {

	}

	public AdministradorDto(String idAdministrador) {
		this.idAdministrador = idAdministrador;
	}

	public String getIdAdministrador() {
		return idAdministrador;
	}

	public void setIdAdministrador(String idAdministrador) {
		this.idAdministrador = idAdministrador;
	}

	@Override
	public String toString() {
		return idAdministrador;
	}
}
