package es.florida.evaluable;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class Vista extends JFrame {

	public static LogIn login;
	public static Vista vista;
	public static Popup popup;

	private JPanel contentPane;
	private JButton btnResumen, btnAnyadir, btnModificar, btnEliminar, btnMostrar, btnBuscar;

	private JTextArea textArea;
	private JSeparator separator_2;
	private JTextField textValor;

	private JComboBox<String> comboBoxFiltro, comboBoxCampo;

	/**
	 * Metodo que se ejecuta despues de realizar el login. Si el login devuelve true
	 * oculta el login y muestra el frame vista
	 * 
	 * @param access Recibe el acceso a la aplicacion
	 */
	public void acceder(boolean access) {
		if (access) {
			login.setVisible(false);
			login.dispose();
			vista.setVisible(true);
			vista.setLocationRelativeTo(null);
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Contraseña o usuario erroneos", "", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Metodo que permite mostrar el popup con los datos de un libro
	 */
	public void anyadirPopup() {
		popup.setVisible(true);
		popup.setLocationRelativeTo(null);
	}

	/**
	 * Metodo main que lanza la aplicacion
	 * 
	 * @param args Recibe los argumentos de la aplicacion
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					// Primero se ejecuta el login
					login = new LogIn();
					login.setVisible(true);
					login.setLocationRelativeTo(null);

					vista = new Vista();
					vista.setVisible(false);

					Modelo modelo = new Modelo(popup);
					modelo.conectareBd();

					popup = new Popup();

					@SuppressWarnings("unused")
					Controlador controlador = new Controlador(login, vista, popup, modelo);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Metodo que crea el frame principal de la aplicacion
	 */
	public Vista() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 753, 529);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Biblioteca");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 17));
		lblNewLabel.setBounds(283, 11, 175, 23);
		contentPane.add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(217, 52, 509, 382);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		textArea.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		textArea.setBorder(BorderFactory.createCompoundBorder(textArea.getBorder(),
				BorderFactory.createEmptyBorder(20, 20, 20, 20)));
		scrollPane.setViewportView(textArea);

		btnResumen = new JButton("Resumen de la colección");
		btnResumen.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		btnResumen.setBounds(10, 52, 197, 23);
		contentPane.add(btnResumen);

		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(0, 0, 0));
		separator.setBounds(10, 40, 716, 2);
		contentPane.add(separator);

		btnAnyadir = new JButton("Añadir libro");
		btnAnyadir.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		btnAnyadir.setBounds(10, 133, 197, 23);
		contentPane.add(btnAnyadir);

		btnModificar = new JButton("Modificar libro");
		btnModificar.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		btnModificar.setBounds(10, 163, 197, 23);
		contentPane.add(btnModificar);

		btnEliminar = new JButton("Eliminar libro");
		btnEliminar.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		btnEliminar.setBounds(10, 192, 197, 23);
		contentPane.add(btnEliminar);

		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(new Color(0, 0, 0));
		separator_1.setBounds(10, 86, 197, 2);
		contentPane.add(separator_1);

		btnMostrar = new JButton("Mostrar libro");
		btnMostrar.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		btnMostrar.setBounds(10, 99, 197, 23);
		contentPane.add(btnMostrar);

		separator_2 = new JSeparator();
		separator_2.setForeground(Color.BLACK);
		separator_2.setBounds(10, 226, 197, 2);
		contentPane.add(separator_2);

		JLabel lblConsultasALa = new JLabel("Consultas a la base de datos");
		lblConsultasALa.setHorizontalAlignment(SwingConstants.CENTER);
		lblConsultasALa.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 13));
		lblConsultasALa.setBounds(20, 237, 175, 14);
		contentPane.add(lblConsultasALa);

		comboBoxCampo = new JComboBox<String>();
		comboBoxCampo.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		comboBoxCampo.setBounds(10, 278, 197, 22);
		contentPane.add(comboBoxCampo);
		setComboBoxCampo();

		JLabel lblCcampo = new JLabel("Campo");
		lblCcampo.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		lblCcampo.setBounds(10, 262, 50, 14);
		contentPane.add(lblCcampo);

		JLabel lblFiltro = new JLabel("Filtro");
		lblFiltro.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		lblFiltro.setBounds(10, 311, 38, 14);
		contentPane.add(lblFiltro);

		comboBoxFiltro = new JComboBox<String>();
		comboBoxFiltro.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		comboBoxFiltro.setBounds(10, 327, 197, 22);
		contentPane.add(comboBoxFiltro);
		setComboBoxFiltro();

		textValor = new JTextField();
		textValor.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		textValor.setBounds(10, 375, 197, 23);
		contentPane.add(textValor);
		textValor.setColumns(10);

		JLabel lblValor = new JLabel("Valor");
		lblValor.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		lblValor.setBounds(10, 360, 38, 14);
		contentPane.add(lblValor);

		JSeparator separator_2_1 = new JSeparator();
		separator_2_1.setForeground(Color.BLACK);
		separator_2_1.setBounds(10, 460, 716, 2);
		contentPane.add(separator_2_1);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		btnBuscar.setBounds(10, 411, 197, 23);
		contentPane.add(btnBuscar);
	}

	/**
	 * @return the btnResumen
	 */
	public JButton getBtnResumen() {
		return btnResumen;
	}

	/**
	 * @return the textArea
	 */
	public JTextArea getTextArea() {
		return textArea;
	}

	/**
	 * @return the btnAnyadir
	 */
	public JButton getBtnAnyadir() {
		return btnAnyadir;
	}

	/**
	 * @return the btnModificar
	 */
	public JButton getBtnModificar() {
		return btnModificar;
	}

	/**
	 * @return the btnEliminar
	 */
	public JButton getBtnEliminar() {
		return btnEliminar;
	}

	/**
	 * @return btnMostrarLibro
	 */
	public JButton getBtnMostrar() {
		return btnMostrar;
	}

	/**
	 * @return comboBoxFiltro
	 */
	public JComboBox<String> getComboBoxFiltro() {
		return comboBoxFiltro;
	}

	/**
	 * Metodo que setea el combobox con los filtros para realizar la consulta a la
	 * base de datos
	 */
	public void setComboBoxFiltro() {
		JComboBox<String> combo = getComboBoxFiltro();
		combo.addItem("Igual (eq)");
		combo.addItem("Mayor o igual (gte)");
		combo.addItem("Menor o igual (lte)");
	}

	/**
	 * @return comboBoxCampo
	 */
	public JComboBox<String> getComboBoxCampo() {
		return comboBoxCampo;
	}

	/**
	 * Metodo que setea el combobox con los campos de la base de datos
	 */
	public void setComboBoxCampo() {
		JComboBox<String> combo = getComboBoxCampo();
		combo.addItem("Id");
		combo.addItem("Título");
		combo.addItem("Autor");
		combo.addItem("Año de nacimiento");
		combo.addItem("Año de publicación");
		combo.addItem("Editorial");
		combo.addItem("Número de páginas");
		combo.addItem("Imagen");
	}

	/**
	 * @return the textFieldValor
	 */
	public JTextField getTextValor() {
		return textValor;
	}

	/**
	 * @return the btnBuscar
	 */
	public JButton getBtnBuscar() {
		return btnBuscar;
	}
}
