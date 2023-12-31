package igu.administrador;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import igu.VentanaInicial;
import logica.administrador.TablaLogica;
import logica.administrador.TablaLogicaAlquileres;
import persistencia.administrador.ActividadDto;
import persistencia.administrador.AdministradorCRUD;
import persistencia.administrador.AlquilerDto;
import persistencia.administrador.InstalacionDto;
import util.Database;

public class VentanaAdministrador extends JFrame {
	private static final long serialVersionUID = 1L;
	private Database db;
	private AdministradorCRUD ac;
	private TablaLogica tablaLogica;
	private TablaLogicaAlquileres tablaLogicaAlquileres;
	private AccionarBotonActividad aBA;
	private AccionarBotonVacio aBV;
	private Timestamp semana;
	private JPanel contentPane;
	private JPanel pnContenidos;
	private JPanel pn1;
	private JButton btnPlanificarActividad;
	private JButton btnCrearTipo;
	private JPanel pn2;
	private JScrollPane sPTabla;
	private JPanel pnTabla;
	private JComboBox<InstalacionDto> cBInstalacion;
	private JLabel lbHoy;
	private JButton btnIzquierda;
	private JButton btnDerecha;
	private JButton btnAtras;
	private JLabel lbCBInstalacion;
	private JPanel pnLeyendaAlquileres;
	private JPanel pnLeyendaActividadesIlimitadas;
	private JPanel pnLeyendaActividadesLimitidas;
	private JLabel lbLeyendaAlquileres;
	private JLabel lbLeyendaActividadesIlimitadas;
	private JLabel lbLeyendaActividadesLimitadas;
	private JButton btnContabilidad;
	private JButton btnAnularAlquileres;
	private JButton btnAlquilarTerceros;
	private JButton btnPlanificarActividadVariosDias;
	private JButton btnAsignarTrasladarRecursos;
	private JButton btnCierreMantenimiento;

	public VentanaAdministrador(VentanaInicial vi) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mostrarVentanaInicial(db);
			}
		});
		this.db = vi.getDatabase();
		this.ac = new AdministradorCRUD(vi.getDatabase());
		this.tablaLogica = new TablaLogica(ac);
		this.tablaLogicaAlquileres = new TablaLogicaAlquileres(ac);
		this.aBA = new AccionarBotonActividad();
		this.aBV = new AccionarBotonVacio();
		this.semana = new Timestamp(System.currentTimeMillis());
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPnContenidos(), BorderLayout.CENTER);
		setResizable(false);
		mostrarPn1();
	}

	private void mostrarVentanaInicial(Database db) {
		VentanaInicial vi = new VentanaInicial(db);
		vi.setLocationRelativeTo(this);
		vi.setVisible(true);
		dispose();
	}

	public AdministradorCRUD getAdministradorCRUD() {
		return ac;
	}

	private void mostrarPn1() {
		setSize(480, 540);
		// setSize(1240, 685);
		((CardLayout) getPnContenidos().getLayout()).show(pnContenidos, "pn1");
	}

	protected void mostrarPn2() {
		repintarTabla();
		setSize(1240, 685);
		((CardLayout) getPnContenidos().getLayout()).show(pnContenidos, "pn2");
	}

	private JPanel getPnContenidos() {
		if (pnContenidos == null) {
			pnContenidos = new JPanel();
			pnContenidos.setLayout(new CardLayout(0, 0));
			pnContenidos.add(getPn1(), "pn1");
			pnContenidos.add(getPn2(), "pn2");
		}
		return pnContenidos;
	}

	private JPanel getPn1() {
		if (pn1 == null) {
			pn1 = new JPanel();
			pn1.setLayout(null);
			pn1.add(getBtnPlanificarActividad());
			pn1.add(getBtnCrearTipo());
			pn1.add(getBtnContabilidad());
			pn1.add(getBtnAnularAlquileres());
			pn1.add(getBtnAlquilarTerceros());
			pn1.add(getBtnPlanificarActividadVariosDias());
			pn1.add(getBtnAsignarTrasladarRecursos());
			pn1.add(getBtnCierreMantenimiento());
		}
		return pn1;
	}

	private JPanel getPn2() {
		if (pn2 == null) {
			pn2 = new JPanel();
			pn2.setBorder(null);
			pn2.setLayout(null);
			pn2.add(getSPTabla());
			pn2.add(getCBInstalacion());
			pn2.add(getLbHoy());
			pn2.add(getBtnIzquierda());
			pn2.add(getBtnDerecha());
			pn2.add(getBtnAtras());
			pn2.add(getLbCBInstalacion());
			pn2.add(getPnLeyendaAlquileres());
			pn2.add(getPnLeyendaActividadesIlimitadas());
			pn2.add(getPnLeyendaActividadesLimitidas());
			pn2.add(getLbLeyendaAlquileres());
			pn2.add(getLbLeyendaActividadesIlimitadas());
			pn2.add(getLbLeyendaActividadesLimitadas());
		}
		return pn2;
	}

	private JButton getBtnCrearTipo() {
		if (btnCrearTipo == null) {
			btnCrearTipo = new JButton("Crear un tipo de actividad");
			btnCrearTipo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mostrarVentanaCreacionTipoActividades();
				}
			});
			btnCrearTipo.setMnemonic('C');
			btnCrearTipo.setFont(new Font("Arial", Font.BOLD, 13));
			btnCrearTipo.setBackground(new Color(65, 105, 225));
			btnCrearTipo.setBounds(78, 23, 300, 35);
		}
		return btnCrearTipo;
	}

	private void mostrarVentanaCreacionTipoActividades() {
		VentanaCreacionTipoActividades dialog = new VentanaCreacionTipoActividades(this);
		dialog.setLocationRelativeTo(this);
		dialog.setModal(true);
		dialog.setVisible(true);
	}

	private JButton getBtnPlanificarActividad() {
		if (btnPlanificarActividad == null) {
			btnPlanificarActividad = new JButton("Planificar actividades / alquileres");
			btnPlanificarActividad.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mostrarPn2();
				}
			});
			btnPlanificarActividad.setMnemonic('P');
			btnPlanificarActividad.setFont(new Font("Arial", Font.BOLD, 13));
			btnPlanificarActividad.setBackground(new Color(65, 105, 225));
			btnPlanificarActividad.setBounds(78, 81, 300, 35);
		}
		return btnPlanificarActividad;
	}

	private JScrollPane getSPTabla() {
		if (sPTabla == null) {
			sPTabla = new JScrollPane();
			sPTabla.setBorder(null);
			sPTabla.setEnabled(false);
			sPTabla.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			sPTabla.setBounds(62, 63, 1100, 500);
			sPTabla.setViewportView(getPnTabla());
		}
		return sPTabla;
	}

	private JPanel getPnTabla() {
		if (pnTabla == null) {
			pnTabla = new JPanel();
			pnTabla.setBorder(null);
			pnTabla.setLayout(new GridLayout(17, 8, 5, 5));
		}
		return pnTabla;
	}

	private void creaBotonesTabla() {
		pnTabla.removeAll();
		creaColumnasTabla();
		creaFilasTabla();
	}

	private void creaColumnasTabla() {
		pnTabla.add(new JLabel(""));
		JLabel lbLunes = new JLabel("Lunes");
		lbLunes.setHorizontalAlignment(SwingConstants.CENTER);
		pnTabla.add(lbLunes);
		JLabel lbMartes = new JLabel("Martes");
		lbMartes.setHorizontalAlignment(SwingConstants.CENTER);
		pnTabla.add(lbMartes);
		JLabel lbMiercoles = new JLabel("Miércoles");
		lbMiercoles.setHorizontalAlignment(SwingConstants.CENTER);
		pnTabla.add(lbMiercoles);
		JLabel lbJueves = new JLabel("Jueves");
		lbJueves.setHorizontalAlignment(SwingConstants.CENTER);
		pnTabla.add(lbJueves);
		JLabel lbViernes = new JLabel("Viernes");
		lbViernes.setHorizontalAlignment(SwingConstants.CENTER);
		pnTabla.add(lbViernes);
		JLabel lbSabado = new JLabel("Sábado");
		lbSabado.setHorizontalAlignment(SwingConstants.CENTER);
		pnTabla.add(lbSabado);
		JLabel lbDomingo = new JLabel("Domingo");
		lbDomingo.setHorizontalAlignment(SwingConstants.CENTER);
		pnTabla.add(lbDomingo);
	}

	private void creaFilasTabla() {
		int contadorHora = 8;
		for (int i = 8; i < TablaLogica.DIM; i++) {
			if (i % 8 == 0) {
				JLabel hora = new JLabel(contadorHora++ + ":00");
				hora.setHorizontalAlignment(SwingConstants.TRAILING);
				pnTabla.add(hora);
			} else {
				pnTabla.add(nuevoBoton(i));
			}
		}
	}

	private JButton nuevoBoton(Integer posicion) {
		List<ActividadDto> actividadesEnCelda = tablaLogica.obtenerListaActividades(posicion);
		List<AlquilerDto> alquileresEnCelda = tablaLogicaAlquileres.obtenerListaAlquileres(posicion);

		String sActividadEnCelda = "Crear actividad/alquiler";
		if (!actividadesEnCelda.isEmpty()) {
			sActividadEnCelda = actividadesEnCelda.get(0).getTipo();
		} else if (!alquileresEnCelda.isEmpty() && alquileresEnCelda.get(0).getEnVigor() != 0) {
			sActividadEnCelda = alquileresEnCelda.get(0).getInstalacion();
		}

		JButton boton = new JButton(sActividadEnCelda);
		boton.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
		boton.setActionCommand(posicion.toString());

		if (!actividadesEnCelda.isEmpty()) {
			if (actividadesEnCelda.get(0).getPlazasTotales() == 0) {
				boton.setBackground(new Color(173, 255, 47));
			} else {
				boton.setBackground(new Color(255, 215, 0));
			}

			boton.addActionListener(aBA);
		} else if (!alquileresEnCelda.isEmpty() && alquileresEnCelda.get(0).getEnVigor() != 0) {
			boton.setBackground(new Color(65, 105, 225));
		} else {
			boton.setBackground(Color.white);
			boton.addActionListener(aBV);
		}

		return boton;
	}

	class AccionarBotonActividad implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton bt = (JButton) e.getSource();
			int indice = Integer.parseInt(bt.getActionCommand());
			Timestamp fechaInicio = obtenerFechaParaIndice(indice);
			mostrarVentanaAsignacionMonitores(fechaInicio, indice);
		}
	}

	@SuppressWarnings("deprecation")
	private Timestamp obtenerFechaParaIndice(int indice) {
		int columna = indice % 8;
		int fila = (indice - columna) / 8;
		int hora = (fila - 1) * 1 + 8;
		int dia = semana.getDate() - (convertirDia(semana.getDay()) - columna);

		return new Timestamp(semana.getYear(), semana.getMonth(), dia, hora, 0, 0, 0);
	}

	private int convertirDia(int dia) {
		if (dia != 0) {
			return dia;
		} else {
			return 7;
		}
	}

	private void mostrarVentanaAsignacionMonitores(Timestamp fechaInicio, int indice) {
		VentanaAsignacionMonitores dialog = new VentanaAsignacionMonitores(this, fechaInicio, indice);
		dialog.setLocationRelativeTo(this);
		dialog.setModal(true);
		dialog.setVisible(true);
	}

	class AccionarBotonVacio implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton bt = (JButton) e.getSource();
			int indice = Integer.parseInt(bt.getActionCommand());
			Timestamp fechaInicio = obtenerFechaParaIndice(indice);
			mostrarVentanaCreacionIntermedia(fechaInicio);
		}
	}

	public void mostrarVentanaCreacionIntermedia(Timestamp fechaInicio) {
		String instalacionS = getCBInstalacion().getSelectedItem().toString();
		VentanaCreacionIntermedia dialog = new VentanaCreacionIntermedia(this, fechaInicio, instalacionS);
		dialog.setLocationRelativeTo(this);
		dialog.setModal(true);
		dialog.setVisible(true);
	}

	public void mostrarVentanaCreacionActividades(Timestamp fechaInicio) {
		VentanaCreacionActividades dialog = new VentanaCreacionActividades(this, fechaInicio);
		dialog.setLocationRelativeTo(this);
		dialog.setModal(true);
		dialog.setVisible(true);
	}

	private JComboBox<InstalacionDto> getCBInstalacion() {
		if (cBInstalacion == null) {
			cBInstalacion = new JComboBox<>();
			cBInstalacion.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					repintarTabla();
				}
			});
			cBInstalacion.setForeground(Color.DARK_GRAY);
			cBInstalacion.setFont(new Font("Arial", Font.BOLD, 13));
			cBInstalacion.setBounds(997, 26, 165, 35);
			cBInstalacion.setModel(new DefaultComboBoxModel<>(ac.listarInstalaciones()));
		}
		return cBInstalacion;
	}

	public void repintarTabla() {
		tablaLogica.generarTabla(semana, getCBInstalacion().getSelectedItem().toString());
		tablaLogicaAlquileres.generarTabla(semana, getCBInstalacion().getSelectedItem().toString());
		creaBotonesTabla();
		repaint();
		validate();
	}

	private JLabel getLbHoy() {
		if (lbHoy == null) {
			lbHoy = new JLabel((String) null);
			lbHoy.setHorizontalAlignment(SwingConstants.CENTER);
			lbHoy.setFont(new Font("Arial Black", Font.PLAIN, 18));
			lbHoy.setBounds(512, 11, 200, 50);
			repintarFecha();
		}
		return lbHoy;
	}

	private JButton getBtnIzquierda() {
		if (btnIzquierda == null) {
			btnIzquierda = new JButton("<");
			btnIzquierda.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					semanaAnterior();
					repintarTabla();
				}
			});
			btnIzquierda.setFocusPainted(false);
			btnIzquierda.setContentAreaFilled(false);
			btnIzquierda.setBorderPainted(false);
			btnIzquierda.setMnemonic('P');
			btnIzquierda.setFont(new Font("Arial", Font.BOLD, 13));
			btnIzquierda.setBackground(new Color(65, 105, 225));
			btnIzquierda.setBounds(452, 11, 50, 50);
		}
		return btnIzquierda;
	}

	private void semanaAnterior() {
		semana = new Timestamp((long) (semana.getTime() - (6.048e+8)));
		repintarFecha();
	}

	private void repintarFecha() {
		getLbHoy().setText(new SimpleDateFormat("dd/MM/yyyy").format(semana));
	}

	private JButton getBtnDerecha() {
		if (btnDerecha == null) {
			btnDerecha = new JButton(">");
			btnDerecha.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					semanaSiguiente();
					repintarTabla();
				}
			});
			btnDerecha.setMnemonic('P');
			btnDerecha.setFont(new Font("Arial", Font.BOLD, 13));
			btnDerecha.setFocusPainted(false);
			btnDerecha.setContentAreaFilled(false);
			btnDerecha.setBorderPainted(false);
			btnDerecha.setBackground(new Color(65, 105, 225));
			btnDerecha.setBounds(722, 11, 50, 50);
		}
		return btnDerecha;
	}

	private void semanaSiguiente() {
		semana = new Timestamp((long) (semana.getTime() + (6.048e+8)));
		repintarFecha();
	}

	private JButton getBtnAtras() {
		if (btnAtras == null) {
			btnAtras = new JButton("Atrás");
			btnAtras.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mostrarPn1();
				}
			});
			btnAtras.setMnemonic('A');
			btnAtras.setForeground(Color.BLACK);
			btnAtras.setFont(new Font("Arial", Font.BOLD, 13));
			btnAtras.setBackground(new Color(255, 69, 0));
			btnAtras.setBounds(997, 587, 165, 35);
		}
		return btnAtras;
	}

	private JLabel getLbCBInstalacion() {
		if (lbCBInstalacion == null) {
			lbCBInstalacion = new JLabel("Instalación:");
			lbCBInstalacion.setLabelFor(getCBInstalacion());
			lbCBInstalacion.setFont(new Font("Arial", Font.PLAIN, 12));
			lbCBInstalacion.setDisplayedMnemonic('I');
			lbCBInstalacion.setBounds(1000, 5, 230, 22);
		}
		return lbCBInstalacion;
	}

	public List<ActividadDto> obtenerListaActividadesParaIndice(int indice) {
		return tablaLogica.obtenerListaActividades(indice);
	}

	private JPanel getPnLeyendaAlquileres() {
		if (pnLeyendaAlquileres == null) {
			pnLeyendaAlquileres = new JPanel();
			pnLeyendaAlquileres.setBorder(null);
			pnLeyendaAlquileres.setBackground(new Color(65, 105, 225));
			pnLeyendaAlquileres.setBounds(167, 568, 10, 10);
		}
		return pnLeyendaAlquileres;
	}

	private JPanel getPnLeyendaActividadesIlimitadas() {
		if (pnLeyendaActividadesIlimitadas == null) {
			pnLeyendaActividadesIlimitadas = new JPanel();
			pnLeyendaActividadesIlimitadas.setBackground(new Color(173, 255, 47));
			pnLeyendaActividadesIlimitadas.setBounds(167, 589, 10, 10);
		}
		return pnLeyendaActividadesIlimitadas;
	}

	private JPanel getPnLeyendaActividadesLimitidas() {
		if (pnLeyendaActividadesLimitidas == null) {
			pnLeyendaActividadesLimitidas = new JPanel();
			pnLeyendaActividadesLimitidas.setBorder(null);
			pnLeyendaActividadesLimitidas.setBackground(new Color(255, 215, 0));
			pnLeyendaActividadesLimitidas.setBounds(167, 610, 10, 10);
		}
		return pnLeyendaActividadesLimitidas;
	}

	private JLabel getLbLeyendaAlquileres() {
		if (lbLeyendaAlquileres == null) {
			lbLeyendaAlquileres = new JLabel("Alquileres");
			lbLeyendaAlquileres.setBounds(187, 566, 240, 14);
		}
		return lbLeyendaAlquileres;
	}

	private JLabel getLbLeyendaActividadesIlimitadas() {
		if (lbLeyendaActividadesIlimitadas == null) {
			lbLeyendaActividadesIlimitadas = new JLabel("Actividades con plazas ilimitadas");
			lbLeyendaActividadesIlimitadas.setBounds(187, 587, 240, 14);
		}
		return lbLeyendaActividadesIlimitadas;
	}

	private JLabel getLbLeyendaActividadesLimitadas() {
		if (lbLeyendaActividadesLimitadas == null) {
			lbLeyendaActividadesLimitadas = new JLabel("Actividades con plazas limitadas");
			lbLeyendaActividadesLimitadas.setBounds(187, 608, 240, 14);
		}
		return lbLeyendaActividadesLimitadas;
	}

	private JButton getBtnContabilidad() {
		if (btnContabilidad == null) {
			btnContabilidad = new JButton("Contabilidad");
			btnContabilidad.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mostrarVentanaContabilidad();
				}
			});
			btnContabilidad.setMnemonic('D');
			btnContabilidad.setFont(new Font("Arial", Font.BOLD, 13));
			btnContabilidad.setBackground(new Color(65, 105, 225));
			btnContabilidad.setBounds(78, 197, 300, 35);
		}
		return btnContabilidad;
	}

	protected void mostrarVentanaContabilidad() {
		VentanaContabilidad dialog = new VentanaContabilidad(this);
		dialog.setLocationRelativeTo(this);
		dialog.setModal(true);
		dialog.setVisible(true);

	}

	private JButton getBtnAnularAlquileres() {
		if (btnAnularAlquileres == null) {
			btnAnularAlquileres = new JButton("Anular alquileres");
			btnAnularAlquileres.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mostrarVentanaAnulacionAlquiler();
				}
			});
			btnAnularAlquileres.setMnemonic('A');
			btnAnularAlquileres.setFont(new Font("Arial", Font.BOLD, 13));
			btnAnularAlquileres.setBackground(new Color(65, 105, 225));
			btnAnularAlquileres.setBounds(78, 313, 300, 35);
		}
		return btnAnularAlquileres;
	}

	protected void mostrarVentanaAnulacionAlquiler() {
		VentanaAnulacionAlquiler dialog = new VentanaAnulacionAlquiler(this);
		dialog.setLocationRelativeTo(this);
		dialog.setModal(true);
		dialog.setVisible(true);

	}

	private JButton getBtnAlquilarTerceros() {
		if (btnAlquilarTerceros == null) {
			btnAlquilarTerceros = new JButton("Alquilar a un Tercero");
			btnAlquilarTerceros.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mostrarVentanaAlquilarATerceros();
				}
			});
			btnAlquilarTerceros.setMnemonic('Q');
			btnAlquilarTerceros.setFont(new Font("Arial", Font.BOLD, 13));
			btnAlquilarTerceros.setBackground(new Color(65, 105, 225));
			btnAlquilarTerceros.setBounds(78, 371, 300, 35);
		}
		return btnAlquilarTerceros;
	}

	protected void mostrarVentanaAlquilarATerceros() {
		VentanaAlquilarATerceros dialog = new VentanaAlquilarATerceros(this);
		dialog.setLocationRelativeTo(this);
		dialog.setModal(true);
		dialog.setVisible(true);

	}

	private JButton getBtnPlanificarActividadVariosDias() {
		if (btnPlanificarActividadVariosDias == null) {
			btnPlanificarActividadVariosDias = new JButton("Planificar actividades varios días");
			btnPlanificarActividadVariosDias.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mostrarVentanaCreacionActividadesVariosDias();
				}
			});
			btnPlanificarActividadVariosDias.setMnemonic('T');
			btnPlanificarActividadVariosDias.setFont(new Font("Arial", Font.BOLD, 13));
			btnPlanificarActividadVariosDias.setBackground(new Color(65, 105, 225));
			btnPlanificarActividadVariosDias.setBounds(78, 139, 300, 35);
		}
		return btnPlanificarActividadVariosDias;
	}

	private void mostrarVentanaCreacionActividadesVariosDias() {
		VentanaCreacionActividadesVariosDias dialog = new VentanaCreacionActividadesVariosDias(this);
		dialog.setLocationRelativeTo(this);
		dialog.setModal(true);
		dialog.setVisible(true);
	}

	private JButton getBtnAsignarTrasladarRecursos() {
		if (btnAsignarTrasladarRecursos == null) {
			btnAsignarTrasladarRecursos = new JButton("Asignar / trasladar recursos");
			btnAsignarTrasladarRecursos.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mostrarVentanaRecursosIntermedia();
				}
			});
			btnAsignarTrasladarRecursos.setMnemonic('R');
			btnAsignarTrasladarRecursos.setFont(new Font("Arial", Font.BOLD, 13));
			btnAsignarTrasladarRecursos.setBackground(new Color(65, 105, 225));
			btnAsignarTrasladarRecursos.setBounds(78, 255, 300, 35);
		}
		return btnAsignarTrasladarRecursos;
	}

	private void mostrarVentanaRecursosIntermedia() {
		VentanaRecursosIntermedia dialog = new VentanaRecursosIntermedia(this);
		dialog.setLocationRelativeTo(this);
		dialog.setModal(true);
		dialog.setVisible(true);
	}

	private JButton getBtnCierreMantenimiento() {
		if (btnCierreMantenimiento == null) {
			btnCierreMantenimiento = new JButton("Cierre mantenimiento");
			btnCierreMantenimiento.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mostarVentanaCierreMantenimiento();
				}
			});
			btnCierreMantenimiento.setMnemonic('M');
			btnCierreMantenimiento.setFont(new Font("Arial", Font.BOLD, 13));
			btnCierreMantenimiento.setBackground(new Color(65, 105, 225));
			btnCierreMantenimiento.setBounds(78, 429, 300, 35);
		}
		return btnCierreMantenimiento;
	}

	private void mostarVentanaCierreMantenimiento() {
		VentanaConfirmacionMantenimiento dialog = new VentanaConfirmacionMantenimiento(this);
		dialog.setLocationRelativeTo(this);
		dialog.setModal(true);
		dialog.setVisible(true);
	}
}