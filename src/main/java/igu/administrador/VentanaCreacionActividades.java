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
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import persistencia.administrador.InstalacionDto;
import persistencia.administrador.TipoActividadDto;

public class VentanaCreacionActividades extends JDialog {
	private static final long serialVersionUID = 1L;
	private VentanaAdministrador va;
	private Timestamp fechaInicio;
	private final JPanel contentPanel = new JPanel();
	private JButton btnCrearActividad;
	private JLabel lbCBFechaFin;
	private JComboBox<String> cBFechaFin;
	private JLabel lbCBTipo;
	private JComboBox<TipoActividadDto> cBTipo;
	private JLabel lbCBInstalacion;
	private JComboBox<InstalacionDto> cBInstalacion;
	private JLabel lbTFPlazas;
	private JCheckBox chckbxTodoElDia;
	private JCheckBox chckbxPlazasIlimitadas;
	private JLabel lbPlazas;
	private JTextArea tAAvisoPlazas;
	private JLabel lbCBFechaInicio;
	JComboBox<String> cBFechaInicio;
	private JSpinner spPlazas;
	private JCheckBox chckbxNecesitaRecursos;
	String instalacion;
	Timestamp fechaInicioActividad;
	Timestamp fechaFinActividad;

	public VentanaCreacionActividades(VentanaAdministrador va, Timestamp fechaInicio) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (confirmarAtras()) {
					dispose();
				}
			}
		});
		this.va = va;
		this.fechaInicio = fechaInicio;
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 550, 435);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getBtnCrearActividad());
		contentPanel.add(getLbCBFechaFin());
		contentPanel.add(getCBFechaFin());
		contentPanel.add(getLbCBTipo());
		contentPanel.add(getCBTipo());
		contentPanel.add(getLbCBInstalacion());
		contentPanel.add(getCBInstalacion());
		contentPanel.add(getLbTFPlazas());
		contentPanel.add(getChckbxTodoElDia());
		contentPanel.add(getChckbxPlazasIlimitadas());
		contentPanel.add(getLbPlazas());
		contentPanel.add(getTAAvisoPlazas());
		contentPanel.add(getLbCBFechaInicio());
		contentPanel.add(getCBFechaInicio());
		contentPanel.add(getSpPlazas());
		contentPanel.add(getChckbxNecesitaRecursos());
		setResizable(false);
	}

	private JLabel getLbCBFechaInicio() {
		if (lbCBFechaInicio == null) {
			lbCBFechaInicio = new JLabel("Hora inicio:");
			lbCBFechaInicio.setLabelFor(cBFechaInicio);
			lbCBFechaInicio.setFont(new Font("Arial", Font.PLAIN, 12));
			lbCBFechaInicio.setDisplayedMnemonic('H');
			lbCBFechaInicio.setBounds(10, 11, 230, 22);
		}
		return lbCBFechaInicio;
	}

	@SuppressWarnings("deprecation")
	private JComboBox<String> getCBFechaInicio() {
		if (cBFechaInicio == null) {
			cBFechaInicio = new JComboBox<>();
			cBFechaInicio.setModel(new DefaultComboBoxModel<>(generarModeloHoras()));
			cBFechaInicio.setSelectedIndex(fechaInicio.getHours() - 8);
			cBFechaInicio.setBounds(10, 50, 230, 22);
		}
		return cBFechaInicio;
	}

	private boolean confirmarAtras() {
		boolean confirmacion = false;
		int respuesta = JOptionPane.showConfirmDialog(this,
				"¿Está seguro de que quiere volver atrás? (perderá los datos introducidos)", "Seleccione una opción:",
				JOptionPane.YES_NO_OPTION);
		if (respuesta == JOptionPane.YES_OPTION) {
			confirmacion = true;
		}
		return confirmacion;
	}

	private JButton getBtnCrearActividad() {
		if (btnCrearActividad == null) {
			btnCrearActividad = new JButton("Crear actividad");
			btnCrearActividad.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (validarCampos()) {
						cerrarYCrearActividad();
					}
				}
			});
			btnCrearActividad.setMnemonic('C');
			btnCrearActividad.setFont(new Font("Arial", Font.BOLD, 13));
			btnCrearActividad.setBackground(new Color(173, 255, 47));
			btnCrearActividad.setBounds(370, 323, 155, 60);
		}
		return btnCrearActividad;
	}

	private boolean validarCampos() {
		if (!getChckbxTodoElDia().isSelected() && finMenorOIgualQueInicio()) {
			mostrarAvisoHora();
			return false;
		}

		return validarInstalacion();
	}

	private boolean finMenorOIgualQueInicio() {
		return calcularHoraParaString(getCBFechaFin().getSelectedItem().toString()) <= calcularHoraParaString(
				getCBFechaInicio().getSelectedItem().toString());
	}

	private void mostrarAvisoHora() {
		getTAAvisoPlazas().setText("La hora de inicio debe ser anterior a la hora de fin.");
	}

	private boolean validarInstalacion() {
		if (instalacionOcupada()) {
			mostrarAvisoInstalacion();
			return false;
		}

		return validarPlazas();
	}

	@SuppressWarnings("deprecation")
	private boolean instalacionOcupada() {
		instalacion = cBInstalacion.getSelectedItem().toString();

		if (getChckbxTodoElDia().isSelected()) {
			fechaInicioActividad = new Timestamp(fechaInicio.getYear(), fechaInicio.getMonth(), fechaInicio.getDate(),
					8, 0, 0, 0);
			fechaFinActividad = new Timestamp(fechaInicio.getYear(), fechaInicio.getMonth(), fechaInicio.getDate(), 23,
					0, 0, 0);
		} else {
			fechaInicioActividad = calcularFechaParaHora(getCBFechaInicio().getSelectedItem().toString());
			fechaFinActividad = calcularFechaParaHora(getCBFechaFin().getSelectedItem().toString());
		}

		return va.getAdministradorCRUD().instalacionOcupada(instalacion, fechaInicioActividad, fechaFinActividad);
	}

	private void mostrarAvisoInstalacion() {
		getTAAvisoPlazas().setText("La instalación seleccionada está ocupada en el plazo introducido.");
	}

	private boolean validarPlazas() {
		if (getChckbxNecesitaRecursos().isSelected()
				&& va.getAdministradorCRUD().obtenerCantidadRecursos(getCBTipo().getSelectedItem().toString(),
						getCBInstalacion().getSelectedItem().toString()) == 0) {
			mostrarAvisoPlazas();
			return false;
		}

		return true;
	}

	private void mostrarAvisoPlazas() {
		getTAAvisoPlazas()
				.setText("No existen recursos suficientes en la instalación para el desarrollo de la actividad.");

	}

	private void cerrarYCrearActividad() {
		int plazas = calcularPlazas();

		va.getAdministradorCRUD().añadirActividad(fechaInicioActividad, fechaFinActividad,
				cBTipo.getSelectedItem().toString(), instalacion, plazas);

		va.repintarTabla();
		dispose();
	}

	private int calcularPlazas() {
		int plazas = 0;
		if (getChckbxNecesitaRecursos().isSelected()) {
			plazas = Integer.parseInt(getLbPlazas().getText());
		} else if (!getChckbxPlazasIlimitadas().isSelected()) {
			plazas = (int) getSpPlazas().getValue();
		}

		return plazas;
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

	private JLabel getLbCBFechaFin() {
		if (lbCBFechaFin == null) {
			lbCBFechaFin = new JLabel("Hora final:");
			lbCBFechaFin.setLabelFor(getCBFechaFin());
			lbCBFechaFin.setFont(new Font("Arial", Font.PLAIN, 12));
			lbCBFechaFin.setDisplayedMnemonic('F');
			lbCBFechaFin.setBounds(10, 89, 230, 22);
		}
		return lbCBFechaFin;
	}

	@SuppressWarnings("deprecation")
	private JComboBox<String> getCBFechaFin() {
		if (cBFechaFin == null) {
			cBFechaFin = new JComboBox<>();
			cBFechaFin.setModel(new DefaultComboBoxModel<>(generarModeloHoras()));
			cBFechaFin.setSelectedIndex(fechaInicio.getHours() - 7);
			cBFechaFin.setBounds(10, 128, 230, 22);
		}
		return cBFechaFin;
	}

	private String[] generarModeloHoras() {
		List<String> horas = new ArrayList<>();
		for (int i = 8; i < 24; i++) {
			horas.add(i + ":00");
		}
		return horas.toArray(new String[0]);
	}

	private JLabel getLbCBTipo() {
		if (lbCBTipo == null) {
			lbCBTipo = new JLabel("Tipo:");
			lbCBTipo.setLabelFor(getCBTipo());
			lbCBTipo.setFont(new Font("Arial", Font.PLAIN, 12));
			lbCBTipo.setDisplayedMnemonic('O');
			lbCBTipo.setBounds(10, 167, 230, 22);
		}
		return lbCBTipo;
	}

	private JComboBox<TipoActividadDto> getCBTipo() {
		if (cBTipo == null) {
			cBTipo = new JComboBox<>();
			cBTipo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					actualizarLbPlazas();
				}
			});
			cBTipo.setBounds(10, 206, 230, 22);
			cBTipo.setModel(new DefaultComboBoxModel<>(va.getAdministradorCRUD().listarTiposActividades()));
		}
		return cBTipo;
	}

	private void actualizarLbPlazas() {
		if (getChckbxNecesitaRecursos().isSelected()) {
			deshabilitarSpinner();
			getChckbxPlazasIlimitadas().setEnabled(false);
			getLbPlazas().setText(Integer.toString(va.getAdministradorCRUD().obtenerCantidadRecursos(
					getCBTipo().getSelectedItem().toString(), getCBInstalacion().getSelectedItem().toString())));
		} else {
			getChckbxPlazasIlimitadas().setEnabled(true);
			if (getChckbxPlazasIlimitadas().isSelected()) {
				deshabilitarSpinner();
				getLbPlazas().setText("Plazas ilimitadas");
			} else {
				habilitarSpinner();
			}
		}
	}

	private void deshabilitarSpinner() {
		getSpPlazas().setVisible(false);
		getLbPlazas().setVisible(true);
	}

	private void habilitarSpinner() {
		getLbPlazas().setVisible(false);
		getSpPlazas().setVisible(true);
	}

	private JLabel getLbCBInstalacion() {
		if (lbCBInstalacion == null) {
			lbCBInstalacion = new JLabel("Instalación:");
			lbCBInstalacion.setLabelFor(getCBInstalacion());
			lbCBInstalacion.setFont(new Font("Arial", Font.PLAIN, 12));
			lbCBInstalacion.setDisplayedMnemonic('I');
			lbCBInstalacion.setBounds(10, 245, 230, 22);
		}
		return lbCBInstalacion;
	}

	private JComboBox<InstalacionDto> getCBInstalacion() {
		if (cBInstalacion == null) {
			cBInstalacion = new JComboBox<>();
			cBInstalacion.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					actualizarLbPlazas();
				}
			});
			cBInstalacion.setBounds(10, 284, 230, 22);
			cBInstalacion.setModel(new DefaultComboBoxModel<>(va.getAdministradorCRUD().listarInstalaciones()));
		}
		return cBInstalacion;
	}

	private JLabel getLbTFPlazas() {
		if (lbTFPlazas == null) {
			lbTFPlazas = new JLabel("Plazas:");
			lbTFPlazas.setLabelFor(getChckbxPlazasIlimitadas());
			lbTFPlazas.setFont(new Font("Arial", Font.PLAIN, 12));
			lbTFPlazas.setDisplayedMnemonic('P');
			lbTFPlazas.setBounds(10, 323, 230, 22);
		}
		return lbTFPlazas;
	}

	private JCheckBox getChckbxTodoElDia() {
		if (chckbxTodoElDia == null) {
			chckbxTodoElDia = new JCheckBox("Todo el día");
			chckbxTodoElDia.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					actualizarCombosFecha();
				}
			});
			chckbxTodoElDia.setMnemonic('T');
			chckbxTodoElDia.setBounds(246, 50, 115, 23);
		}
		return chckbxTodoElDia;
	}

	@SuppressWarnings("deprecation")
	private void actualizarCombosFecha() {
		if (chckbxTodoElDia.isSelected()) {
			getCBFechaInicio().setSelectedIndex(fechaInicio.getHours() - 8);
			getCBFechaInicio().setEnabled(false);
			getCBFechaFin().setSelectedIndex(fechaInicio.getHours() - 7);
			getCBFechaFin().setEnabled(false);
		} else {
			getCBFechaInicio().setEnabled(true);
			getCBFechaFin().setEnabled(true);
		}
	}

	private JCheckBox getChckbxPlazasIlimitadas() {
		if (chckbxPlazasIlimitadas == null) {
			chckbxPlazasIlimitadas = new JCheckBox("Plazas ilimitadas");
			chckbxPlazasIlimitadas.setEnabled(false);
			chckbxPlazasIlimitadas.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					actualizarLbPlazas();
				}
			});
			chckbxPlazasIlimitadas.setBounds(246, 360, 115, 23);
		}
		return chckbxPlazasIlimitadas;
	}

	private JLabel getLbPlazas() {
		if (lbPlazas == null) {
			lbPlazas = new JLabel(Integer.toString(va.getAdministradorCRUD().obtenerCantidadRecursos(
					getCBTipo().getSelectedItem().toString(), getCBInstalacion().getSelectedItem().toString())));
			lbPlazas.setBounds(10, 362, 230, 22);
		}
		return lbPlazas;
	}

	private JTextArea getTAAvisoPlazas() {
		if (tAAvisoPlazas == null) {
			tAAvisoPlazas = new JTextArea();
			tAAvisoPlazas.setWrapStyleWord(true);
			tAAvisoPlazas.setLineWrap(true);
			tAAvisoPlazas.setForeground(Color.RED);
			tAAvisoPlazas.setFont(new Font("Arial", Font.ITALIC, 12));
			tAAvisoPlazas.setEnabled(false);
			tAAvisoPlazas.setEditable(false);
			tAAvisoPlazas.setDisabledTextColor(Color.RED);
			tAAvisoPlazas.setBorder(null);
			tAAvisoPlazas.setBounds(370, 243, 155, 69);
		}
		return tAAvisoPlazas;
	}

	private JSpinner getSpPlazas() {
		if (spPlazas == null) {
			spPlazas = new JSpinner();
			spPlazas.setModel(new SpinnerNumberModel(1, 1, 99, 1));
			spPlazas.setVisible(false);
			spPlazas.setBounds(10, 362, 75, 22);
		}
		return spPlazas;
	}

	private JCheckBox getChckbxNecesitaRecursos() {
		if (chckbxNecesitaRecursos == null) {
			chckbxNecesitaRecursos = new JCheckBox("Necesita recursos");
			chckbxNecesitaRecursos.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					actualizarLbPlazas();
				}
			});
			chckbxNecesitaRecursos.setMnemonic('C');
			chckbxNecesitaRecursos.setSelected(true);
			chckbxNecesitaRecursos.setBounds(246, 323, 115, 23);
		}
		return chckbxNecesitaRecursos;
	}
}