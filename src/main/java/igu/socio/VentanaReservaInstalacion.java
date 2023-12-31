package igu.socio;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import logica.socio.TablaLogicaSocioInstalacion;
import persistencia.administrador.ActividadDto;
import persistencia.administrador.AlquilerDto;
import persistencia.administrador.InstalacionDto;
import persistencia.administrador.ReservaDto;
import persistencia.socio.SocioCRUD;

public class VentanaReservaInstalacion extends JDialog {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Timestamp fecha;
	private Timestamp fechaFin;
	private JPanel contentPane;
	private JButton btnAnterior;
	private JButton btnSiguiente;
	private JLabel lblFecha;
	private JComboBox<InstalacionDto> cbInstalaciones;
	private JLabel lblInstalaciones;
	private JLabel lblDuracion;
	private JRadioButton rdbtnUnaHora;
	private JRadioButton rdbtnDosHoras;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel lblHoras;
	private JButton btnAlquilar;
	private JScrollPane scrollPane;
	private JList<String> listHoras;
	private SocioCRUD sc;
	private VentanaSocio vs;
	private TablaLogicaSocioInstalacion tablaLogica;

	/**
	 * Create the frame.
	 */
	public VentanaReservaInstalacion(VentanaSocio vs) {
		this.vs = vs;
		this.sc = vs.getSc();
		this.tablaLogica = new TablaLogicaSocioInstalacion(sc);
		this.fecha = new Timestamp(System.currentTimeMillis());
		setTitle("Gimnasio: Socio: Reserva Instalación");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 359, 603);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getBtnAnterior());
		contentPane.add(getBtnSiguiente());
		contentPane.add(getLblFecha());
		contentPane.add(getCbInstalaciones());
		contentPane.add(getLblInstalaciones());
		contentPane.add(getLblDuracion());
		contentPane.add(getRdbtnUnaHora());
		contentPane.add(getRdbtnDosHoras());
		contentPane.add(getLblHoras());
		contentPane.add(getBtnAlquilar());
		contentPane.add(getScrollPane());
		repintarFecha();
		rellenarInstalacion();
	}

	private JButton getBtnAnterior() {
		if (btnAnterior == null) {
			btnAnterior = new JButton("<");
			if (isActualDate(fecha)) {
				btnAnterior.setEnabled(false);
			}
			btnAnterior.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					diaAnterior();
					repintarLista();
					if (isActualDate(fecha)) {
						btnAnterior.setEnabled(false);
					}
				}
			});
			btnAnterior.setBounds(29, 76, 52, 23);
		}
		return btnAnterior;
	}

	private JButton getBtnSiguiente() {
		if (btnSiguiente == null) {
			btnSiguiente = new JButton(">");
			btnSiguiente.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					diaSiguiente();
					repintarLista();
					if (!isActualDate(fecha)) {
						btnAnterior.setEnabled(true);
					}
				}
			});
			btnSiguiente.setBounds(260, 76, 52, 23);
		}
		return btnSiguiente;
	}

	private JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel("");
			lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 22));
			lblFecha.setHorizontalTextPosition(SwingConstants.CENTER);
			lblFecha.setBounds(107, 72, 128, 27);
		}
		return lblFecha;
	}

	@SuppressWarnings("deprecation")
	private void repintarFecha() {
		getLblFecha().setText(fecha.toLocaleString().substring(0, 12));
	}

	private void diaAnterior() {
		fecha = new Timestamp((long) (fecha.getTime() - (8.64e+7)));
		repintarFecha();
	}

	private void diaSiguiente() {
		fecha = new Timestamp((long) (fecha.getTime() + (8.64e+7)));
		repintarFecha();
	}

	@SuppressWarnings("deprecation")
	private boolean isActualDate(Timestamp fecha) {
		Timestamp hoy = new Timestamp(System.currentTimeMillis());

		if ((fecha.getYear() != hoy.getYear()) || (fecha.getMonth() != hoy.getMonth())
				|| (fecha.getDate() != hoy.getDate())) {
			return false;
		}
		return true;
	}

	private JComboBox<InstalacionDto> getCbInstalaciones() {
		if (cbInstalaciones == null) {
			cbInstalaciones = new JComboBox<>();
			cbInstalaciones.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					repintarLista();
				}
			});
			cbInstalaciones.setBounds(29, 179, 285, 34);
		}
		return cbInstalaciones;
	}

	private void rellenarInstalacion() {
		DefaultComboBoxModel<InstalacionDto> modelo = new DefaultComboBoxModel<>();
		tablaLogica.generarTabla();
		InstalacionDto[] instalaciones = tablaLogica.getTabla();
		for (InstalacionDto instalacione : instalaciones) {
			modelo.addElement(instalacione);
		}
		getCbInstalaciones().setModel(modelo);
	}

	private JLabel getLblInstalaciones() {
		if (lblInstalaciones == null) {
			lblInstalaciones = new JLabel("Instalaciones:");
			lblInstalaciones.setBounds(29, 145, 122, 23);
		}
		return lblInstalaciones;
	}

	private JLabel getLblDuracion() {
		if (lblDuracion == null) {
			lblDuracion = new JLabel("Duración:");
			lblDuracion.setBounds(29, 236, 166, 23);
		}
		return lblDuracion;
	}

	private JRadioButton getRdbtnUnaHora() {
		if (rdbtnUnaHora == null) {
			rdbtnUnaHora = new JRadioButton("Una Hora");
			rdbtnUnaHora.addActionListener(new ActionListener() {
				@Override
				@SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent e) {
					repintarLista();
					fechaFin = fecha;
					fechaFin.setHours(fecha.getHours() + 1);
				}
			});
			rdbtnUnaHora.setBounds(29, 266, 109, 23);
			buttonGroup.add(rdbtnUnaHora);
		}
		return rdbtnUnaHora;
	}

	private JRadioButton getRdbtnDosHoras() {
		if (rdbtnDosHoras == null) {
			rdbtnDosHoras = new JRadioButton("Dos Horas");
			rdbtnDosHoras.addActionListener(new ActionListener() {
				@Override
				@SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent e) {
					repintarLista();
					fechaFin = fecha;
					fechaFin.setHours(fecha.getHours() + 2);
				}
			});
			rdbtnDosHoras.setBounds(203, 266, 109, 23);
			buttonGroup.add(rdbtnDosHoras);
		}
		return rdbtnDosHoras;
	}

	private JLabel getLblHoras() {
		if (lblHoras == null) {
			lblHoras = new JLabel("Horas:");
			lblHoras.setBounds(29, 306, 166, 23);
		}
		return lblHoras;
	}

	@SuppressWarnings("deprecation")
	private void repintarLista() {

		DefaultListModel<String> modelo = new DefaultListModel<>();

		List<Integer> hours = new ArrayList<>();

		for (int i = 8; i < 23; i++) {
			hours.add(i);
		}

		List<ActividadDto> acts = sc.listarActividadesDia((InstalacionDto) cbInstalaciones.getSelectedItem(), fecha);
		List<ReservaDto> reser = sc.listarReservas(vs.getVI().getSocio());
		List<AlquilerDto> alqs = sc.listarAlquileresDia((InstalacionDto) cbInstalaciones.getSelectedItem(), fecha);

		Set<Integer> ocupadas = new HashSet<>();

		for (ReservaDto element : reser) {
			for (int j = 0; j < acts.size(); j++) {
				if (!element.getIdActividad().equals(acts.get(j).getIdActividad())) {
					ocupadas.add(j);
				}
			}
		}

		for (ActividadDto act : acts) {
			int ini = act.getFechaInicio().getHours();
			int fin = act.getFechaFin().getHours();

			for (int i = ini; i < fin; i++) {
				ocupadas.add(i);
			}
		}

		for (AlquilerDto alq : alqs) {
			int ini = alq.getFechaInicio().getHours();
			int fin = alq.getFechaFin().getHours();

			for (int i = ini; i < fin; i++) {
				ocupadas.add(i);
			}
		}

		for (Integer i : ocupadas) {
			hours.remove(i);
		}

		hours.forEach(i -> modelo.addElement(i + ":00"));
		getListHoras().setModel(modelo);

		repaint();
		validate();
	}

	private JButton getBtnAlquilar() {
		if (btnAlquilar == null) {
			btnAlquilar = new JButton("Alquilar");
			btnAlquilar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (getListHoras() == null) {
						JOptionPane.showMessageDialog(null, "Debes seleccionar una hora para reservar");
					} else {
						procesarAlquiler();
						JOptionPane.showMessageDialog(null, "Has alquilado " + cbInstalaciones.getSelectedItem()
								+ " a las " + listHoras.getSelectedValue());
					}
				}
			});
			btnAlquilar.setBounds(223, 530, 89, 23);
		}
		return btnAlquilar;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(29, 340, 285, 175);
			scrollPane.setViewportView(getListHoras());
		}
		return scrollPane;
	}

	private JList<String> getListHoras() {
		if (listHoras == null) {
			listHoras = new JList<>();
		}
		return listHoras;
	}

	private void procesarAlquiler() {
		AlquilerDto alq = new AlquilerDto();
		alq.setEnVigor(1);
		alq.setIdAlquiler(getCbInstalaciones().getSelectedItem().toString());
		alq.setIdSocio(vs.getVI().getSocio().getIdSocio());
		alq.setInstalacion(((InstalacionDto) getCbInstalaciones().getSelectedItem()).getNombre());
		alq.setPagado(0);
		alq.setFechaInicio(fecha);
		alq.setFechaFin(fechaFin);

		sc.añadirAlquiler(alq);
	}
}
