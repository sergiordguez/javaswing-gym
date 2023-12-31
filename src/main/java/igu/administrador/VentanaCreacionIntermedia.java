package igu.administrador;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;

import javax.swing.JButton;
import javax.swing.JDialog;

public class VentanaCreacionIntermedia extends JDialog {
	private static final long serialVersionUID = 1L;
	private JButton btnCrearActividad;
	private JButton btnCrearAlquiler;
	private VentanaAdministrador va;
	private Timestamp fechaInicio;
	private String instalacion;

	public VentanaCreacionIntermedia(VentanaAdministrador va, Timestamp fechaInicio, String instalacion) {
		this.va = va;
		this.fechaInicio = fechaInicio;
		this.instalacion = instalacion;
		setBounds(100, 100, 360, 240);
		getContentPane().setLayout(null);
		getContentPane().add(getBtnCrearActividad());
		getContentPane().add(getBtnCrearAlquiler());
	}

	private JButton getBtnCrearActividad() {
		if (btnCrearActividad == null) {
			btnCrearActividad = new JButton("Crear actividad");
			btnCrearActividad.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mostrarVentanaCreacionActividades(fechaInicio);
				}
			});
			btnCrearActividad.setMnemonic('C');
			btnCrearActividad.setFont(new Font("Arial", Font.BOLD, 13));
			btnCrearActividad.setBackground(new Color(65, 105, 225));
			btnCrearActividad.setBounds(72, 43, 200, 35);
		}
		return btnCrearActividad;
	}

	public void mostrarVentanaCreacionActividades(Timestamp fechaInicio) {
		VentanaCreacionActividades dialog = new VentanaCreacionActividades(va, fechaInicio);
		dialog.setLocationRelativeTo(this);
		dialog.setModal(true);
		dialog.setVisible(true);
		dispose();
	}

	private JButton getBtnCrearAlquiler() {
		if (btnCrearAlquiler == null) {
			btnCrearAlquiler = new JButton("Crear alquiler");
			btnCrearAlquiler.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mostrarVentanaCreacionAlquileres(fechaInicio, instalacion);
				}
			});
			btnCrearAlquiler.setMnemonic('Q');
			btnCrearAlquiler.setFont(new Font("Arial", Font.BOLD, 13));
			btnCrearAlquiler.setBackground(new Color(65, 105, 225));
			btnCrearAlquiler.setBounds(72, 121, 200, 35);
		}
		return btnCrearAlquiler;
	}

	protected void mostrarVentanaCreacionAlquileres(Timestamp fechaInicio1, String instalacion) {
		VentanaCreacionAlquiler dialog = new VentanaCreacionAlquiler(va, fechaInicio, instalacion);
		dialog.setLocationRelativeTo(this);
		dialog.setModal(true);
		dialog.setVisible(true);
		dispose();

	}
}
