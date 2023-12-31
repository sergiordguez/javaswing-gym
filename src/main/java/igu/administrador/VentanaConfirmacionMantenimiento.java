package igu.administrador;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class VentanaConfirmacionMantenimiento extends JDialog {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnDosFechas;
	private JButton btnIndefinida;
	private VentanaAdministrador va;

	public VentanaConfirmacionMantenimiento(VentanaAdministrador va) {
		this.va = va;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getBtnDosFechas());
		contentPane.add(getBtnIndefinida());
	}

	private JButton getBtnDosFechas() {
		if (btnDosFechas == null) {
			btnDosFechas = new JButton("Cierre dos fechas");
			btnDosFechas.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mostrarVentanaDosFechas();
				}
			});
			btnDosFechas.setBackground(new Color(65, 105, 225));
			btnDosFechas.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnDosFechas.setBounds(55, 58, 303, 54);
		}
		return btnDosFechas;
	}

	private JButton getBtnIndefinida() {
		if (btnIndefinida == null) {
			btnIndefinida = new JButton("Cierre indefinido");
			btnIndefinida.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mostrarVentanaIndefinida();
				}
			});
			btnIndefinida.setBackground(new Color(65, 105, 225));
			btnIndefinida.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnIndefinida.setBounds(55, 152, 303, 54);
		}
		return btnIndefinida;
	}

	private void mostrarVentanaDosFechas() {
		VentanaCierreMantenimientoFechas dialog = new VentanaCierreMantenimientoFechas(this);
		dialog.setLocationRelativeTo(this);
		dialog.setModal(true);
		dialog.setVisible(true);
	}

	private void mostrarVentanaIndefinida() {
		if (!mantenimiento()) {
			VentanaCierreMantenimientoIndefinido dialog = new VentanaCierreMantenimientoIndefinido(this);
			dialog.setLocationRelativeTo(this);
			dialog.setModal(true);
			dialog.setVisible(true);
		} else {
			String[] options = { "Nuevo Mantenimiento", "Añadir Fecha Fin" };
			int option = JOptionPane.showOptionDialog(null,
					"¿Quieres crear un nuevo mantenimiento o añadir una fecha de fin a uno creado?",
					"Escoge una opción", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options,
					options[0]);
			if (option == 1) {
				VentanaCierreMantenimientoExistente dialog = new VentanaCierreMantenimientoExistente(this);
				dialog.setLocationRelativeTo(this);
				dialog.setModal(true);
				dialog.setVisible(true);
			} else {
				VentanaCierreMantenimientoIndefinido dialog = new VentanaCierreMantenimientoIndefinido(this);
				dialog.setLocationRelativeTo(this);
				dialog.setModal(true);
				dialog.setVisible(true);
			}
		}
	}

	public VentanaAdministrador getVA() {
		return this.va;
	}

	private boolean mantenimiento() {
		return va.getAdministradorCRUD().listarActividades();
	}
}
