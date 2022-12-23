package es.florida.evaluable;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class LogIn extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldUser;
	private JPasswordField passwordField;
	private JLabel lblContrasenya;
	private JButton btnAcceder;

	/**
	 * Metodo que crea el frame para realizar el login de usuario
	 */
	public LogIn() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 381, 151);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		textFieldUser = new JTextField();
		textFieldUser.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		textFieldUser.setBounds(92, 11, 254, 26);
		contentPane.add(textFieldUser);
		textFieldUser.setColumns(10);

		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		lblUsuario.setBounds(15, 13, 54, 23);
		contentPane.add(lblUsuario);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		passwordField.setBounds(92, 48, 254, 26);
		contentPane.add(passwordField);

		lblContrasenya = new JLabel("Contraseña");
		lblContrasenya.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		lblContrasenya.setBounds(15, 48, 72, 25);
		contentPane.add(lblContrasenya);

		btnAcceder = new JButton("Acceder");
		btnAcceder.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
		btnAcceder.setBounds(249, 81, 97, 23);
		contentPane.add(btnAcceder);
	}

	/**
	 * @return the textFieldUser
	 */
	public JTextField getTextFieldUser() {
		return textFieldUser;
	}

	/**
	 * @return the passwordField
	 */
	public JPasswordField getPasswordField() {
		return passwordField;
	}

	/**
	 * @return the btnAcceder
	 */
	public JButton getBtnAcceder() {
		return btnAcceder;
	}
}
