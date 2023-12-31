package igu.administrador;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import persistencia.administrador.InstalacionDto;
import persistencia.administrador.RecursoDto;

public class VentanaAsignarRecursos extends JDialog {
	private static final long serialVersionUID = 1L;
	private VentanaAdministrador va;
	private JLabel lbCBInstalacion;
	private JComboBox<InstalacionDto> cBInstalacion;
	private JLabel lbTipo;
	private JTextField tFTipo;
	private JComboBox<RecursoDto> cBTipo;
	private JCheckBox chckbxTipo;
	private JButton btnAsignarRecursos;
	private JTextArea tAAvisoTipo;
	private JLabel lbCantidad;
	private JSpinner sPCantidad;

	public VentanaAsignarRecursos(VentanaAdministrador va) {
		this.va = va;
		setBounds(100, 100, 450, 450);
		getContentPane().setLayout(null);
		getContentPane().add(getLbCBInstalacion());
		getContentPane().add(getCBInstalacion());
		getContentPane().add(getLbTipo());
		getContentPane().add(getTFTipo());
		getContentPane().add(getCBTipo());
		getContentPane().add(getChckbxTipo());
		getContentPane().add(getBtnAsignarRecursos());
		getContentPane().add(getTAAvisoTipo());
		getContentPane().add(getLbCantidad());
		getContentPane().add(getSPCantidad());
	}

	private JLabel getLbCBInstalacion() {
		if (lbCBInstalacion == null) {
			lbCBInstalacion = new JLabel("Instalación:");
			lbCBInstalacion.setLabelFor(getCBInstalacion());
			lbCBInstalacion.setFont(new Font("Arial", Font.PLAIN, 12));
			lbCBInstalacion.setDisplayedMnemonic('I');
			lbCBInstalacion.setBounds(10, 26, 230, 22);
		}
		return lbCBInstalacion;
	}

	private JComboBox<InstalacionDto> getCBInstalacion() {
		if (cBInstalacion == null) {
			cBInstalacion = new JComboBox<>();
			cBInstalacion.setBounds(10, 74, 246, 22);
			cBInstalacion.setModel(new DefaultComboBoxModel<>(va.getAdministradorCRUD().listarInstalaciones()));
		}
		return cBInstalacion;
	}

	private JLabel getLbTipo() {
		if (lbTipo == null) {
			lbTipo = new JLabel("Tipo del recurso:");
			lbTipo.setFont(new Font("Arial", Font.PLAIN, 12));
			lbTipo.setDisplayedMnemonic('T');
			lbTipo.setBounds(10, 122, 175, 25);
		}
		return lbTipo;
	}

	private JTextField getTFTipo() {
		if (tFTipo == null) {
			tFTipo = new JTextField();
			tFTipo.setText("");
			tFTipo.setColumns(10);
			tFTipo.setBounds(10, 173, 246, 25);
		}
		return tFTipo;
	}

	private JComboBox<RecursoDto> getCBTipo() {
		if (cBTipo == null) {
			cBTipo = new JComboBox<>();
			cBTipo.setVisible(false);
			cBTipo.setBounds(10, 173, 230, 22);
			cBTipo.setModel(
					new DefaultComboBoxModel<>(va.getAdministradorCRUD().listarRecursos().toArray(new RecursoDto[0])));
		}
		return cBTipo;
	}

	private JCheckBox getChckbxTipo() {
		if (chckbxTipo == null) {
			chckbxTipo = new JCheckBox("Recurso existente");
			chckbxTipo.setMnemonic('R');
			chckbxTipo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					actualizarPantalla();
				}
			});
			chckbxTipo.setBounds(262, 174, 182, 23);
		}
		return chckbxTipo;
	}

	private void actualizarPantalla() {
		getTFTipo().setVisible(!getChckbxTipo().isSelected());
		getCBTipo().setVisible(getChckbxTipo().isSelected());

		getTAAvisoTipo().setText("");
		getTFTipo().setText("");
	}

	private JButton getBtnAsignarRecursos() {
		if (btnAsignarRecursos == null) {
			btnAsignarRecursos = new JButton("Asignar recursos");
			btnAsignarRecursos.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					cerrarYAsignarRecursos();
				}
			});
			btnAsignarRecursos.setMnemonic('A');
			btnAsignarRecursos.setFont(new Font("Arial", Font.BOLD, 13));
			btnAsignarRecursos.setBackground(new Color(173, 255, 47));
			btnAsignarRecursos.setBounds(262, 340, 162, 60);
		}
		return btnAsignarRecursos;
	}

	private void cerrarYAsignarRecursos() {
		if (getChckbxTipo().isSelected()) {
			va.getAdministradorCRUD().asignarRecursos(((RecursoDto) getCBTipo().getSelectedItem()).getNombre(),
					((InstalacionDto) getCBInstalacion().getSelectedItem()).getNombre(),
					(Integer) getSPCantidad().getValue());
			dispose();
		} else {
			if (getTFTipo().getText().isBlank()) {
				getTAAvisoTipo().setText("Por favor, introduzca el nombre del recurso a asignar.");
			} else {
				va.getAdministradorCRUD().asignarRecursos(getTFTipo().getText(),
						((InstalacionDto) getCBInstalacion().getSelectedItem()).getNombre(),
						(Integer) getSPCantidad().getValue());
				dispose();
			}

		}
	}

	private JTextArea getTAAvisoTipo() {
		if (tAAvisoTipo == null) {
			tAAvisoTipo = new JTextArea();
			tAAvisoTipo.setWrapStyleWord(true);
			tAAvisoTipo.setLineWrap(true);
			tAAvisoTipo.setForeground(Color.RED);
			tAAvisoTipo.setFont(new Font("Arial", Font.ITALIC, 12));
			tAAvisoTipo.setEnabled(false);
			tAAvisoTipo.setEditable(false);
			tAAvisoTipo.setDisabledTextColor(Color.RED);
			tAAvisoTipo.setBorder(null);
			tAAvisoTipo.setBounds(10, 340, 162, 60);
		}
		return tAAvisoTipo;
	}

	private JLabel getLbCantidad() {
		if (lbCantidad == null) {
			lbCantidad = new JLabel("Cantidad:");
			lbCantidad.setLabelFor(getSPCantidad());
			lbCantidad.setFont(new Font("Arial", Font.PLAIN, 12));
			lbCantidad.setDisplayedMnemonic('C');
			lbCantidad.setBounds(10, 224, 175, 25);
		}
		return lbCantidad;
	}

	@SuppressWarnings("deprecation")
	private JSpinner getSPCantidad() {
		if (sPCantidad == null) {
			sPCantidad = new JSpinner();
			sPCantidad.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
			sPCantidad.setBounds(10, 275, 162, 20);
		}
		return sPCantidad;
	}
}
