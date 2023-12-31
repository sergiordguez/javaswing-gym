package persistencia.administrador;

public class NecesitaDto {
	private String nombreTipoActividad;
	private String nombreRecurso;

	public NecesitaDto() {

	}

	public NecesitaDto(String nombreTipoActividad, String nombreRecurso) {
		this.nombreTipoActividad = nombreTipoActividad;
		this.nombreRecurso = nombreRecurso;
	}

	public String getNombreTipoActividad() {
		return nombreTipoActividad;
	}

	public void setNombreTipoActividad(String nombreTipoActividad) {
		this.nombreTipoActividad = nombreTipoActividad;
	}

	public String getNombreRecurso() {
		return nombreRecurso;
	}

	public void setNombreRecurso(String nombreRecurso) {
		this.nombreRecurso = nombreRecurso;
	}
}
