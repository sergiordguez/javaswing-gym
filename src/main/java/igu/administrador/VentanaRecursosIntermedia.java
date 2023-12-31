package igu.administrador;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

public class VentanaRecursosIntermedia extends JDialog {
	private static final long serialVersionUID = 1L;
	private VentanaAdministrador va;
	private JButton btnAsignarRecursos;
	private JButton btnTrasladarRecursos;

	public VentanaRecursosIntermedia(VentanaAdministrador va) {
		this.va = va;
		setBounds(100, 100, 360, 240);
		getContentPane().setLayout(null);
		getContentPane().add(getBtnAsignarRecursos());
		getContentPane().add(getBtnTrasladarRecursos());
	}

	public VentanaAdministrador getVa() {
		return va;
	}

	private JButton getBtnAsignarRecursos() {
		if (btnAsignarRecursos == null) {
			btnAsignarRecursos = new JButton("Asignar recursos");
			btnAsignarRecursos.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mostrarVentanaAsignarRecursos();
				}
			});
			btnAsignarRecursos.setMnemonic('A');
			btnAsignarRecursos.setFont(new Font("Arial", Font.BOLD, 13));
			btnAsignarRecursos.setBackground(new Color(65, 105, 225));
			btnAsignarRecursos.setBounds(72, 43, 200, 35);
		}
		return btnAsignarRecursos;
	}

	private void mostrarVentanaAsignarRecursos() {
		VentanaAsignarRecursos dialog = new VentanaAsignarRecursos(va);
		dialog.setLocationRelativeTo(this);
		dialog.setModal(true);
		dialog.setVisible(true);
		dispose();
	}

	private JButton getBtnTrasladarRecursos() {
		if (btnTrasladarRecursos == null) {
			btnTrasladarRecursos = new JButton("Trasladar recursos");
			btnTrasladarRecursos.setMnemonic('T');
			btnTrasladarRecursos.setFont(new Font("Arial", Font.BOLD, 13));
			btnTrasladarRecursos.setBackground(new Color(65, 105, 225));
			btnTrasladarRecursos.setBounds(72, 121, 200, 35);
			btnTrasladarRecursos.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mostrarVentanaMoverRecursos();
				}
			});
		}
		return btnTrasladarRecursos;
	}

	protected void mostrarVentanaMoverRecursos() {
		VentanaMoverRecursos dialog = new VentanaMoverRecursos(this);
		dialog.setLocationRelativeTo(this);
		dialog.setModal(true);
		dialog.setVisible(true);
		dispose();
	}
}
