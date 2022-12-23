package es.florida.evaluable;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class Popup extends JFrame {

	private JPanel contentPane;
	private JButton btnCancelar;
	private JButton btnAceptar;
	private JTextField textTitulo;
	private JTextField textAutor;
	private JTextField textAnyoNac;
	private JTextField textAnyoPub;
	private JTextField textEditorial;
	private JTextField textNumPag;
	private JTextField textThumbnail;
	private JLabel lblAoDeNacimiento;
	private JLabel lblAoDePublicacin;
	private JLabel lblEditorial;
	private JLabel lblPopup;
	private JComboBox<String> comboBox;
	private JLabel lblImage;
	JLabel lblRutaImg;

	/**
	 * Metodo que crea el popup para ver los datos de un libro
	 */
	public Popup() {
		setBounds(100, 100, 751, 433);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		btnCancelar.setBounds(335, 360, 130, 23);
		contentPane.add(btnCancelar);

		textTitulo = new JTextField();
		textTitulo.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		textTitulo.setBounds(171, 80, 293, 29);
		contentPane.add(textTitulo);
		textTitulo.setColumns(10);

		textAutor = new JTextField();
		textAutor.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		textAutor.setColumns(10);
		textAutor.setBounds(171, 120, 293, 29);
		contentPane.add(textAutor);

		textAnyoNac = new JTextField();
		textAnyoNac.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		textAnyoNac.setColumns(10);
		textAnyoNac.setBounds(172, 160, 293, 29);
		contentPane.add(textAnyoNac);

		textAnyoPub = new JTextField();
		textAnyoPub.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
		textAnyoPub.setColumns(10);
		textAnyoPub.setBounds(172, 200, 293, 29);
		contentPane.add(textAnyoPub);

		textEditorial = new JTextField();
		textEditorial.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		textEditorial.setColumns(10);
		textEditorial.setBounds(171, 240, 293, 29);
		contentPane.add(textEditorial);

		textNumPag = new JTextField();
		textNumPag.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		textNumPag.setColumns(10);
		textNumPag.setBounds(171, 280, 293, 29);
		contentPane.add(textNumPag);

		textThumbnail = new JTextField();
		textThumbnail.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		textThumbnail.setColumns(10);
		textThumbnail.setBounds(171, 320, 293, 29);
		contentPane.add(textThumbnail);

		lblRutaImg = new JLabel("Ruta de la imagen");
		lblRutaImg.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		lblRutaImg.setBounds(34, 324, 120, 18);
		contentPane.add(lblRutaImg);

		JLabel lblNmeroDePginas = new JLabel("Nº de páginas");
		lblNmeroDePginas.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		lblNmeroDePginas.setBounds(34, 284, 120, 18);
		contentPane.add(lblNmeroDePginas);

		JLabel lblAutor = new JLabel("Autor/a");
		lblAutor.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		lblAutor.setBounds(34, 124, 58, 18);
		contentPane.add(lblAutor);

		JLabel lblTitulo = new JLabel("Titulo");
		lblTitulo.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		lblTitulo.setBounds(34, 84, 58, 18);
		contentPane.add(lblTitulo);

		lblAoDeNacimiento = new JLabel("Año de nacimiento");
		lblAoDeNacimiento.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		lblAoDeNacimiento.setBounds(34, 164, 120, 18);
		contentPane.add(lblAoDeNacimiento);

		lblAoDePublicacin = new JLabel("Año de publicación");
		lblAoDePublicacin.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		lblAoDePublicacin.setBounds(34, 204, 120, 18);
		contentPane.add(lblAoDePublicacin);

		lblEditorial = new JLabel("Editorial");
		lblEditorial.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		lblEditorial.setBounds(34, 244, 113, 18);
		contentPane.add(lblEditorial);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		btnAceptar.setBounds(199, 360, 130, 23);
		contentPane.add(btnAceptar);

		lblPopup = new JLabel("Añadir libro");
		lblPopup.setHorizontalAlignment(SwingConstants.CENTER);
		lblPopup.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 15));
		lblPopup.setBounds(172, 11, 153, 23);
		contentPane.add(lblPopup);

		comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 13));
		comboBox.setBounds(34, 45, 431, 22);
		contentPane.add(comboBox);

		lblImage = new JLabel();
		lblImage.setBounds(491, 45, 222, 326);
		contentPane.add(lblImage);
	}

	/**
	 * @return the lblRutaImg
	 */
	public JLabel getLblRutaImg() {
		return lblRutaImg;
	}

	/**
	 * @return the comboBox
	 */
	public JComboBox<String> getComboBox() {
		return comboBox;
	}

	/**
	 * @param libros Recibe la coleccion de libros para mostrar en el comboBox
	 */
	public void setComboBox(ArrayList<String> libros) {

		JComboBox<String> combo = getComboBox();
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		for (String libro : libros) {
			model.addElement(libro);
		}
		combo.setModel(model);

	}

	/**
	 * @return the btnCancelar
	 */
	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	/**
	 * @return the btnAceptar
	 */
	public JButton getBtnAceptar() {
		return btnAceptar;
	}

	/**
	 * @param btnAceptar the btnAceptar to set
	 */
	public void setBtnAceptar(JButton btnAceptar) {
		this.btnAceptar = btnAceptar;
	}

	/**
	 * @return the textTitulo
	 */
	public JTextField getTextTitulo() {
		return textTitulo;
	}

	/**
	 * @param textTitulo the textTitulo to set
	 */
	public void setTextTitulo(JTextField textTitulo) {
		this.textTitulo = textTitulo;
	}

	/**
	 * @return the textAutor
	 */
	public JTextField getTextAutor() {
		return textAutor;
	}

	/**
	 * @param textAutor the textAutor to set
	 */
	public void setTextAutor(JTextField textAutor) {
		this.textAutor = textAutor;
	}

	/**
	 * @return the textAnyoNac
	 */
	public JTextField getTextAnyoNac() {
		return textAnyoNac;
	}

	/**
	 * @param textAnyoNac the textAnyoNac to set
	 */
	public void setTextAnyoNac(JTextField textAnyoNac) {
		this.textAnyoNac = textAnyoNac;
	}

	/**
	 * @return the textAnyoPub
	 */
	public JTextField getTextAnyoPub() {
		return textAnyoPub;
	}

	/**
	 * @param textAnyoPub the textAnyoPub to set
	 */
	public void setTextAnyoPub(JTextField textAnyoPub) {
		this.textAnyoPub = textAnyoPub;
	}

	/**
	 * @return the textEditorial
	 */
	public JTextField getTextEditorial() {
		return textEditorial;
	}

	/**
	 * @param textEditorial the textEditorial to set
	 */
	public void setTextEditorial(JTextField textEditorial) {
		this.textEditorial = textEditorial;
	}

	/**
	 * @return the textNumPag
	 */
	public JTextField getTextNumPag() {
		return textNumPag;
	}

	/**
	 * @param textNumPag the textNumPag to set
	 */
	public void setTextNumPag(JTextField textNumPag) {
		this.textNumPag = textNumPag;
	}

	/**
	 * @return the textThumbnail
	 */
	public JTextField getTextThumbnail() {
		return textThumbnail;
	}

	/**
	 * @param textThumbnail the textThumbnail to set
	 */
	public void setTextThumbnail(JTextField textThumbnail) {
		this.textThumbnail = textThumbnail;
	}

	/**
	 * @return the lblPopup
	 */
	public JLabel getLblPopup() {
		return lblPopup;
	}

	/**
	 * @param lblPopup the lblPopup to set
	 */
	public void setLblPopup(JLabel lblPopup) {
		this.lblPopup = lblPopup;
	}

	/**
	 * @param btnCancelar the btnCancelar to set
	 */
	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	/**
	 * @return the lblImage
	 */
	public JLabel getLblImage() {
		return lblImage;
	}
}
