package persistencia.monitor;

import java.sql.Timestamp;
import java.util.List;

import persistencia.administrador.ActividadDto;
import persistencia.administrador.MonitorDto;
import persistencia.socio.SocioDto;
import util.Database;

public class MonitorCRUD {

	Database db;

	public MonitorCRUD(Database db) {
		this.db = db;
	}

	public MonitorDto[] getMonitores() {
		MonitorDto[] listaMonitores = db.executeQueryPojo(MonitorDto.class, "SELECT * FROM MONITOR")
				.toArray(new MonitorDto[0]);
		return listaMonitores;
	}

	public List<ActividadDto> listarActividades(MonitorDto dto) {
		List<ActividadDto> listaActividades = db.executeQueryPojo(ActividadDto.class,
				"SELECT * FROM ACTIVIDAD WHERE monitor = ?", dto.getIdMonitor());
		return listaActividades;
	}

	public void setPlaza(String dto, ActividadDto act) {
		db.executeUpdate("INSERT INTO RESERVA VALUES (? ,?)", dto, act.getIdActividad());
	}

	public List<SocioDto> getParticipantes(ActividadDto dto) {
		List<SocioDto> socios = db.executeQueryPojo(SocioDto.class, "SELECT idSocio FROM RESERVA WHERE idActividad = ?",
				dto.getIdActividad());
		return socios;
	}

	public List<ActividadDto> listarActividadesHoy(Timestamp fecha) {
		String ini = calcularIni(fecha);
		String fin = calcularFin(fecha);
		List<ActividadDto> listaActividades = db.executeQueryPojo(ActividadDto.class,
				"SELECT * FROM ACTIVIDAD WHERE fechaInicio BETWEEN ? AND ?", ini, fin);

		return listaActividades;
	}

	@SuppressWarnings("deprecation")
	private String calcularIni(Timestamp fecha) {
		String sFecha = fecha.toString().substring(0, 8);

		int diaDelMes = fecha.getDate();

		if (diaDelMes < 10) {
			sFecha += "0" + diaDelMes;
		} else {
			sFecha += diaDelMes;
		}
		sFecha += " 00:00:00";
		return sFecha;
	}

	@SuppressWarnings("deprecation")
	private String calcularFin(Timestamp fecha) {
		String sFecha = fecha.toString().substring(0, 8);

		int diaDelMes = fecha.getDate() + 1;

		if (diaDelMes < 10) {
			sFecha += "0" + diaDelMes;
		} else {
			sFecha += diaDelMes;
		}
		sFecha += " 00:00:00";
		return sFecha;
	}
}
