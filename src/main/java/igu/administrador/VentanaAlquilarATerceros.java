package igu.administrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListModel;

import persistencia.administrador.ActividadDto;
import persistencia.administrador.AdministradorCRUD;
import persistencia.administrador.AlquilerDto;
import persistencia.administrador.InstalacionDto;

public class VentanaAlquilarATerceros extends JDialog {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lbNombre;
	private JTextField txNombre;
	private JLabel lbDia;
	private JComboBox<TimeStampWrapper> comboBox;
	private JList<String> listaHorasDisponibles;
	private JLabel lbInstalacion;
	private JComboBox<InstalacionDto> cbInstalacion;

	private AdministradorCRUD ac;
	// private VentanaAdministrador va;
	private JButton btAñadirHoras;
	private JList<TimeStampWrapperAdded> listaHorasAñadidas;
	private JLabel lbHoras;
	private JButton btEliminarHoras;
	private JButton btFinalizar;

	private InstalacionDto lastSelected;

	/**
	 * Create the dialog.
	 */
	public VentanaAlquilarATerceros(VentanaAdministrador ventanaAdministrador) {
		setBounds(100, 100, 691, 580);
		// this.va = ventanaAdministrador;
		this.ac = ventanaAdministrador.getAdministradorCRUD();
		getContentPane().setLayout(null);
		getContentPane().add(getLbNombre());
		getContentPane().add(getTxNombre());
		getContentPane().add(getLbDia());
		getContentPane().add(getComboBox());
		getContentPane().add(getListaHorasDisponibles());
		getContentPane().add(getLbInstalacion());
		getContentPane().add(getCbInstalacion());
		getContentPane().add(getBtAñadirHoras());
		getContentPane().add(getListaHorasAñadidas());
		getContentPane().add(getLbHoras());
		getContentPane().add(getBtEliminarHoras());
		getContentPane().add(getBtFinalizar());

		repintarLista();
	}

	private JLabel getLbNombre() {
		if (lbNombre == null) {
			lbNombre = new JLabel("Introduzca el nombre de la empresa: ");
			lbNombre.setLabelFor(getTxNombre());
			lbNombre.setBounds(21, 35, 196, 19);
		}
		return lbNombre;
	}

	private JTextField getTxNombre() {
		if (txNombre == null) {
			txNombre = new JTextField();
			txNombre.setBounds(227, 35, 96, 19);
			txNombre.setColumns(10);
		}
		return txNombre;
	}

	private JLabel getLbDia() {
		if (lbDia == null) {
			lbDia = new JLabel("Selecciona el día del alquiler: ");
			lbDia.setBounds(21, 92, 196, 19);
		}
		return lbDia;
	}

	private JComboBox<TimeStampWrapper> getComboBox() {
		if (comboBox == null) {
			comboBox = new JComboBox<>();
			comboBox.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					repintarLista();
				}
			});
			comboBox.setMaximumRowCount(7);
			comboBox.setModel(rellenarComboBox());
			comboBox.setBounds(227, 92, 96, 19);
		}
		return comboBox;
	}

	private DefaultComboBoxModel<TimeStampWrapper> rellenarComboBox() {
		TimeStampWrapper[] days = new TimeStampWrapper[7];

		TimeStampWrapper day = new TimeStampWrapper(new Timestamp(System.currentTimeMillis()));
		day = day.nextDate();
		for (int i = 0; i < 7; i++) {
			days[i] = day;
			day = day.nextDate();
		}

		DefaultComboBoxModel<TimeStampWrapper> model = new DefaultComboBoxModel<>(days);
		return model;
	}

	@SuppressWarnings("deprecation")
	private void repintarLista() {

		DefaultListModel<String> modelo = new DefaultListModel<>();

		List<Integer> hours = new ArrayList<>();

		for (int i = 8; i < 23; i++) {
			hours.add(i);
		}

		List<ActividadDto> acts = ac.listarActividadesDia((InstalacionDto) cbInstalacion.getSelectedItem(),
				((TimeStampWrapper) comboBox.getSelectedItem()).date);
		List<AlquilerDto> alqs = ac.listarAlquileresDia((InstalacionDto) cbInstalacion.getSelectedItem(),
				((TimeStampWrapper) comboBox.getSelectedItem()).date);

		Set<Integer> ocupadas = new HashSet<>();

		for (ActividadDto act : acts) {
			int ini = act.getFechaInicio().getHours();
			int fin = act.getFechaFin().getHours();

			for (int i = ini; i < fin; i++) {
				ocupadas.add(i);
			}
		}

		for (AlquilerDto alq : alqs) {
			int ini = alq.getFechaInicio().getHours();
			int fin = alq.getFechaFin().getHours();

			for (int i = ini; i < fin; i++) {
				ocupadas.add(i);
			}
		}

		for (Integer i : ocupadas) {
			hours.remove(i);
		}

		hours.forEach(i -> modelo.addElement(i + ":00"));
		getListaHorasDisponibles().setModel(modelo);

		repaint();
		validate();
	}

	private JList<String> getListaHorasDisponibles() {
		if (listaHorasDisponibles == null) {
			listaHorasDisponibles = new JList<>();
			listaHorasDisponibles.setBounds(21, 184, 302, 270);
		}
		return listaHorasDisponibles;
	}

	public class TimeStampWrapper {
		private Timestamp date;

		public TimeStampWrapper(Timestamp date) {
			this.date = date;
		}

		@SuppressWarnings("deprecation")
		public TimeStampWrapper nextDate() {
			LocalDateTime nextDate = LocalDateTime.of(date.getYear(), date.getMonth() + 1, date.getDate(),
					date.getHours(), date.getMinutes(), date.getSeconds(), date.getNanos()).plusDays(1);

			return new TimeStampWrapper(
					new Timestamp(nextDate.getYear(), nextDate.getMonthValue() - 1, nextDate.getDayOfMonth(),
							nextDate.getHour(), nextDate.getMinute(), nextDate.getSecond(), nextDate.getNano()));
		}

		@SuppressWarnings("deprecation")
		@Override
		public String toString() {
			String out = "";

			if (date.getDate() < 10) {
				out += "0" + date.getDate();
			} else {
				out += date.getDate();
			}

			out += "/";

			if (date.getMonth() < 9) {
				out += "0" + (date.getMonth() + 1);
			} else {
				out += (date.getMonth() + 1);
			}

			out += "/" + (date.getYear() + 1900);

			return out;
		}
	}

	private JLabel getLbInstalacion() {
		if (lbInstalacion == null) {
			lbInstalacion = new JLabel("Selecciona la instalación del alquiler:");
			lbInstalacion.setLabelFor(getCbInstalacion());
			lbInstalacion.setBounds(21, 144, 196, 19);

		}
		return lbInstalacion;
	}

	private JComboBox<InstalacionDto> getCbInstalacion() {
		if (cbInstalacion == null) {
			cbInstalacion = new JComboBox<>();
			cbInstalacion.setMaximumRowCount(7);
			cbInstalacion.setBounds(227, 144, 96, 19);
			cbInstalacion.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (getListaHorasAñadidas().getModel().getSize() > 0) {
						if (mostrarAviso()) {
							repintarLista();
							getListaHorasAñadidas().setModel(new DefaultListModel<>());
						} else {
							getCbInstalacion().setSelectedItem(lastSelected);
						}
					} else {
						repintarLista();
					}
					lastSelected = (InstalacionDto) cbInstalacion.getSelectedItem();
				}
			});
			cbInstalacion.setModel(new DefaultComboBoxModel<>(ac.listarInstalaciones()));
			lastSelected = (InstalacionDto) cbInstalacion.getSelectedItem();
		}
		return cbInstalacion;
	}

	private boolean mostrarAviso() {
		boolean confirmacion = false;
		int respuesta = JOptionPane.showConfirmDialog(this,
				"Si cambias de instalación se perderán las horas añadidas, ¿estás seguro de que quieres continuar?",
				"Seleccione una opción:", JOptionPane.YES_NO_OPTION);
		if (respuesta == JOptionPane.YES_OPTION) {
			confirmacion = true;
		}
		return confirmacion;
	}

	private JButton getBtAñadirHoras() {
		if (btAñadirHoras == null) {
			btAñadirHoras = new JButton("Añadir Horas");
			btAñadirHoras.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					añadirHoras();
				}
			});
			btAñadirHoras.setBounds(31, 465, 140, 36);
		}
		return btAñadirHoras;
	}

	@SuppressWarnings("deprecation")
	private void añadirHoras() {
		List<TimeStampWrapperAdded> list = new ArrayList<>();

		List<String> hours = listaHorasDisponibles.getSelectedValuesList();

		TimeStampWrapper day = (TimeStampWrapper) getComboBox().getSelectedItem();

		for (String hour : hours) {
			String[] split = hour.split(":");

			Timestamp time = new Timestamp(day.date.getYear(), day.date.getMonth(), day.date.getDate(),
					Integer.valueOf(split[0]), Integer.valueOf(split[1]), 0, 0);

			TimeStampWrapperAdded reserva = new TimeStampWrapperAdded(time);

			list.add(reserva);
		}
		ListModel<TimeStampWrapperAdded> oldModel = getListaHorasAñadidas().getModel();

		for (int i = oldModel.getSize() - 1; i >= 0; i--) {
			if (!list.contains(oldModel.getElementAt(i))) {
				list.add(0, oldModel.getElementAt(i));
			}
		}

		DefaultListModel<TimeStampWrapperAdded> newModel = new DefaultListModel<>();
		list.forEach(i -> newModel.addElement(i));

		getListaHorasAñadidas().setModel(newModel);

	}

	private JList<TimeStampWrapperAdded> getListaHorasAñadidas() {
		if (listaHorasAñadidas == null) {
			listaHorasAñadidas = new JList<>();
			listaHorasAñadidas.setBounds(363, 184, 302, 270);
		}
		return listaHorasAñadidas;
	}

	private JLabel getLbHoras() {
		if (lbHoras == null) {
			lbHoras = new JLabel("Horas añadidas:");
			lbHoras.setBounds(363, 146, 96, 19);
		}
		return lbHoras;
	}

	private JButton getBtEliminarHoras() {
		if (btEliminarHoras == null) {
			btEliminarHoras = new JButton("Eliminar Horas");
			btEliminarHoras.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					eliminarHoras();
				}
			});
			btEliminarHoras.setBounds(554, 142, 111, 23);
		}
		return btEliminarHoras;
	}

	private void eliminarHoras() {
		List<TimeStampWrapperAdded> toRemove = listaHorasAñadidas.getSelectedValuesList();
		ListModel<TimeStampWrapperAdded> oldModel = getListaHorasAñadidas().getModel();
		List<TimeStampWrapperAdded> newList = new ArrayList<>();
		DefaultListModel<TimeStampWrapperAdded> newModel = new DefaultListModel<>();

		for (int i = 0; i < oldModel.getSize(); i++) {
			if (!toRemove.contains(oldModel.getElementAt(i))) {
				newList.add(oldModel.getElementAt(i));
			}
		}

		newList.forEach(i -> newModel.addElement(i));
		listaHorasAñadidas.setModel(newModel);
	}

	private JButton getBtFinalizar() {
		if (btFinalizar == null) {
			btFinalizar = new JButton("Finalizar");
			btFinalizar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					finalizar();

				}
			});
			btFinalizar.setBounds(363, 472, 140, 36);
		}
		return btFinalizar;
	}

	private void finalizar() {
		if (getTxNombre().getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Añade el nombre de la empresa", "Datos no rellenados",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (getListaHorasAñadidas().getModel().getSize() == 0) {
			JOptionPane.showMessageDialog(this, "Añade horas al alquiler", "Datos no rellenados",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			procesarAlquiler();
		}
	}

	private void procesarAlquiler() {
		for (int i = 0; i < getListaHorasAñadidas().getModel().getSize(); i++) {
			TimeStampWrapperAdded hour = getListaHorasAñadidas().getModel().getElementAt(i);

			AlquilerDto alq = new AlquilerDto();
			alq.setEnVigor(1);
			alq.setIdAlquiler(UUID.randomUUID().toString());
			alq.setIdSocio(getTxNombre().getText());
			alq.setInstalacion(((InstalacionDto) getCbInstalacion().getSelectedItem()).getNombre());
			alq.setPagado(0);
			alq.setFechaInicio(hour.date);
			alq.setFechaFin(hour.nextHour().date);

			añadirAlquiler(alq);
		}
		JOptionPane.showMessageDialog(this, "Alquiler añadido correctamente", "Datos añadidos",
				JOptionPane.INFORMATION_MESSAGE);
		dispose();
	}

	private void añadirAlquiler(AlquilerDto alq) {
		ac.añadirAlquiler(alq);

		if (ac.buscarSocioPorId(alq.getIdSocio()) == null) {
			ac.añadirTercero(alq.getIdSocio());
		}
	}

	public class TimeStampWrapperAdded {
		private Timestamp date;

		public TimeStampWrapperAdded(Timestamp date) {
			this.date = date;
		}

		@SuppressWarnings("deprecation")
		public TimeStampWrapper nextDate() {
			LocalDateTime nextDate = LocalDateTime.of(date.getYear(), date.getMonth() + 1, date.getDate(),
					date.getHours(), date.getMinutes(), date.getSeconds(), date.getNanos()).plusDays(1);

			return new TimeStampWrapper(
					new Timestamp(nextDate.getYear(), nextDate.getMonthValue() - 1, nextDate.getDayOfMonth(),
							nextDate.getHour(), nextDate.getMinute(), nextDate.getSecond(), nextDate.getNano()));
		}

		@SuppressWarnings("deprecation")
		public TimeStampWrapper nextHour() {
			LocalDateTime nextDate = LocalDateTime.of(date.getYear(), date.getMonth() + 1, date.getDate(),
					date.getHours(), date.getMinutes(), date.getSeconds(), date.getNanos()).plusHours(1);

			return new TimeStampWrapper(
					new Timestamp(nextDate.getYear(), nextDate.getMonthValue() - 1, nextDate.getDayOfMonth(),
							nextDate.getHour(), nextDate.getMinute(), nextDate.getSecond(), nextDate.getNano()));
		}

		@SuppressWarnings("deprecation")
		@Override
		public String toString() {
			String out = "";

			if (date.getHours() < 10) {
				out += "0" + date.getHours();
			} else {
				out += date.getHours();
			}

			out += ":";

			if (date.getMinutes() < 10) {
				out += "0" + date.getMinutes();
			} else {
				out += date.getMinutes();
			}

			out += " ";

			if (date.getDate() < 10) {
				out += "0" + date.getDate();
			} else {
				out += date.getDate();
			}

			out += "/";

			if (date.getMonth() < 9) {
				out += "0" + (date.getMonth() + 1);
			} else {
				out += (date.getMonth() + 1);
			}

			out += "/" + (date.getYear() + 1900);

			return out;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Objects.hash(date);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if ((obj == null) || (getClass() != obj.getClass()))
				return false;
			TimeStampWrapperAdded other = (TimeStampWrapperAdded) obj;
			return Objects.equals(date, other.date);
		}
	}
}
