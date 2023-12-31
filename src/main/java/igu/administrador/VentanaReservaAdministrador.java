package igu.administrador;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import persistencia.administrador.AdministradorCRUD;

public class VentanaReservaAdministrador extends JDialog {
	private static final long serialVersionUID = 1L;
	private PanelActividad panelActividad;
	private JLabel lbTFModo;
	private JTextField tFModo;
	private JButton btnReserva;
	private JTextArea tAAviso;
	private JLabel lbComo;
	private JComboBox<String> cBModo;
	private String avisoVacio;
	private String avisoExisteSocio;

	public VentanaReservaAdministrador(PanelActividad panelActividad) {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (confirmarAtras()) {
					dispose();
				}
			}
		});
		this.panelActividad = panelActividad;
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 600, 250);
		getContentPane().setLayout(null);
		getContentPane().add(getLbTFModo());
		getContentPane().add(getTFModo());
		getContentPane().add(getBtnReserva());
		getContentPane().add(getTAAviso());
		getContentPane().add(getLbComo());
		getContentPane().add(getCBModo());
		actualizarModo();
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

	private JLabel getLbTFModo() {
		if (lbTFModo == null) {
			lbTFModo = new JLabel("Número de socio");
			lbTFModo.setLabelFor(getTFModo());
			lbTFModo.setFont(new Font("Arial", Font.PLAIN, 12));
			lbTFModo.setDisplayedMnemonic('N');
			lbTFModo.setBounds(10, 65, 175, 25);
		}
		return lbTFModo;
	}

	private JTextField getTFModo() {
		if (tFModo == null) {
			tFModo = new JTextField();
			tFModo.setColumns(10);
			tFModo.setBounds(10, 105, 175, 25);
		}
		return tFModo;
	}

	private JButton getBtnReserva() {
		if (btnReserva == null) {
			btnReserva = new JButton("Reservar plaza");
			btnReserva.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String modo = getCBModo().getSelectedItem().toString();

					if (validarCampos(modo)) {
						cerrarYReservarPlaza(modo);
					}
				}
			});
			btnReserva.setMnemonic('R');
			btnReserva.setFont(new Font("Arial", Font.BOLD, 13));
			btnReserva.setBackground(new Color(173, 255, 47));
			btnReserva.setBounds(360, 135, 205, 60);
		}
		return btnReserva;
	}

	private boolean validarCampos(String modo) {
		if (getTFModo().getText().isBlank()) {
			mostrarAvisoVacio();
			return false;
		}

		return validarExisteSocio(modo);
	}

	private void mostrarAvisoVacio() {
		getTAAviso().setText(avisoVacio);
	}

	private AdministradorCRUD getAdministradorCRUD() {
		return panelActividad.getVentanaAsignacionMonitores().getVentanaAdministrador().getAdministradorCRUD();
	}

	private boolean validarExisteSocio(String modo) {
		if ((modo.equals("Número de socio") && !getAdministradorCRUD().existeSocioNumero(getTFModo().getText()))
				|| (modo.equals("DNI") && !getAdministradorCRUD().existeSocioDNI(getTFModo().getText()))
				|| (modo.equals("Información de contacto")
						&& !getAdministradorCRUD().existeSocioInfo(getTFModo().getText()))) {
			mostrarAvisoExisteSocio();
			return false;
		}

		return validarExisteReserva(modo);
	}

	private void mostrarAvisoExisteSocio() {
		getTAAviso().setText(avisoExisteSocio);
	}

	private boolean validarExisteReserva(String modo) {
		if ((modo.equals("Número de socio")
				&& getAdministradorCRUD().existeReservaNumero(getTFModo().getText(), panelActividad.getActividadDto()))
				|| (modo.equals("DNI") && getAdministradorCRUD().existeReservaDNI(getTFModo().getText(),
						panelActividad.getActividadDto()))
				|| (modo.equals("Información de contacto") && getAdministradorCRUD()
						.existeReservaInfo(getTFModo().getText(), panelActividad.getActividadDto()))) {
			mostrarAvisoExisteReserva();
			return false;
		}

		return true;
	}

	private void mostrarAvisoExisteReserva() {
		getTAAviso().setText("Dicho socio ya ha reservado plaza en la actividad");
	}

	private void cerrarYReservarPlaza(String modo) {
		if (modo.equals("Número de socio")) {
			getAdministradorCRUD().reservarPlazaNumero(getTFModo().getText(), panelActividad.getActividadDto());
		} else if (modo.equals("DNI")) {
			getAdministradorCRUD().reservarPlazaDNI(getTFModo().getText(), panelActividad.getActividadDto());
		} else {
			getAdministradorCRUD().reservarPlazaInfo(getTFModo().getText(), panelActividad.getActividadDto());
		}

		panelActividad.getVentanaAsignacionMonitores().repintaPanelesActividad();
		dispose();
	}

	private JTextArea getTAAviso() {
		if (tAAviso == null) {
			tAAviso = new JTextArea();
			tAAviso.setWrapStyleWord(true);
			tAAviso.setLineWrap(true);
			tAAviso.setForeground(Color.RED);
			tAAviso.setFont(new Font("Arial", Font.ITALIC, 12));
			tAAviso.setEnabled(false);
			tAAviso.setEditable(false);
			tAAviso.setDisabledTextColor(Color.RED);
			tAAviso.setBorder(null);
			tAAviso.setBounds(10, 145, 175, 50);
		}
		return tAAviso;
	}

	private JLabel getLbComo() {
		if (lbComo == null) {
			lbComo = new JLabel("Seleccione cómo desea indentificar al socio:");
			lbComo.setHorizontalAlignment(SwingConstants.LEFT);
			lbComo.setFont(new Font("Arial Black", Font.PLAIN, 14));
			lbComo.setDisplayedMnemonic('P');
			lbComo.setBounds(10, 15, 350, 35);
		}
		return lbComo;
	}

	private JComboBox<String> getCBModo() {
		if (cBModo == null) {
			cBModo = new JComboBox<>();
			cBModo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					actualizarModo();
				}
			});
			cBModo.setModel(
					new DefaultComboBoxModel<>(new String[] { "Número de socio", "DNI", "Información de contacto" }));
			cBModo.setBounds(370, 23, 195, 22);
		}
		return cBModo;
	}

	private void actualizarModo() {
		String modo = getCBModo().getSelectedItem().toString();
		if (modo.equals("Número de socio")) {
			getLbTFModo().setText("Número de socio");
			getLbTFModo().setDisplayedMnemonic('N');
			this.avisoVacio = "Por favor, introduzca un número de socio";
			this.avisoExisteSocio = "No existe un socio con dicho número";
		} else if (modo.equals("DNI")) {
			getLbTFModo().setText("DNI");
			getLbTFModo().setDisplayedMnemonic('D');
			this.avisoVacio = "Por favor, introduzca un DNI";
			this.avisoExisteSocio = "No existe un socio con dicho DNI";
		} else {
			getLbTFModo().setText("Información de contacto");
			getLbTFModo().setDisplayedMnemonic('I');
			this.avisoVacio = "Por favor, introduzca información de contacto";
			this.avisoExisteSocio = "No existe un socio con dicha información de contacto";
		}

		getTFModo().setText("");
		getTAAviso().setText("");
		getTFModo().grabFocus();
	}
}
