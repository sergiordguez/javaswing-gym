package igu.administrador;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import persistencia.administrador.ActividadDto;
import persistencia.administrador.AdministradorCRUD;
import persistencia.administrador.InstalacionDto;

public class VentanaCierreMantenimientoFechas extends JDialog {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblInstalacion;
	private JComboBox<InstalacionDto> cbInstalaciones;
	private AdministradorCRUD ac;
	private JLabel lblFechaInicio;
	private JTextField txtFechaInicio;
	private JLabel lblFechaFin;
	private JTextField txtFechaFin;
	private JButton btnCerrar;
	private JButton btnAnterior;
	private JLabel lblFecha;
	private JButton btnSiguiente;
	private JScrollPane scrollPane;
	private JButton btnAñadirHora;
	private JButton btnAñadirHoraFin;
	private Timestamp fecha;
	private Timestamp fechaInicio;
	private JList<String> list;
	private JButton btnEliminarInicio;
	private JButton btnEliminarFin;
	private String horaAux;
	private TimeStampWrapperAdded horaInicio;
	private TimeStampWrapperAdded horaFin;

	/**
	 * Create the frame.
	 */
	public VentanaCierreMantenimientoFechas(VentanaConfirmacionMantenimiento va) {
		setResizable(false);
		this.ac = va.getVA().getAdministradorCRUD();
		this.fecha = new Timestamp(System.currentTimeMillis());
		setTitle("Administrador: Cierre Mantenimiento");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 590, 467);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblInstalacion());
		contentPane.add(getCbInstalaciones());
		contentPane.add(getLblFechaInicio());
		contentPane.add(getTxtFechaInicio());
		contentPane.add(getLblFechaFin());
		contentPane.add(getTxtFechaFin());
		contentPane.add(getBtnCerrar());
		contentPane.add(getBtnAnterior());
		contentPane.add(getLblFecha());
		contentPane.add(getBtnSiguiente());
		contentPane.add(getScrollPane());
		contentPane.add(getBtnAñadirHora());
		contentPane.add(getBtnAñadirHoraFin());
		contentPane.add(getBtnEliminarInicio());
		contentPane.add(getBtnEliminarFin());
		repintarFecha();
		rellenarHoras();
	}

	private JLabel getLblInstalacion() {
		if (lblInstalacion == null) {
			lblInstalacion = new JLabel("Instalacion:");
			lblInstalacion.setBounds(297, 75, 92, 28);
		}
		return lblInstalacion;
	}

	private JComboBox<InstalacionDto> getCbInstalaciones() {
		if (cbInstalaciones == null) {
			cbInstalaciones = new JComboBox<>();
			cbInstalaciones.setBounds(399, 75, 148, 28);
			cbInstalaciones.setModel(new DefaultComboBoxModel<>(ac.listarInstalaciones()));
		}
		return cbInstalaciones;
	}

	private JLabel getLblFechaInicio() {
		if (lblFechaInicio == null) {
			lblFechaInicio = new JLabel("Fecha inicio cierre:");
			lblFechaInicio.setBounds(293, 129, 141, 28);
		}
		return lblFechaInicio;
	}

	private JTextField getTxtFechaInicio() {
		if (txtFechaInicio == null) {
			txtFechaInicio = new JTextField();
			txtFechaInicio.setEditable(false);
			txtFechaInicio.setBounds(293, 167, 254, 34);
			txtFechaInicio.setColumns(10);
		}
		return txtFechaInicio;
	}

	private JLabel getLblFechaFin() {
		if (lblFechaFin == null) {
			lblFechaFin = new JLabel("Fecha fin cierre:");
			lblFechaFin.setBounds(293, 227, 141, 28);
		}
		return lblFechaFin;
	}

	private JTextField getTxtFechaFin() {
		if (txtFechaFin == null) {
			txtFechaFin = new JTextField();
			txtFechaFin.setEditable(false);
			txtFechaFin.setBounds(293, 266, 254, 34);
			txtFechaFin.setColumns(10);
		}
		return txtFechaFin;
	}

	private JButton getBtnCerrar() {
		if (btnCerrar == null) {
			btnCerrar = new JButton("Cerrar Instalación");
			btnCerrar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (getTxtFechaInicio().getText().isEmpty() || getTxtFechaFin().getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Debe haber una fecha de inicio y de fin");
					} else {
						cerrarInstalacion();
						JOptionPane.showMessageDialog(null, getCbInstalaciones().getSelectedItem() + " cerrada desde "
								+ horaInicio.date.toString() + " hasta " + horaFin.date.toString());
					}
				}
			});
			btnCerrar.setBounds(420, 389, 127, 28);
		}
		return btnCerrar;
	}

	private JButton getBtnAnterior() {
		if (btnAnterior == null) {
			btnAnterior = new JButton("<");
			btnAnterior.setBounds(13, 29, 53, 23);
			if (isActualDate(fecha)) {
				btnAnterior.setEnabled(false);
			}
			btnAnterior.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					diaAnterior();
					if (fechaInicio != null) {
						if (fechaInicio.equals(fecha)) {
							repintarHoras();
						} else {
							rellenarHoras();
						}
					}
					if (isActualDate(fecha)) {
						btnAnterior.setEnabled(false);
					}
				}
			});
		}
		return btnAnterior;
	}

	private JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel("New label");
			lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblFecha.setBounds(84, 25, 120, 23);
		}
		return lblFecha;
	}

	private JButton getBtnSiguiente() {
		if (btnSiguiente == null) {
			btnSiguiente = new JButton(">");
			btnSiguiente.setBounds(214, 29, 53, 23);
			btnSiguiente.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					diaSiguiente();
					if (fechaInicio != null) {
						if (fechaInicio.equals(fecha)) {
							repintarHoras();
						} else {
							rellenarHoras();
						}
					}
					if (!isActualDate(fecha)) {
						btnAnterior.setEnabled(true);
					}
				}
			});
		}
		return btnSiguiente;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(13, 63, 254, 273);
			scrollPane.setViewportView(getList());
		}
		return scrollPane;
	}

	private JButton getBtnAñadirHora() {
		if (btnAñadirHora == null) {
			btnAñadirHora = new JButton("Añadir Hora Inicio");
			btnAñadirHora.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (list.getSelectedValue() != null) {
						horaInicio = new TimeStampWrapperAdded(toTimestamp(list.getSelectedValue()));
						txtFechaInicio.setText(horaInicio.toString());
						btnAñadirHora.setEnabled(false);
						fechaInicio = fecha;
						btnEliminarInicio.setEnabled(true);
						btnAñadirHoraFin.setEnabled(true);
						horaAux = list.getSelectedValue();
						repintarHoras();
					} else {
						horaInicio = new TimeStampWrapperAdded(toTimestamp(list.getSelectedValue()));
						txtFechaInicio.setText(horaInicio.toString());
						btnEliminarInicio.setEnabled(true);
						fechaInicio = fecha;
						horaAux = list.getSelectedValue();
						@SuppressWarnings("deprecation")
						Timestamp fechaAux = new Timestamp(3000, 12, 31, 12, 0, 0, 0);
						horaFin = new TimeStampWrapperAdded(fechaAux);
					}
				}
			});
			btnAñadirHora.setBounds(13, 347, 120, 34);
		}
		return btnAñadirHora;
	}

	private JButton getBtnAñadirHoraFin() {
		if (btnAñadirHoraFin == null) {
			btnAñadirHoraFin = new JButton("Añadir Hora Fin");
			btnAñadirHoraFin.setEnabled(false);
			btnAñadirHoraFin.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (list.getSelectedValue() != null) {
						horaFin = new TimeStampWrapperAdded(toTimestamp(list.getSelectedValue()));
						txtFechaFin.setText(horaFin.toString());
						btnAñadirHoraFin.setEnabled(false);
						btnEliminarFin.setEnabled(true);
					} else {
						JOptionPane.showMessageDialog(null, "Debes seleccionar una hora de fin");
					}
				}
			});
			btnAñadirHoraFin.setBounds(13, 386, 120, 34);
		}
		return btnAñadirHoraFin;
	}

	private JList<String> getList() {
		if (list == null) {
			list = new JList<>();
		}
		return list;
	}

	@SuppressWarnings("deprecation")
	private void repintarFecha() {
		getLblFecha().setText(fecha.toLocaleString().substring(0, 12));
	}

	private void diaAnterior() {
		fecha = new Timestamp((long) (fecha.getTime() - (8.64e+7)));
		repintarFecha();
	}

	private void diaSiguiente() {
		fecha = new Timestamp((long) (fecha.getTime() + (8.64e+7)));
		repintarFecha();
	}

	@SuppressWarnings("deprecation")
	private boolean isActualDate(Timestamp fecha) {
		Timestamp hoy = new Timestamp(System.currentTimeMillis());

		if ((fecha.getYear() != hoy.getYear()) || (fecha.getMonth() != hoy.getMonth())
				|| (fecha.getDate() != hoy.getDate())) {
			return false;
		}
		return true;
	}

	private void rellenarHoras() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		List<Integer> hours = new ArrayList<>();

		for (int i = 8; i < 23; i++) {
			hours.add(i);
		}

		hours.forEach(i -> modelo.addElement(i + ":00"));
		getList().setModel(modelo);

		repaint();
		validate();
	}

	private void repintarHoras() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		List<Integer> hours = new ArrayList<>();

		if (horaAux.length() == 4) {
			String aux1 = "";
			aux1 += horaAux.charAt(0);
			int horaInicio = Integer.parseInt(aux1);
			for (int i = horaInicio + 1; i < 23; i++) {
				hours.add(i);
			}
		} else {
			String aux = "";
			aux += horaAux.charAt(0);
			aux += horaAux.charAt(1);
			int horaInicio = Integer.parseInt(aux);
			for (int i = horaInicio + 1; i < 23; i++) {
				hours.add(i);
			}
		}

		hours.forEach(i -> modelo.addElement(i + ":00"));
		getList().setModel(modelo);

		repaint();
		validate();
	}

	private void cerrarInstalacion() {
		ActividadDto act = new ActividadDto();

		act.setIdActividad("MANTENIMIENTO");
		act.setFechaInicio(horaInicio.date);
		act.setFechaFin(horaFin.date);
		act.setInstalacion(getCbInstalaciones().getSelectedItem().toString());

		ac.añadirActividad(act);
	}

	@SuppressWarnings("deprecation")
	private Timestamp toTimestamp(String hora) {
		if (hora.length() == 4) {
			String aux1 = "";
			aux1 += hora.charAt(0);
			fecha.setHours(Integer.parseInt(aux1));
			fecha.setMinutes(0);
			fecha.setSeconds(0);
			fecha.setNanos(0);
		} else {
			String aux = "";
			aux += hora.charAt(0);
			aux += hora.charAt(1);
			fecha.setHours(Integer.parseInt(aux));
			fecha.setMinutes(0);
			fecha.setSeconds(0);
			fecha.setNanos(0);
		}

		return fecha;
	}

	private JButton getBtnEliminarInicio() {
		if (btnEliminarInicio == null) {
			btnEliminarInicio = new JButton("Cancelar inicio");
			btnEliminarInicio.setEnabled(false);
			btnEliminarInicio.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					txtFechaInicio.setText("");
					btnAñadirHora.setEnabled(true);
					btnAñadirHoraFin.setEnabled(false);
					btnEliminarInicio.setEnabled(false);
					fechaInicio = null;
					rellenarHoras();
				}
			});
			btnEliminarInicio.setBounds(143, 350, 124, 28);
		}
		return btnEliminarInicio;
	}

	private JButton getBtnEliminarFin() {
		if (btnEliminarFin == null) {
			btnEliminarFin = new JButton("Cancelar fin");
			btnEliminarFin.setEnabled(false);
			btnEliminarFin.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					txtFechaFin.setText("");
					btnAñadirHora.setEnabled(false);
					btnAñadirHoraFin.setEnabled(true);
					btnEliminarFin.setEnabled(false);
				}
			});
			btnEliminarFin.setBounds(143, 389, 124, 28);
		}
		return btnEliminarFin;
	}

	public class TimeStampWrapperAdded {
		private Timestamp date;

		public TimeStampWrapperAdded(Timestamp date) {
			this.date = date;
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

	public AdministradorCRUD getAC() {
		return this.ac;
	}
}
