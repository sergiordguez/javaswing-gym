package igu.administrador;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import persistencia.administrador.AdministradorCRUD;
import persistencia.administrador.AlquilerDto;
import persistencia.administrador.InstalacionDto;
import persistencia.socio.SocioDto;

public class VentanaAnulacionAlquiler extends JDialog {

	private static final long serialVersionUID = 1L;
	private JLabel lblAlquileres;
	private JList<AlquilerDto> list;
	private JButton btnCancelar;
	private JComboBox<InstalacionDto> cbInstalacion;
	private AdministradorCRUD ac;
	private VentanaAdministrador conect;

	public VentanaAnulacionAlquiler(VentanaAdministrador va) {
		ac = va.getAdministradorCRUD();
		conect = va;
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (confirmarAtras()) {
					dispose();
				}
			}
		});
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("Gimnasio: Socio: Actividades");
		setBounds(100, 100, 662, 529);
		getContentPane().setLayout(null);
		getContentPane().add(getLblAlquileres());
		getContentPane().add(getList());
		getContentPane().add(getBtnCancelar());
		getContentPane().add(getCbInstalacion());

		repintarLista();
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

	private JLabel getLblAlquileres() {
		if (lblAlquileres == null) {
			lblAlquileres = new JLabel("Alquileres");
			lblAlquileres.setForeground(Color.BLUE);
			lblAlquileres.setFont(new Font("Tahoma", Font.PLAIN, 25));
			lblAlquileres.setBounds(117, 24, 152, 37);
		}
		return lblAlquileres;
	}

	private JList<AlquilerDto> getList() {
		if (list == null) {
			list = new JList<>();
			list.setBounds(39, 72, 303, 372);
		}
		return list;
	}

	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar Alquiler");
			btnCancelar.setMnemonic('C');
			btnCancelar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (list.getSelectedValue() == null) {
						JOptionPane.showMessageDialog(null, "Debes selecciona un alquiler para cancelar la reserva");
					} else {
						AlquilerDto alq = list.getSelectedValue();
						SocioDto soc = ac.buscarSocioPorId(alq.getIdSocio());
						ac.cancelarAlquiler(alq);
						JOptionPane.showMessageDialog(null, String.format(
								"Has cancelado el alquiler. Se debe poner avisar al socio\n%s %s - Información de contacto: %s",
								soc.getNombre(), soc.getApellido(), soc.getInfoContacto()));
						mostrarVentanaAdministrador();
					}
				}
			});
			btnCancelar.setBounds(411, 415, 127, 29);
		}
		return btnCancelar;
	}

	private void mostrarVentanaAdministrador() {
		conect.setLocationRelativeTo(this);
		conect.setVisible(true);
		this.dispose();
	}

	private JComboBox<InstalacionDto> getCbInstalacion() {
		if (cbInstalacion == null) {
			cbInstalacion = new JComboBox<>();
			cbInstalacion.setBounds(411, 37, 127, 24);
			cbInstalacion.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					repintarLista();
				}
			});
			cbInstalacion.setModel(new DefaultComboBoxModel<>(ac.listarInstalaciones()));
		}
		return cbInstalacion;
	}

	public void repintarLista() {

		DefaultListModel<AlquilerDto> modelo = new DefaultListModel<>();
		InstalacionDto ins = (InstalacionDto) getCbInstalacion().getSelectedItem();
		List<AlquilerDto> alquileres = ac.listarAlquileres(ins.getNombre());
		alquileres.forEach(i -> modelo.addElement(i));
		getList().setModel(modelo);

		repaint();
		validate();
	}
}
