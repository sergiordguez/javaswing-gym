package logica.administrador;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import persistencia.administrador.ActividadDto;
import persistencia.administrador.AdministradorCRUD;

public class TablaLogica {
	private AdministradorCRUD ac;
	public final static int DIM = 128;
	private List<ArrayList<ActividadDto>> tabla;

	public TablaLogica(AdministradorCRUD ac) {
		this.ac = ac;
	}

	public void generarTabla(Timestamp fecha, String instalacion) {
		this.tabla = new ArrayList<>();
		for (int i = 0; i < DIM; i++) {
			tabla.add(new ArrayList<ActividadDto>());
		}

		List<ActividadDto> listaActividades = ac.listarActividades(fecha, instalacion);
		for (ActividadDto actividadDto : listaActividades) {
			colocarActividad(actividadDto);
		}
	}

	@SuppressWarnings("deprecation")
	private void colocarActividad(ActividadDto actividadDto) {
		int horaInicio = actividadDto.getFechaInicio().getHours();
		int horaFin = actividadDto.getFechaFin().getHours();
		int dia = actividadDto.getFechaInicio().getDay();
		int columna = obtenerColumnaParaDia(dia);

		for (int i = horaInicio - 8; i < horaFin - 8; i++) {
			tabla.get((i + 1) * 8 + (columna + 1)).add(actividadDto);
		}
	}

	private int obtenerColumnaParaDia(int dia) {
		if (dia != 0) {
			return dia - 1;
		} else {
			return 6;
		}
	}

	public ArrayList<ActividadDto> obtenerListaActividades(int posicion) {
		return tabla.get(posicion);
	}
}
