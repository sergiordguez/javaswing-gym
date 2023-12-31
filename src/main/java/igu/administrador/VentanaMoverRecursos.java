package igu.administrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import persistencia.administrador.AdministradorCRUD;
import persistencia.administrador.DisponeDto;
import persistencia.administrador.InstalacionDto;

public class VentanaMoverRecursos extends JDialog {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private AdministradorCRUD ac;
	private JLabel lbOrigen;
	private JComboBox<InstalacionDto> cbOrigen;
	private JLabel lbDestino;
	private JComboBox<InstalacionDto> cbDestino;
	private JLabel lbRecurso;
	private JComboBox<DisponeDto> cbRecursos;
	private JLabel lbCantidad;
	private JSpinner spCantidad;
	private JButton btAceptar;

	/**
	 * Create the dialog.
	 */
	public VentanaMoverRecursos(VentanaRecursosIntermedia ventanaRecursosIntermedia) {
		setBounds(100, 100, 422, 400);
		// this.va = ventanaAdministrador;
		this.ac = ventanaRecursosIntermedia.getVa().getAdministradorCRUD();
		getContentPane().setLayout(null);
		getContentPane().add(getLbOrigen());
		getContentPane().add(getCbOrigen());
		getContentPane().add(getLbDestino());
		getContentPane().add(getCbDestino());
		getContentPane().add(getLbRecurso());
		getContentPane().add(getCbRecursos());
		getContentPane().add(getLbCantidad());
		getContentPane().add(getSpCantidad());
		getContentPane().add(getBtAceptar());
		inicializar();
	}

	private void inicializar() {
		cbDestino.setModel(new DefaultComboBoxModel<>(ac.listarInstalaciones()));
		cbOrigen.setModel(new DefaultComboBoxModel<>(ac.listarInstalaciones()));
		cbRecursos.setModel(
				new DefaultComboBoxModel<>(ac.listarRecursosInstalacion((InstalacionDto) cbOrigen.getSelectedItem())));

		int max = ((DisponeDto) cbRecursos.getSelectedItem()).getCantidad();

		spCantidad.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(max),
				Integer.valueOf(1)));
	}

	private void cambiarOrigen() {
		cbRecursos.setModel(
				new DefaultComboBoxModel<>(ac.listarRecursosInstalacion((InstalacionDto) cbOrigen.getSelectedItem())));

		int max = ((DisponeDto) cbRecursos.getSelectedItem()).getCantidad();

		spCantidad.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(max),
				Integer.valueOf(1)));
	}

	private void ejecutar() {
		DisponeDto oldRecursos = (DisponeDto) cbRecursos.getSelectedItem();
		oldRecursos.setCantidad(-(Integer) getSpCantidad().getValue());
		ac.updateDispone(oldRecursos);

		DisponeDto newRecursos = new DisponeDto();
		newRecursos.setNombreInstalacion(((InstalacionDto) cbDestino.getSelectedItem()).getNombre());
		newRecursos.setNombreRecurso(oldRecursos.getNombreRecurso());
		newRecursos.setCantidad((Integer) getSpCantidad().getValue());
		ac.updateDispone(newRecursos);
		dispose();
	}

	private JLabel getLbOrigen() {
		if (lbOrigen == null) {
			lbOrigen = new JLabel("Instalación de orígen:");
			lbOrigen.setBounds(35, 45, 134, 21);
		}
		return lbOrigen;
	}

	private JComboBox<InstalacionDto> getCbOrigen() {
		if (cbOrigen == null) {
			cbOrigen = new JComboBox<>();
			cbOrigen.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					cambiarOrigen();
				}
			});
			cbOrigen.setBounds(179, 44, 134, 21);
		}
		return cbOrigen;
	}

	private JLabel getLbDestino() {
		if (lbDestino == null) {
			lbDestino = new JLabel("Instalación de destino:");
			lbDestino.setBounds(35, 98, 134, 21);
		}
		return lbDestino;
	}

	private JComboBox<InstalacionDto> getCbDestino() {
		if (cbDestino == null) {
			cbDestino = new JComboBox<>();
			cbDestino.setBounds(179, 97, 134, 21);
		}
		return cbDestino;
	}

	private JLabel getLbRecurso() {
		if (lbRecurso == null) {
			lbRecurso = new JLabel("Selecciona el recurso:");
			lbRecurso.setBounds(35, 145, 134, 21);
		}
		return lbRecurso;
	}

	private JComboBox<DisponeDto> getCbRecursos() {
		if (cbRecursos == null) {
			cbRecursos = new JComboBox<>();
			cbRecursos.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int max = ((DisponeDto) cbRecursos.getSelectedItem()).getCantidad();

					spCantidad.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0),
							Integer.valueOf(max), Integer.valueOf(1)));
				}
			});
			cbRecursos.setBounds(179, 144, 134, 21);
		}
		return cbRecursos;
	}

	private JLabel getLbCantidad() {
		if (lbCantidad == null) {
			lbCantidad = new JLabel("Selecciona la cantidad:");
			lbCantidad.setBounds(35, 197, 134, 21);
		}
		return lbCantidad;
	}

	private JSpinner getSpCantidad() {
		if (spCantidad == null) {
			spCantidad = new JSpinner();
			spCantidad
					.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
			spCantidad.setBounds(179, 197, 75, 21);
		}
		return spCantidad;
	}

	private JButton getBtAceptar() {
		if (btAceptar == null) {
			btAceptar = new JButton("Aceptar");
			btAceptar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ejecutar();
				}
			});
			btAceptar.setBounds(108, 267, 134, 33);
		}
		return btAceptar;
	}
}
