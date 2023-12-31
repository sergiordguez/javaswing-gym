package logica.monitor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistencia.administrador.ActividadDto;
import persistencia.monitor.MonitorCRUD;

public class TablaLogicaMonitor {

	private MonitorCRUD mc;
	public final static int DIM = 15;
	private Map<Integer, List<ActividadDto>> tabla = new HashMap<>();

	public TablaLogicaMonitor(MonitorCRUD mc) {
		this.mc = mc;
	}

	public void generarTabla(Timestamp fecha) {
		tabla = new HashMap<>();
		List<ActividadDto> actividades = mc.listarActividadesHoy(fecha);

		for (ActividadDto act : actividades) {
			int firstHour = act.getFechaInicio().getHours();
			int lastHour = act.getFechaFin().getHours();

			for (int i = firstHour; i < lastHour; i++) {
				if (tabla.get(i) != null) {
					tabla.get(i).add(act);
				} else {
					tabla.put(i, new ArrayList<>());
					tabla.get(i).add(act);
				}

			}
		}
	}

	public Map<Integer, List<ActividadDto>> getTabla() {
		return tabla;
	}
}
