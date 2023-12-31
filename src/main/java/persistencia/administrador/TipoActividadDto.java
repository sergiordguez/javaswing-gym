package persistencia.administrador;

public class TipoActividadDto {
	private String nombre;
	private String intensidad;

	public TipoActividadDto() {

	}

	public TipoActividadDto(String nombre, String intensidad) {
		this.setNombre(nombre);
		this.setIntensidad(intensidad);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIntensidad() {
		return intensidad;
	}

	public void setIntensidad(String intensidad) {
		this.intensidad = intensidad;
	}

	@Override
	public String toString() {
		return nombre;
	}

}
