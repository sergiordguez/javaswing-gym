package persistencia.socio;

import java.sql.Timestamp;
import java.util.List;

import persistencia.administrador.ActividadDto;
import persistencia.administrador.AlquilerDto;
import persistencia.administrador.CuotaDto;
import persistencia.administrador.InstalacionDto;
import persistencia.administrador.ReservaDto;
import util.Database;

public class SocioCRUD {
	Database db;

	public SocioCRUD(Database db) {
		this.db = db;
	}

	public SocioDto[] getSocios() {
		SocioDto[] listaSocios = db.executeQueryPojo(SocioDto.class, "SELECT * FROM SOCIO").toArray(new SocioDto[0]);
		return listaSocios;
	}

	public ActividadDto[] listarActividades(Timestamp fecha) {
		ActividadDto[] listaActividades = db
				.executeQueryPojo(ActividadDto.class, "SELECT * FROM ACTIVIDAD WHERE fechaInicio = ?", fecha.toString())
				.toArray(new ActividadDto[0]);
		return listaActividades;
	}

	public List<ReservaDto> listarActividades(SocioDto sc) {
		List<ReservaDto> listaActividades = db.executeQueryPojo(ReservaDto.class,
				"SELECT * FROM RESERVA WHERE idSocio = ?", sc.getIdSocio());
		return listaActividades;
	}

	public void setReserva(SocioDto socio, ActividadDto dto) {
		db.executeUpdate("INSERT INTO RESERVA VALUES (?, ?)", socio.getIdSocio(), dto.getIdActividad());
	}

	public void cancelReserva(SocioDto socio, ActividadDto dto) {
		db.executeUpdate("UPDATE RESERVA SET (?, ?) WHERE (?, ?)", socio.getIdSocio(), dto.getIdActividad(),
				socio.getIdSocio(), dto.getIdActividad());
	}

	public List<ActividadDto> listarActividadesHoy(Timestamp fecha) {
		String ini = calcularIni(fecha);
		String fin = calcularFin(fecha);
		List<ActividadDto> listaActividades = db.executeQueryPojo(ActividadDto.class,
				"SELECT * FROM ACTIVIDAD WHERE fechaInicio BETWEEN ? AND ?", ini, fin);

		return listaActividades;
	}

	public InstalacionDto[] listarInstalaciones() {
		InstalacionDto[] instalaciones = db.executeQueryPojo(InstalacionDto.class, "SELECT nombre FROM INSTALACION")
				.toArray(new InstalacionDto[0]);
		return instalaciones;
	}

	public void setAlquiler(SocioDto socio, Timestamp fecha, InstalacionDto dto) {
		db.executeUpdate("INSERT INTO ALQUILER VALUES (?, ?, ?, ?, ?,?, ?)", dto.getNombre(), fecha, fecha, 1, 0,
				socio.getIdSocio(), dto.getNombre());
	}

	public void restarPlazas(ActividadDto dto) {
		db.executeUpdate("UPDATE ACTIVIDAD SET plazasDisponibles = plazasDisponibles - 1 WHERE idActividad = ?",
				dto.getIdActividad());
	}

	public boolean existeReservaNumero(String numero, ActividadDto actividad) {
		List<ReservaDto> listaReservas = db.executeQueryPojo(ReservaDto.class,
				"SELECT * FROM RESERVA R WHERE r.idSocio = ? AND r.idActividad = ?", numero,
				actividad.getIdActividad());

		return listaReservas.size() > 0;
	}

	public List<ReservaDto> listarReservas(SocioDto sc) {
		List<ReservaDto> listaActividades = db.executeQueryPojo(ReservaDto.class,
				"SELECT * FROM RESERVA WHERE idSocio = ?", sc.getIdSocio());
		return listaActividades;
	}

	public List<ActividadDto> listarActividadesFecha(String idActividad) {
		List<ActividadDto> listActividades = db.executeQueryPojo(ActividadDto.class,
				"SELECT * FROM ACTIVIDAD WHERE idActividad = ?", idActividad);
		return listActividades;
	}

	public List<AlquilerDto> listarAlquileres(SocioDto sc) {
		List<AlquilerDto> listaActividades = db.executeQueryPojo(AlquilerDto.class,
				"SELECT * FROM ALQUILER WHERE idSocio = ?", sc.getIdSocio());
		return listaActividades;
	}

	public AlquilerDto[] listarAlquileresMes(SocioDto socio) {
		AlquilerDto[] listarAlquileres = db.executeQueryPojo(AlquilerDto.class,
				"SELECT * FROM ALQUILER WHERE (idSocio = ?) AND (enVigor = 1) AND (pagado = 1)", socio.getIdSocio())
				.toArray(new AlquilerDto[0]);
		return listarAlquileres;
	}

	public CuotaDto[] listarCuota(String mes, SocioDto socio) {

		CuotaDto[] listarCuotas = db.executeQueryPojo(CuotaDto.class,
				"SELECT * FROM CUOTA WHERE idSocio = ? AND mes = ?", socio.getIdSocio(), mes).toArray(new CuotaDto[0]);
		return listarCuotas;
	}

	public List<ActividadDto> listarActividadesDia(InstalacionDto instalacion, Timestamp dia) {
		List<ActividadDto> listaActividades = db.executeQueryPojo(ActividadDto.class,
				"SELECT * FROM ACTIVIDAD A WHERE a.instalacion = ? AND a.fechaInicio BETWEEN ? AND ?",
				instalacion.getNombre(), calcularInicioDia(dia).toString(), calcularFinDia(dia).toString());

		return listaActividades;
	}

	public List<AlquilerDto> listarAlquileresDia(InstalacionDto instalacion, Timestamp dia) {
		List<AlquilerDto> listaActividades = db.executeQueryPojo(AlquilerDto.class,
				"SELECT * FROM ALQUILER A WHERE a.instalacion = ? AND a.fechaInicio BETWEEN ? AND ?",
				instalacion.getNombre(), calcularInicioDia(dia).toString(), calcularFinDia(dia).toString());

		return listaActividades;
	}

	public void añadirAlquiler(AlquilerDto alq) {
		db.executeUpdate(
				"INSERT INTO ALQUILER(idAlquiler, fechaInicio, fechaFin, enVigor, pagado, idSocio, instalacion) VALUES(?, ?, ?, ?, ?, ?, ?)",
				alq.getIdAlquiler(), alq.getFechaInicio(), alq.getFechaFin(), alq.getEnVigor(), alq.getPagado(),
				alq.getIdSocio(), alq.getInstalacion());
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

	@SuppressWarnings("deprecation")
	private Timestamp calcularInicioDia(Timestamp date) {
		return new Timestamp(date.getYear(), date.getMonth(), date.getDate(), 0, 0, 0, 0);
	}

	@SuppressWarnings("deprecation")
	private Timestamp calcularFinDia(Timestamp date) {
		return new Timestamp(date.getYear(), date.getMonth(), date.getDate() + 1, 0, 0, 0, 0);
	}

	public List<AlquilerDto> listarAlquileresActivos(SocioDto sc) {
		List<AlquilerDto> listaActividades = db.executeQueryPojo(AlquilerDto.class,
				"SELECT * FROM ALQUILER WHERE idSocio = ? AND enVigor = 1", sc.getIdSocio());
		return listaActividades;
	}

	public void cancelAlquiler(AlquilerDto dto) {
		db.executeUpdate("UPDATE ALQUILER SET enVigor = 0, pagado = 1 WHERE idAlquiler = ?", dto.getIdAlquiler());
	}
}
