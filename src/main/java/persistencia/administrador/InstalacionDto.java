package persistencia.administrador;

public class InstalacionDto {
	private String nombre;
	private double precioPorHora;

	public InstalacionDto() {

	}

	public InstalacionDto(String nombre, double precioPorHora) {
		this.nombre = nombre;
		this.precioPorHora = precioPorHora;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecioPorHora() {
		return precioPorHora;
	}

	public void setPrecioPorHora(double precioPorHora) {
		this.precioPorHora = precioPorHora;
	}

	@Override
	public String toString() {
		return nombre;
	}
}