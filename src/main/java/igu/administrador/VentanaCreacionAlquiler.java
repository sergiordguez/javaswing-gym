package igu.administrador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import persistencia.administrador.InstalacionDto;

public class VentanaCreacionAlquiler extends JDialog {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private VentanaAdministrador vA;
	private Timestamp fechaInicio;
	private JLabel lblHoraInicio;
	private JComboBox<String> cbHoraIncio;
	private JLabel lblHorafinal;
	private JComboBox<String> cbHoraFinal;
	private JLabel lblInstalacion;
	private JComboBox<InstalacionDto> cbInstalacion;
	private JPanel panel;
	private JRadioButton rdbtnEfectivo;
	private JRadioButton rdbtnPagoCuota;
	private JButton btnCrearAlquiler;
	private Timestamp fechaInicioAlquiler;
	private Timestamp fechaFinAlquiler;
	private String instalacion;
	private int duracion;
	private JLabel lblDniSocio;
	private JTextField txtDNISocio;
	private JLabel lblDniAviso;
	private JButton btnAtras;
	private Timestamp fechaActual;
	private JScrollPane scrollPane;
	private JTextArea txtAreaAvisoHorario;
	private JLabel lblAvisoInstalacion;
	private String instalacionInicial;

	/**
	 * Create the dialog.
	 */
	public VentanaCreacionAlquiler(VentanaAdministrador va, Timestamp fechaInicio, String instalacionInicial) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (confirmarSalida()) {
					dispose();
				}
			}

			@Override
			public void windowOpened(WindowEvent e) {
				if (!comprobarSemana()) {
					JOptionPane.showMessageDialog(va,
							"Lo sentimos, sólo se pueden realizar alquileres desde una semana antes al momento de su uso.");
					dispose();
				} else if (comprobarDiaPasado()) {
					JOptionPane.showMessageDialog(va,
							"Lo sentimos, no se pueden realizar alquileres en fechas pasadas.");
					dispose();
				}
			}
		});
		this.vA = va;
		this.instalacionInicial = instalacionInicial;
		this.fechaActual = new Timestamp(System.currentTimeMillis());
		this.fechaInicio = fechaInicio;
		setTitle("Creacion Alquileres");
		setResizable(false);
		setBounds(100, 100, 487, 482);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getLblHoraInicio());
		contentPanel.add(getCbHoraIncio());
		contentPanel.add(getLblHorafinal());
		contentPanel.add(getCbHoraFinal());
		contentPanel.add(getLblInstalacion());
		contentPanel.add(getCbInstalacion());
		contentPanel.add(getPanel());
		contentPanel.add(getBtnCrearAlquiler());
		contentPanel.add(getLblDniSocio());
		contentPanel.add(getTxtDNISocio());
		contentPanel.add(getLblDniAviso());
		contentPanel.add(getBtnAtras());
		contentPanel.add(getScrollPane());
		contentPanel.add(getLblAvisoInstalacion());

	}

	protected boolean comprobarDiaPasado() {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		if (ts.compareTo(fechaInicio) < 0) {
			return false;
		}
		return true;
	}

	protected boolean confirmarSalida() {
		int yes = JOptionPane.showConfirmDialog(this,
				"Si cancela la creación del alquiler se perderán los datos introducidos. ¿Está seguro?",
				"Seleccione una opción:", JOptionPane.YES_NO_OPTION);
		if (yes == JOptionPane.YES_OPTION) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	protected boolean comprobarSemana() {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(ts);
		cal.add(Calendar.DAY_OF_WEEK, 7);
		ts.setTime(cal.getTime().getTime());
		ts.toString().substring(0, 8);
		if (ts.getDate() < fechaInicio.getDate()) {
			return false;
		}
		return true;
	}

	private JLabel getLblHoraInicio() {
		if (lblHoraInicio == null) {
			lblHoraInicio = new JLabel("Hora inicio:");
			lblHoraInicio.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblHoraInicio.setBounds(30, 19, 73, 27);
		}
		return lblHoraInicio;
	}

	private JComboBox<String> getCbHoraIncio() {
		if (cbHoraIncio == null) {
			cbHoraIncio = new JComboBox<>();
			cbHoraIncio.setBackground(new Color(255, 255, 255));
			cbHoraIncio.setFont(new Font("Tahoma", Font.PLAIN, 12));
			cbHoraIncio.setBounds(30, 51, 105, 21);
			cbHoraIncio.setModel(new DefaultComboBoxModel<>(generarHoras()));
			cbHoraIncio.setSelectedIndex(fechaInicio.getHours() - 8);
		}
		return cbHoraIncio;
	}

	private String[] generarHoras() {
		List<String> horas = new ArrayList<>();
		for (int i = 8; i < 24; i++) {
			horas.add(i + ":00");
		}
		return horas.toArray(new String[0]);
	}

	private JLabel getLblHorafinal() {
		if (lblHorafinal == null) {
			lblHorafinal = new JLabel("Hora final:");
			lblHorafinal.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblHorafinal.setBounds(30, 92, 78, 15);
		}
		return lblHorafinal;
	}

	private JComboBox<String> getCbHoraFinal() {
		if (cbHoraFinal == null) {
			cbHoraFinal = new JComboBox<>();
			cbHoraFinal.setFont(new Font("Tahoma", Font.PLAIN, 12));
			cbHoraFinal.setBounds(30, 119, 98, 21);
			cbHoraFinal.setModel(new DefaultComboBoxModel<>(generarHoras()));
			cbHoraFinal.setSelectedIndex(fechaInicio.getHours() - 7);
		}
		return cbHoraFinal;
	}

	private JLabel getLblInstalacion() {
		if (lblInstalacion == null) {
			lblInstalacion = new JLabel("Instalacion:");
			lblInstalacion.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblInstalacion.setBounds(30, 150, 89, 21);
		}
		return lblInstalacion;
	}

	private JComboBox<InstalacionDto> getCbInstalacion() {
		if (cbInstalacion == null) {
			cbInstalacion = new JComboBox<>();
			cbInstalacion.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					InstalacionDto i = (InstalacionDto) getCbInstalacion().getSelectedItem();
					instalacion = i.getNombre();
				}
			});
			cbInstalacion.setFont(new Font("Tahoma", Font.PLAIN, 12));
			cbInstalacion.setBounds(30, 181, 89, 21);
			cbInstalacion.setModel(new DefaultComboBoxModel<>(vA.getAdministradorCRUD().listarInstalaciones()));
			if (instalacionInicial.equals("Gimnasio")) {
				cbInstalacion.setSelectedIndex(1);
			}

		}
		return cbInstalacion;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Tipo de pago", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel.setBounds(30, 212, 324, 92);
			panel.setLayout(null);
			panel.add(getRdbtnEfectivo());
			panel.add(getRdbtnPagoCuota());
		}
		return panel;
	}

	private JRadioButton getRdbtnEfectivo() {
		if (rdbtnEfectivo == null) {
			rdbtnEfectivo = new JRadioButton("En efectivo en el momento del alquiler");
			rdbtnEfectivo.setBounds(19, 28, 276, 23);
			rdbtnEfectivo.setSelected(true);
			buttonGroup.add(rdbtnEfectivo);
			rdbtnEfectivo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
		return rdbtnEfectivo;
	}

	private JRadioButton getRdbtnPagoCuota() {
		if (rdbtnPagoCuota == null) {
			rdbtnPagoCuota = new JRadioButton("Añadir el pago a la cuota");
			rdbtnPagoCuota.setBounds(19, 53, 210, 23);
			buttonGroup.add(rdbtnPagoCuota);
			rdbtnPagoCuota.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
		return rdbtnPagoCuota;
	}

	private JButton getBtnCrearAlquiler() {
		if (btnCrearAlquiler == null) {
			btnCrearAlquiler = new JButton("Crear alquiler");
			btnCrearAlquiler.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (checkParametros()) {
						crearAlquiler();
//						sacarTicket();
					}
				}
			});
			btnCrearAlquiler.setBackground(new Color(0, 128, 0));
			btnCrearAlquiler.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btnCrearAlquiler.setBounds(319, 387, 144, 48);
		}
		return btnCrearAlquiler;
	}

	protected void sacarTicket() {
		if (getRdbtnEfectivo().isSelected()) {
			vA.getAdministradorCRUD().generarTicket(fechaInicioAlquiler, fechaFinAlquiler, instalacion, duracion);
		}
	}

	protected void crearAlquiler() {
		vA.getAdministradorCRUD().añadirAlquiler(fechaInicioAlquiler, fechaFinAlquiler, 1, tipodepago(), getIDSocio(),
				instalacion);
		vA.repintarTabla();
		dispose();
	}

	private int tipodepago() {
		if (getRdbtnEfectivo().isSelected()) {
			return 1;
		}
		return 0;
	}

	private String getIDSocio() {
		String dni = getTxtDNISocio().getText();
		String id = vA.getAdministradorCRUD().getIdSocio(dni);
		return id;
	}

	protected boolean checkParametros() {

		if (!validarCondicionesHoras()) {
			getScrollPane().setVisible(true);
			return false;
		}
		if (getTxtDNISocio().getText().isBlank() || getTxtDNISocio().getText().isEmpty() || !checkSocio()) {
			getLblDniAviso().setVisible(true);
//		}if(!comprobarDisponibilidadSocio()) {
//			JOptionPane.showMessageDialog(this, "La instalación se encuentra ocupada durante estas horas");
//			return false;
		} else if (validarInstalacion()) {
			return true;
		}
		return false;
	}

//	private boolean comprobarDisponibilidadSocio() {
//		boolean f =vA.getAdministradorCRUD().comprobarDisponibilidadSocio(getIDSocio(),fechaInicioAlquiler,fechaFinAlquiler);
//		return f;
//	}

	private boolean checkSocio() {
		if (vA.getAdministradorCRUD().existeSocioDNI(getTxtDNISocio().getText())) {
			return true;
		}
		return false;
	}

	private boolean validarCondicionesHoras() {
		if (comprobarDuracionAlquiler() && diferentesHoras() && horaFinMayorHoraInicio()) {
			return true;
		}
		return false;
	}

	private boolean validarInstalacion() {
		if (checkInstalacion()) {
			getLblInstalacion().setText("La instalacion está ocupada para el horario escogido");
			getLblInstalacion().setVisible(false);
			return false;
		}
		return true;
	}

	private boolean checkInstalacion() {
		instalacion = cbInstalacion.getSelectedItem().toString();
		fechaInicioAlquiler = calcularFechaParaHora(getCbHoraIncio().getSelectedItem().toString());
		fechaFinAlquiler = calcularFechaParaHora(getCbHoraFinal().getSelectedItem().toString());
		return vA.getAdministradorCRUD().instalacionOcupada(instalacion, fechaInicioAlquiler, fechaFinAlquiler);
	}

	@SuppressWarnings("deprecation")
	private Timestamp calcularFechaParaHora(String sHora) {
		int hora = calcularHoraParaString(sHora);
		return new Timestamp(fechaInicio.getYear(), fechaInicio.getMonth(), fechaInicio.getDate(), hora, 0, 0, 0);
	}

	private int calcularHoraParaString(String sHora) {
		int hora;
		if (sHora.length() > 4) {
			hora = Integer.parseInt(sHora.substring(0, 2));
		} else {
			hora = Integer.parseInt(sHora.substring(0, 1));
		}
		return hora;
	}

	private boolean horaFinMayorHoraInicio() {
		String inicioS = ((String) getCbHoraIncio().getSelectedItem()).split(":")[0];
		String finS = ((String) getCbHoraFinal().getSelectedItem()).split(":")[0];
		int inicio = Integer.parseInt(inicioS);
		int fin = Integer.parseInt(finS);
		if (fin > inicio) {
			return true;
		}
		return false;
	}

	private boolean diferentesHoras() {
		String inicioS = ((String) getCbHoraIncio().getSelectedItem()).split(":")[0];
		String finS = ((String) getCbHoraFinal().getSelectedItem()).split(":")[0];
		if (!inicioS.equals(finS)) {
			return true;
		}
		return false;
	}

	private boolean comprobarDuracionAlquiler() {
		String inicioS = ((String) getCbHoraIncio().getSelectedItem()).split(":")[0];
		String finS = ((String) getCbHoraFinal().getSelectedItem()).split(":")[0];
		int inicio = Integer.parseInt(inicioS);
		int fin = Integer.parseInt(finS);
		int duracion1 = fin - inicio;
		if (duracion1 <= 2 && duracion1 > 0) {
			if (duracion1 == 1) {
				this.duracion = 1;
			} else {
				this.duracion = 2;
			}
			return true;
		}
		return false;
	}

	private JLabel getLblDniSocio() {
		if (lblDniSocio == null) {
			lblDniSocio = new JLabel("Introduzca el DNI del socio:");
			lblDniSocio.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblDniSocio.setBounds(30, 314, 153, 21);
		}
		return lblDniSocio;
	}

	private JTextField getTxtDNISocio() {
		if (txtDNISocio == null) {
			txtDNISocio = new JTextField();
			txtDNISocio.setBounds(30, 348, 144, 21);
			txtDNISocio.setColumns(10);
		}
		return txtDNISocio;
	}

	private JLabel getLblDniAviso() {
		if (lblDniAviso == null) {
			lblDniAviso = new JLabel("Es necesario introducir un DNI válido");
			lblDniAviso.setForeground(new Color(128, 0, 0));
			lblDniAviso.setBackground(new Color(222, 184, 135));
			lblDniAviso.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblDniAviso.setBounds(198, 338, 265, 36);
			lblDniAviso.setVisible(false);

		}
		return lblDniAviso;
	}

	private JButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new JButton("Atrás");
			btnAtras.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (confirmarSalida()) {
						dispose();
					}
				}
			});
			btnAtras.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btnAtras.setBackground(new Color(255, 0, 0));
			btnAtras.setBounds(177, 387, 132, 48);
		}
		return btnAtras;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(177, 38, 286, 111);
			scrollPane.setViewportView(getTxtAreaAvisoHorario());
			scrollPane.setVisible(false);
		}
		return scrollPane;
	}

	private JTextArea getTxtAreaAvisoHorario() {
		if (txtAreaAvisoHorario == null) {
			txtAreaAvisoHorario = new JTextArea();
			txtAreaAvisoHorario.setBackground(new Color(245, 222, 179));
			txtAreaAvisoHorario.setLineWrap(true);
			txtAreaAvisoHorario.setWrapStyleWord(true);
			txtAreaAvisoHorario.setFont(new Font("Monospaced", Font.PLAIN, 15));
			txtAreaAvisoHorario.setForeground(new Color(128, 0, 0));
			txtAreaAvisoHorario.setEditable(false);
			txtAreaAvisoHorario.setText("El horario del alquiler tiene que tener "
					+ "una duración de 1 o 2 horas y la hora de inicio tiene que ser menor que la hora de finalización.");
//			txtAreaAvisoHorario.setVisible(false);
		}
		return txtAreaAvisoHorario;
	}

	private JLabel getLblAvisoInstalacion() {
		if (lblAvisoInstalacion == null) {
			lblAvisoInstalacion = new JLabel("");
			lblAvisoInstalacion.setBounds(138, 181, 298, 21);
			lblAvisoInstalacion.setVisible(false);
		}
		return lblAvisoInstalacion;
	}
}
