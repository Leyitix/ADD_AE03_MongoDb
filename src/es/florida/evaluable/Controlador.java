package es.florida.evaluable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Controlador {

	LogIn login;
	Vista vista;
	Modelo modelo;
	Popup popup;

	int idSeleccionado;
	boolean anyadir = false, modificar = false, eliminar = false, mostrar = false;

	static ImageIcon imagen;

	/**
	 * Metodo constructor de la clase Controlador
	 * 
	 * @param login  Recibe el frame para realizar el login
	 * 
	 * @param vista  Recibe el frame principal vista
	 * 
	 * @param popup  Recibe el frame que muestra los libros
	 * 
	 * @param modelo Recibe la clase modelo
	 */
	Controlador(LogIn login, Vista vista, Popup popup, Modelo modelo) {
		this.login = login;
		this.vista = vista;
		this.modelo = modelo;
		this.popup = popup;
		control();
	}

	/**
	 * Metodo que contiene los actionListeners que realizan la interaccion entre la
	 * interfaz y la clase modelo
	 * 
	 * - actionListenerAcceder: boton que permite logear al usuario
	 * 
	 * - actionListenerResumen: muestra el resumen de los libros en el textArea
	 * 
	 * - actionListenerAnyadir: muestra el popup para anyadir un nuevo libro
	 * 
	 * - actionListenerModificar: muestra el popup para modificar un libro
	 * 
	 * - actionListenerEliminar: muestra el popup para eliminar un libro
	 * 
	 * - actionListenerMostrar: muestra el popup para ver un determinado libro
	 * 
	 * - actionListenerCancelar: boton para cancelar las acciones en el popup y
	 * cerrar el frame
	 * 
	 * - actionListenerAceptar: boton para aceptar las acciones en el popup y cerrar
	 * el frame
	 * 
	 * - actionListenerBuscar: boton para realizar la consulta a la base de datos
	 */
	public void control() {

		ActionListener actionListenerAcceder = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String user = login.getTextFieldUser().getText();

				@SuppressWarnings("deprecation")
				String password = login.getPasswordField().getText();
				String sha256 = modelo.convertirSHA256(password);

				boolean access = modelo.login(user, sha256);
				vista.acceder(access);

			}
		};

		ActionListener actionListenerResumen = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				vista.getTextArea().setText("");
				ArrayList<String> libros = modelo.mostrarResumen();
				for (String libro : libros) {
					vista.getTextArea().append(libro + "\n");
				}
			}

		};

		ActionListener actionListenerAnyadir = new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarTextInput();

				setAnyadir(true);
				setModificar(false);
				setEliminar(false);
				setMostrar(false);

				popup.getLblPopup().setText("A??adir libro");
				popup.getComboBox().hide();
				popup.getBtnAceptar().show();
				popup.getLblRutaImg().show();
				popup.getTextThumbnail().show();
				popup.getBtnCancelar().setText("Cancelar");

				// Default image
				imagenPorDefecto();

				enable();
				vista.anyadirPopup();

			}

		};

		ActionListener actionListenerModificar = new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {

				setAnyadir(false);
				setModificar(true);
				setEliminar(false);
				setMostrar(false);

				popup.getLblPopup().setText("Modificar libro");
				popup.getComboBox().show();
				popup.getBtnAceptar().show();
				popup.getLblRutaImg().show();
				popup.getTextThumbnail().show();
				popup.getBtnCancelar().setText("Cancelar");

				// Default image
				imagenPorDefecto();

				enable();
				vista.anyadirPopup();

				ArrayList<String> libros = modelo.mostrarResumen();
				popup.setComboBox(libros);

				popup.getComboBox().addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						if (isModificar()) {
							int id = getId();
							String[] data = modelo.cargarTextInput(id);
							dataComboBox(data);

							// Image
							try {
								String nombreImagen = id + ".png";
								imagen = modelo.leerMostrarImagen(nombreImagen);
								popup.getLblImage().setIcon(imagen);
							} catch (IOException e1) {
								e1.printStackTrace();
							}

						}

					}
				});
			}

		};

		ActionListener actionListenerEliminar = new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {

				setAnyadir(false);
				setModificar(false);
				setEliminar(true);
				setMostrar(false);

				popup.getLblPopup().setText("Eliminar libro");
				popup.getComboBox().show();
				popup.getBtnAceptar().show();
				popup.getLblRutaImg().hide();
				popup.getTextThumbnail().hide();
				popup.getBtnCancelar().setText("Cancelar");

				// Default image
				imagenPorDefecto();

				disable();
				vista.anyadirPopup();

				ArrayList<String> libros = modelo.mostrarResumen();
				popup.setComboBox(libros);

				popup.getComboBox().addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {

						if (isEliminar() == true) {
							int id = getId();
							String[] data = modelo.cargarTextInput(id);
							dataComboBox(data);

							// Image
							try {
								String nombreImagen = id + ".png";
								imagen = modelo.leerMostrarImagen(nombreImagen);
								popup.getLblImage().setIcon(imagen);
							} catch (IOException e1) {
								e1.printStackTrace();
							}

						}

					}
				});
			}

		};

		ActionListener actionListenerMostrar = new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {

				setAnyadir(false);
				setModificar(false);
				setEliminar(false);
				setMostrar(true);

				popup.getLblPopup().setText("Mostrar libro");
				popup.getComboBox().show();
				popup.getBtnAceptar().hide();
				popup.getLblRutaImg().hide();
				popup.getTextThumbnail().hide();
				popup.getBtnCancelar().setText("Cerrar");

				// Default image
				imagenPorDefecto();

				disable();
				vista.anyadirPopup();

				ArrayList<String> libros = modelo.mostrarResumen();
				popup.setComboBox(libros);

				popup.getComboBox().addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {

						if (isMostrar() == true) {
							disable();
							limpiarTextInput();
							int id = getId();
							String[] data = modelo.cargarTextInput(id);
							dataComboBox(data);

							// Image
							try {
								String nombreImagen = id + ".png";
								imagen = modelo.leerMostrarImagen(nombreImagen);
								popup.getLblImage().setIcon(imagen);
							} catch (IOException e1) {
								e1.printStackTrace();
							}

						}

					}
				});

			}

		};

		ActionListener actionListenerCancelar = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				popup.setVisible(false);
				popup.dispose();
				setAnyadir(false);
				setModificar(false);
				setEliminar(false);
				setMostrar(false);
				limpiarTextInput();
			}

		};

		ActionListener actionListenerAceptar = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String titulo = popup.getTextTitulo().getText();
				String autor = popup.getTextAutor().getText();
				String anyNac = popup.getTextAnyoNac().getText();
				String anyPub = popup.getTextAnyoPub().getText();
				String editorial = popup.getTextEditorial().getText();
				String numPag = popup.getTextNumPag().getText();
				String thumb = popup.getTextThumbnail().getText();

				if (isAnyadir() == true) {
					try {
						modelo.anyadirLibro(titulo, autor, anyNac, anyPub, editorial, numPag, thumb);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(new JFrame(), "Libro a??adido con ??xito", "A??adir",
							JOptionPane.INFORMATION_MESSAGE);
					popup.setVisible(false);
					limpiarTextInput();
					popup.dispose();
				}

				if (isModificar() == true) {

					int option = JOptionPane.showConfirmDialog(null, "??Est??s seguro que deseas modificar el libro?",
							"Modificar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					int id = getId();

					if (option == 0) {
						modelo.modificarLibro(titulo, autor, anyNac, anyPub, editorial, numPag, thumb,
								getIdSeleccionado());
						disable();

						String[] data = modelo.cargarTextInput(id);
						dataComboBox(data);

						JOptionPane.showMessageDialog(new JFrame(), "Libro modificado con ??xito", "Modificar",
								JOptionPane.INFORMATION_MESSAGE);

						limpiarTextInput();
						imagenPorDefecto();
						popup.setVisible(false);
						popup.dispose();

					} else {
						JOptionPane.showMessageDialog(new JFrame(), "No se ha modificado el libro", "Modificar",
								JOptionPane.INFORMATION_MESSAGE);

						popup.getComboBox().setSelectedIndex(id);
					}

				}

				if (isEliminar() == true) {

					int id = getId();

					int option = JOptionPane.showConfirmDialog(null, "??Est??s seguro que deseas eliminar el libro?",
							"Eliminar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (option == 0) {
						modelo.eliminarLibro(id);
						JOptionPane.showMessageDialog(new JFrame(), "Libro eliminado con ??xito", "Eliminar",
								JOptionPane.INFORMATION_MESSAGE);
						limpiarTextInput();
						imagenPorDefecto();
						popup.setVisible(false);
						popup.dispose();

					} else {
						JOptionPane.showMessageDialog(new JFrame(), "No se ha eliminado el libro", "Eliminar",
								JOptionPane.INFORMATION_MESSAGE);

						popup.getComboBox().setSelectedIndex(id);
					}
				}
			}

		};

		ActionListener actionListenerBuscar = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				vista.getTextArea().setText("");

				int indexCampo = vista.getComboBoxCampo().getSelectedIndex();
				int indexFiltro = vista.getComboBoxFiltro().getSelectedIndex();
				String valor = vista.getTextValor().getText();

				try {
					ArrayList<String> consultas = modelo.consultas(indexCampo, indexFiltro, valor);

					for (String consulta : consultas) {
						vista.getTextArea().append(consulta + "\n");
					}

				} catch (Exception e2) {
					JOptionPane.showMessageDialog(new JFrame(), "No se encuentran coincidencias", "ERROR",
							JOptionPane.ERROR_MESSAGE);
				}

			}

		};

		login.getBtnAcceder().addActionListener(actionListenerAcceder);

		vista.getBtnResumen().addActionListener(actionListenerResumen);
		vista.getBtnAnyadir().addActionListener(actionListenerAnyadir);
		vista.getBtnModificar().addActionListener(actionListenerModificar);
		vista.getBtnEliminar().addActionListener(actionListenerEliminar);
		vista.getBtnMostrar().addActionListener(actionListenerMostrar);
		vista.getBtnBuscar().addActionListener(actionListenerBuscar);

		popup.getBtnCancelar().addActionListener(actionListenerCancelar);
		popup.getBtnAceptar().addActionListener(actionListenerAceptar);

	}

	/**
	 * Metodo que permite deshabilitar los textInput para evitar que el usuario
	 * modifique su contenido
	 */
	@SuppressWarnings("deprecation")
	public void disable() {
		popup.getTextTitulo().disable();
		popup.getTextAutor().disable();
		popup.getTextAnyoNac().disable();
		popup.getTextAnyoPub().disable();
		popup.getTextEditorial().disable();
		popup.getTextNumPag().disable();
		popup.getTextThumbnail().disable();
	}

	/**
	 * Metodo que permite habilitar los textInput para permitir que el usuario
	 * modifique su contenido
	 */
	@SuppressWarnings("deprecation")
	public void enable() {
		popup.getTextTitulo().enable();
		popup.getTextAutor().enable();
		popup.getTextAnyoNac().enable();
		popup.getTextAnyoPub().enable();
		popup.getTextEditorial().enable();
		popup.getTextNumPag().enable();
		popup.getTextThumbnail().enable();
	}

	/**
	 * Metodo que vacia los textInput de contenido
	 */
	public void limpiarTextInput() {
		popup.getTextTitulo().setText("");
		popup.getTextAutor().setText("");
		popup.getTextAnyoNac().setText("");
		popup.getTextAnyoPub().setText("");
		popup.getTextEditorial().setText("");
		popup.getTextNumPag().setText("");
		popup.getTextThumbnail().setText("");
	}

	/**
	 * Metodo que rellena los textInput de los datos de un libro determinado
	 * 
	 * @param data Recibe un array con los datos de un determinado libro
	 */
	public void dataComboBox(String[] data) {
		popup.getTextTitulo().setText(data[0]);
		popup.getTextAutor().setText(data[1]);
		popup.getTextAnyoNac().setText(data[2]);
		popup.getTextAnyoPub().setText(data[3]);
		popup.getTextEditorial().setText(data[4]);
		popup.getTextNumPag().setText(data[5]);
		popup.getTextThumbnail().setText(data[6]);

		int id = Integer.parseInt(data[7]);
		setIdSeleccionado(id);
	}

	/**
	 * Metodo que devuelve el id del libro seleccionado en el comboBox
	 * 
	 * @return id Id del libro seleccionado
	 */
	public int getId() {
		String cadena = (String) popup.getComboBox().getSelectedItem();
		int posicionPunto = cadena.indexOf('.');
		String sHastaPrimerPunto = cadena.substring(0, posicionPunto);
		int id = Integer.parseInt(sHastaPrimerPunto);

		return id;
	}

	/**
	 * Metodo que devuelve una imagen por defecto para mostrar cuando no haya una
	 * imagen valida para mostrar
	 */
	public void imagenPorDefecto() {
		// Default image
		try {
			imagen = modelo.leerMostrarImagen("default-placeholder.png");
			popup.getLblImage().setIcon(imagen);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * @return the idSeleccionado
	 */
	public int getIdSeleccionado() {
		return idSeleccionado;
	}

	/**
	 * @param idSeleccionado the idSeleccionado to set
	 */
	public void setIdSeleccionado(int idSeleccionado) {
		this.idSeleccionado = idSeleccionado;
	}

	/**
	 * @return the anyadir
	 */
	public boolean isAnyadir() {
		return anyadir;
	}

	/**
	 * @param anyadir the anyadir to set
	 */
	public void setAnyadir(boolean anyadir) {
		this.anyadir = anyadir;
	}

	/**
	 * @return the modificar
	 */
	public boolean isModificar() {
		return modificar;
	}

	/**
	 * @param modificar the modificar to set
	 */
	public void setModificar(boolean modificar) {
		this.modificar = modificar;
	}

	/**
	 * @return the eliminar
	 */
	public boolean isEliminar() {
		return eliminar;
	}

	/**
	 * @param eliminar the eliminar to set
	 */
	public void setEliminar(boolean eliminar) {
		this.eliminar = eliminar;
	}

	/**
	 * @return the mostrar
	 */
	public boolean isMostrar() {
		return mostrar;
	}

	/**
	 * @param mostrar the mostrar to set
	 */
	public void setMostrar(boolean mostrar) {
		this.mostrar = mostrar;
	}

}
