package igu.administrador;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import persistencia.administrador.ActividadDto;
import persistencia.socio.SocioDto;

public class PanelActividad extends JPanel {
	private static final long serialVersionUID = 1L;
	private VentanaAsignacionMonitores vam;
	private ActividadDto actividadDto;
	private JPanel pnBoton;
	private JButton btnAsignarMonitor;
	private JTextPane tPInformacion;
	private JButton btnCancelar;
	private JButton btnReservarPlaza;
	private List<String> listaSociosApuntados = new ArrayList<>();

	public PanelActividad(VentanaAsignacionMonitores ventanaAsignacionMonitores, ActividadDto actividadDto) {
		this.vam = ventanaAsignacionMonitores;
		this.actividadDto = actividadDto;
		setLayout(new GridLayout(2, 0, 0, 0));
		add(getTPInformacion());
		add(getPnBoton());
	}

	public VentanaAsignacionMonitores getVentanaAsignacionMonitores() {
		return vam;
	}

	public ActividadDto getActividadDto() {
		return actividadDto;
	}

	private JPanel getPnBoton() {
		if (pnBoton == null) {
			pnBoton = new JPanel();
			pnBoton.setLayout(null);
			pnBoton.add(getBtnAsignarMonitor());
			pnBoton.add(getBtnCancelar());
			habilitaBotonReserva();
		}
		return pnBoton;
	}

	private void habilitaBotonReserva() {
		if (actividadDto.getPlazasTotales() > 0) {
			pnBoton.add(getBtnReservarPlaza());
			if (actividadDto.getPlazasDisponibles() == 0
					|| !esElMismoDia(actividadDto.getFechaInicio(), new Timestamp(System.currentTimeMillis()))) {
				getBtnReservarPlaza().setEnabled(false);
			}
		}
	}

	private boolean esElMismoDia(Timestamp fecha1, Timestamp fecha2) {
		LocalDate localDate1 = fecha1.toLocalDateTime().toLocalDate();
		LocalDate localDate2 = fecha2.toLocalDateTime().toLocalDate();

		return localDate1.equals(localDate2);
	}

	private JButton getBtnAsignarMonitor() {
		if (btnAsignarMonitor == null) {
			btnAsignarMonitor = new JButton("Asignar monitor");
			btnAsignarMonitor.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					validarMonitor();
				}
			});
			btnAsignarMonitor.setForeground(Color.BLACK);
			btnAsignarMonitor.setFont(new Font("Arial", Font.BOLD, 13));
			btnAsignarMonitor.setBackground(new Color(173, 255, 47));
			btnAsignarMonitor.setBounds(101, 11, 344, 35);
		}
		return btnAsignarMonitor;
	}

	private void validarMonitor() {
		vam.validarMonitor(this);
	}

	@SuppressWarnings("deprecation")
	private void rellenarInformacion() {
		String idMonitor = "Sin monitor asignado";
		String plazasDisponibles = "Plazas ilimitadas";
		if (actividadDto.getMonitor() != null) {
			idMonitor = actividadDto.getMonitor();
		}
		if (actividadDto.getPlazasTotales() > 0) {
			plazasDisponibles = Integer.toString(actividadDto.getPlazasDisponibles());
		}

		getTPInformacion()
				.setText(String.format("%s en el/la %s de %d:00 a %d:00\n Monitor: %s\n Plazas disponibles: %s",
						actividadDto.getTipo(), actividadDto.getInstalacion(), actividadDto.getFechaInicio().getHours(),
						actividadDto.getFechaFin().getHours(), idMonitor, plazasDisponibles));
	}

	private JTextPane getTPInformacion() {
		if (tPInformacion == null) {
			tPInformacion = new JTextPane();
			tPInformacion.setBorder(null);
			tPInformacion.setDisabledTextColor(Color.BLACK);
			tPInformacion.setFont(new Font("Arial Black", Font.PLAIN, 14));
			tPInformacion.setEnabled(false);
			tPInformacion.setEditable(false);
			rellenarYCentrarInformacion();
		}
		return tPInformacion;
	}

	private void rellenarYCentrarInformacion() {
		rellenarInformacion();
		StyledDocument doc = tPInformacion.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
	}

	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar actividad");
			btnCancelar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mostrarVentanaCancelarActividades();
//					cancelarActividad();
				}
			});
			btnCancelar.setForeground(Color.BLACK);
			btnCancelar.setFont(new Font("Arial", Font.BOLD, 13));
			btnCancelar.setBackground(new Color(255, 69, 0));
			btnCancelar.setBounds(273, 57, 172, 35);
		}
		return btnCancelar;
	}

	protected void mostrarVentanaCancelarActividades() {
		VentanaCancelacionActividad dialog = new VentanaCancelacionActividad(this);
		dialog.setLocationRelativeTo(this);
		dialog.setModal(true);
		dialog.setVisible(true);

	}

	protected void cancelarActividad(ActividadDto a) {
		avisarSociosMeses(a);

		vam.getVentanaAdministrador().getAdministradorCRUD().borrarActividad(actividadDto);
		vam.getVentanaAdministrador().repintarTabla();
		vam.repintaPanelesActividad();
	}

//	public void avisarSocios() {
//		if (actividadDto.getPlazasTotales() != 0
//				&& actividadDto.getPlazasDisponibles() < actividadDto.getPlazasTotales()) {
//			mostrarVentanaContacto();
//		}
//	}

//	protected void cancelarActividadesSemana() {
//		avisarSociosMeses(); //mostrar la hora/fecha de la actividad que cancelas
//	}

	public void avisarSocios(ActividadDto a) {
		if (a.getPlazasTotales() != 0 && a.getPlazasDisponibles() < a.getPlazasTotales()) {
			añadirSocios(a);
//			mostrarVentanaContactoMeses(a);
		}
	}

	private void añadirSocios(ActividadDto a) {
		if (listaSociosApuntados.size() > 0) {
			listaSociosApuntados.add("-------------------------------------------------------------");
		}
		listaSociosApuntados.add("Actividad---> " + a.getIdActividad() + "--->Hora Inicio---->"
				+ a.getFechaInicio().getHours() + ":00--->" + "Fecha final---> " + a.getFechaFin().getHours() + ":00");
		for (SocioDto socio : getVentanaAsignacionMonitores().getVentanaAdministrador().getAdministradorCRUD()
				.obtenerSociosActividad(a)) {
			listaSociosApuntados.add(String.format("%s %s - Información de contacto: %s", socio.getNombre(),
					socio.getApellido(), socio.getInfoContacto()));
		}
	}

	public void mostrarVentanaContactoMeses() {
		VentanaContacto dialog = new VentanaContacto(this);
		dialog.setLocationRelativeTo(this);
		dialog.setModal(true);
		dialog.setVisible(true);
	}

	public List<String> getListaSociosApuntados() {
		return listaSociosApuntados;
	}

	private JButton getBtnReservarPlaza() {
		if (btnReservarPlaza == null) {
			btnReservarPlaza = new JButton("Reservar plaza");
			btnReservarPlaza.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mostrarVentanaReserva();
				}
			});
			btnReservarPlaza.setForeground(Color.BLACK);
			btnReservarPlaza.setFont(new Font("Arial", Font.BOLD, 13));
			btnReservarPlaza.setBackground(new Color(255, 215, 0));
			btnReservarPlaza.setBounds(101, 57, 172, 35);
		}
		return btnReservarPlaza;
	}

	private void mostrarVentanaReserva() {
		VentanaReservaAdministrador dialog = new VentanaReservaAdministrador(this);
		dialog.setLocationRelativeTo(this);
		dialog.setModal(true);
		dialog.setVisible(true);
	}

	public void avisarSociosMeses(ActividadDto a) {
		if (a.getPlazasTotales() != 0 && a.getPlazasDisponibles() < a.getPlazasTotales()) {
			mostrarVentanaContactoMeses(a);
		}
	}

	private void mostrarVentanaContactoMeses(ActividadDto a) {
		VentanaContacto dialog = new VentanaContacto(this, a);
		dialog.setLocationRelativeTo(this);
		dialog.setModal(true);
		dialog.setVisible(true);
	}
}