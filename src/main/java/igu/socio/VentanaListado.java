package igu.socio;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import logica.socio.TablaLogicaSocioListado;
import persistencia.administrador.ActividadDto;
import persistencia.administrador.AlquilerDto;
import persistencia.socio.SocioCRUD;

public class VentanaListado extends JDialog {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblTitulo;
	private JLabel lblActividades;
	private JScrollPane scrollPane;
	private JLabel lblInstalaciones;
	private JScrollPane scrollPane_1;
	private TablaLogicaSocioListado tablaLogica;
	private VentanaSocio vs;
	private SocioCRUD sc;
	private JList<ActividadDto> listActividades;
	private JList<AlquilerDto> listInstalaciones;

	/**
	 * Create the frame.
	 */
	public VentanaListado(VentanaSocio vs) {
		this.vs = vs;
		this.sc = vs.getSc();
		this.tablaLogica = new TablaLogicaSocioListado(sc);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 696);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblTitulo());
		contentPane.add(getLblActividades());
		contentPane.add(getScrollPane());
		contentPane.add(getLblInstalaciones());
		contentPane.add(getScrollPane_1());
		rellenarInstalacion();
		rellenarActividad();
	}

	private JLabel getLblTitulo() {
		if (lblTitulo == null) {
			lblTitulo = new JLabel("Horarios Socio");
			lblTitulo.setForeground(Color.BLUE);
			lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 22));
			lblTitulo.setBounds(138, 26, 149, 48);
		}
		return lblTitulo;
	}

	private JLabel getLblActividades() {
		if (lblActividades == null) {
			lblActividades = new JLabel("Actividades reservadas:");
			lblActividades.setBounds(22, 85, 149, 27);
		}
		return lblActividades;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(22, 123, 388, 206);
			scrollPane.setViewportView(getListActividades());
		}
		return scrollPane;
	}

	private JLabel getLblInstalaciones() {
		if (lblInstalaciones == null) {
			lblInstalaciones = new JLabel("Instalaciones alquiladas:");
			lblInstalaciones.setBounds(22, 340, 149, 27);
		}
		return lblInstalaciones;
	}

	private JScrollPane getScrollPane_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setBounds(22, 378, 388, 204);
			scrollPane_1.setViewportView(getListInstalaciones());
		}
		return scrollPane_1;
	}

	private void rellenarInstalacion() {
		DefaultListModel<AlquilerDto> modelo = new DefaultListModel<>();
		tablaLogica.generarTablaInstalaciones(vs.getVI().getSocio());
		List<AlquilerDto> instalaciones = tablaLogica.getInstalaciones();
		for (AlquilerDto instalacione : instalaciones) {
			modelo.addElement(instalacione);
		}
		getListInstalaciones().setModel(modelo);
	}

	private void rellenarActividad() {
		DefaultListModel<ActividadDto> modelo = new DefaultListModel<>();
		tablaLogica.generarTablaActividades(vs.getVI().getSocio());
		List<ActividadDto> actividades = tablaLogica.getActividades();
		for (ActividadDto actividade : actividades) {
			modelo.addElement(actividade);
		}
		getListActividades().setModel(modelo);
	}

	private JList<ActividadDto> getListActividades() {
		if (listActividades == null) {
			listActividades = new JList<>();
		}
		return listActividades;
	}

	private JList<AlquilerDto> getListInstalaciones() {
		if (listInstalaciones == null) {
			listInstalaciones = new JList<>();
		}
		return listInstalaciones;
	}

	public VentanaSocio getVS() {
		return this.vs;
	}
}
