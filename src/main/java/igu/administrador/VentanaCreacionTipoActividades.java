package igu.administrador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import persistencia.administrador.RecursoDto;
import persistencia.administrador.TipoActividadDto;

public class VentanaCreacionTipoActividades extends JDialog {
	private static final long serialVersionUID = 1L;
	private VentanaAdministrador va;
	private final JPanel contentPanel = new JPanel();
	private JButton btnCrearActividad;
	private JLabel lbTFNombre;
	private JTextField tFNombre;
	private JLabel lbTARecursos;
	private JLabel lbIntensidad;
	private JComboBox<String> cBIntensidad;
	private JScrollPane scrRecursos;
	private JPanel pnRecursos;
	private JTextArea tAAvisoNombre;

	public VentanaCreacionTipoActividades(VentanaAdministrador va) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (confirmarAtras()) {
					dispose();
				}
			}
		});
		this.va = va;
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 450);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getBtnCrearActividad());
		contentPanel.add(getLbTFNombre());
		contentPanel.add(getTFNombre());
		contentPanel.add(getLbTARecursos());
		contentPanel.add(getLbIntensidad());
		contentPanel.add(getCBIntensidad());
		contentPanel.add(getScrRecursos());
		contentPanel.add(getTAAvisoNombre());
		setResizable(false);
		creaCheckBoxRecursos();
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

	private void creaCheckBoxRecursos() {
		List<RecursoDto> listaRecursos = va.getAdministradorCRUD().listarRecursos();
		for (RecursoDto dtoRecurso : listaRecursos) {
			getPnRecursos().add(new JCheckBox(dtoRecurso.toString()));
		}
	}

	private JButton getBtnCrearActividad() {
		if (btnCrearActividad == null) {
			btnCrearActividad = new JButton("Crear tipo de actividad");
			btnCrearActividad.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (validarNombre()) {
						cerrarYCrearTipo();
					}
				}
			});
			btnCrearActividad.setMnemonic('C');
			btnCrearActividad.setFont(new Font("Arial", Font.BOLD, 13));
			btnCrearActividad.setBackground(new Color(173, 255, 47));
			btnCrearActividad.setBounds(247, 337, 175, 60);
		}
		return btnCrearActividad;
	}

	private boolean validarNombre() {
		if (getTFNombre().getText().isEmpty()) {
			mostrarAvisoVacio();
			return false;
		}

		return comprobarOtrosTipos();
	}

	private void mostrarAvisoVacio() {
		getTAAvisoNombre().setText("Por favor, introduzca un nombre.");
	}

	private boolean comprobarOtrosTipos() {
		TipoActividadDto[] listaTipoActividades = va.getAdministradorCRUD().listarTiposActividades();
		String nombreActividad = getTFNombre().getText();
		for (TipoActividadDto listaTipoActividade : listaTipoActividades) {
			if (nombreActividad.equals(listaTipoActividade.getNombre())) {
				mostrarAvisoOtrosTipos();
				return false;
			}
		}

		return true;
	}

	private void mostrarAvisoOtrosTipos() {
		getTAAvisoNombre().setText("Ya existe un tipo de actividad con ese nombre. Por favor, introduzca otro.");
	}

	private void cerrarYCrearTipo() {
		List<String> listaRecursos = obtenerRecursos();

		va.getAdministradorCRUD().añadirTipoActividad(
				new TipoActividadDto(getTFNombre().getText(), getCBIntensidad().getSelectedItem().toString()),
				listaRecursos);
		dispose();
	}

	private List<String> obtenerRecursos() {
		List<String> listaRecursos = new ArrayList<>();
		for (int i = 0; i < getPnRecursos().getComponentCount(); i++) {

			JCheckBox cb = (JCheckBox) getPnRecursos().getComponent(i);
			if (cb.isSelected()) {
				listaRecursos.add(cb.getText());
			}
		}

		return listaRecursos;
	}

	private JLabel getLbTFNombre() {
		if (lbTFNombre == null) {
			lbTFNombre = new JLabel("Nombre:");
			lbTFNombre.setLabelFor(getTFNombre());
			lbTFNombre.setFont(new Font("Arial", Font.PLAIN, 12));
			lbTFNombre.setDisplayedMnemonic('N');
			lbTFNombre.setBounds(10, 19, 175, 25);
		}
		return lbTFNombre;
	}

	private JTextField getTFNombre() {
		if (tFNombre == null) {
			tFNombre = new JTextField();
			tFNombre.setBounds(10, 63, 175, 25);
			tFNombre.setColumns(10);
		}
		return tFNombre;
	}

	private JLabel getLbTARecursos() {
		if (lbTARecursos == null) {
			lbTARecursos = new JLabel("Recursos necesarios:");
			lbTARecursos.setFont(new Font("Arial", Font.PLAIN, 12));
			lbTARecursos.setBounds(10, 107, 175, 25);
		}
		return lbTARecursos;
	}

	private JLabel getLbIntensidad() {
		if (lbIntensidad == null) {
			lbIntensidad = new JLabel("Intensidad:");
			lbIntensidad.setLabelFor(getCBIntensidad());
			lbIntensidad.setFont(new Font("Arial", Font.PLAIN, 12));
			lbIntensidad.setDisplayedMnemonic('I');
			lbIntensidad.setBounds(10, 331, 175, 25);
		}
		return lbIntensidad;
	}

	private JComboBox<String> getCBIntensidad() {
		if (cBIntensidad == null) {
			cBIntensidad = new JComboBox<>();
			cBIntensidad.setModel(new DefaultComboBoxModel<>(new String[] { "Baja", "Moderada", "Alta" }));
			cBIntensidad.setBounds(10, 375, 175, 22);
		}
		return cBIntensidad;
	}

	private JScrollPane getScrRecursos() {
		if (scrRecursos == null) {
			scrRecursos = new JScrollPane();
			scrRecursos.setBorder(null);
			scrRecursos.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrRecursos.setBounds(10, 151, 175, 161);
			scrRecursos.setViewportView(getPnRecursos());
		}
		return scrRecursos;
	}

	private JPanel getPnRecursos() {
		if (pnRecursos == null) {
			pnRecursos = new JPanel();
			pnRecursos.setBorder(null);
			pnRecursos.setLayout(new GridLayout(0, 1, 0, 0));
		}
		return pnRecursos;
	}

	private JTextArea getTAAvisoNombre() {
		if (tAAvisoNombre == null) {
			tAAvisoNombre = new JTextArea();
			tAAvisoNombre.setDisabledTextColor(Color.RED);
			tAAvisoNombre.setWrapStyleWord(true);
			tAAvisoNombre.setLineWrap(true);
			tAAvisoNombre.setFont(new Font("Arial", Font.ITALIC, 12));
			tAAvisoNombre.setForeground(Color.RED);
			tAAvisoNombre.setEditable(false);
			tAAvisoNombre.setEnabled(false);
			tAAvisoNombre.setBorder(null);
			tAAvisoNombre.setBounds(247, 63, 175, 69);
		}
		return tAAvisoNombre;
	}
}
