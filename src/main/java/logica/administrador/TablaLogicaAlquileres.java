package logica.administrador;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import persistencia.administrador.AdministradorCRUD;
import persistencia.administrador.AlquilerDto;

public class TablaLogicaAlquileres {
	private AdministradorCRUD ac;
	public final static int DIM = 128;
	private List<ArrayList<AlquilerDto>> tabla;

	public TablaLogicaAlquileres(AdministradorCRUD ac) {
		this.ac = ac;
	}

	public void generarTabla(Timestamp fecha, String instalacion) {
		this.tabla = new ArrayList<>();
		for (int i = 0; i < DIM; i++) {
			tabla.add(new ArrayList<AlquilerDto>());
		}

		List<AlquilerDto> listaAlquileres = ac.listarAlquileres(fecha, instalacion);
		for (AlquilerDto AlquilerDto : listaAlquileres) {
			colocarAlquiler(AlquilerDto);
		}
	}

	@SuppressWarnings("deprecation")
	private void colocarAlquiler(AlquilerDto AlquilerDto) {
		int horaInicio = AlquilerDto.getFechaInicio().getHours();
		int horaFin = AlquilerDto.getFechaFin().getHours();
		int dia = AlquilerDto.getFechaInicio().getDay();
		int columna = obtenerColumnaParaDia(dia);

		for (int i = horaInicio - 8; i < horaFin - 8; i++) {
			tabla.get((i + 1) * 8 + (columna + 1)).add(AlquilerDto);
		}
	}

	private int obtenerColumnaParaDia(int dia) {
		if (dia != 0) {
			return dia - 1;
		} else {
			return 6;
		}
	}

	public ArrayList<AlquilerDto> obtenerListaAlquileres(int posicion) {
		return tabla.get(posicion);
	}
}
