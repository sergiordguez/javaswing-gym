package persistencia.administrador;

public class RecursoDto {
	private String nombre;

	public RecursoDto() {

	}

	public RecursoDto(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return nombre;
	}

}