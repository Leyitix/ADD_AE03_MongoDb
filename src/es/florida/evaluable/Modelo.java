package es.florida.evaluable;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.bson.Document;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import org.apache.commons.codec.binary.Base64;
//import static com.mongodb.client.model.Filters.*;

public class Modelo {

	private MongoCollection<Document> coleccionUsuarios, coleccionLibros;
	private boolean access;

	Popup popup;

	/**
	 * Metodo constructor de la clase Modelo
	 * 
	 * @param popup frame que contiene los datos del libro
	 */
	Modelo(Popup popup) {
		this.popup = popup;
	}

	/**
	 * Metodo que sirve para crear la conexión con la base de datos
	 * 
	 */
	public void conectareBd() {

		try {

			BufferedReader br = new BufferedReader(new FileReader("conexion.json"));
			String linea, lineasJson = "", usuarios, host, bd, libros;
			int puerto;

			while ((linea = br.readLine()) != null) {
				lineasJson += linea;
			}

			br.close();

			JSONObject jsO = new JSONObject(lineasJson);
			host = jsO.getString("IP");
			puerto = jsO.getInt("puerto");
			bd = jsO.getString("baseDatos");
			usuarios = jsO.getString("coleccion_01");
			libros = jsO.getString("coleccion_02");

			// Conexion con Mongodb
			@SuppressWarnings("resource")
			MongoClient mongoClient = new MongoClient(host, puerto);
			MongoDatabase database = mongoClient.getDatabase(bd);

			setColeccionUsuarios(database.getCollection(usuarios));
			setColeccionLibros(database.getCollection(libros));

		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), "No se ha puede crear la conexión con la base de datos",
					"Conexión base de datos", JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * Metodo que convierte una contraseña en texto plano a SHA256
	 * 
	 * @param password Contrasenya introducida por el usuario
	 * 
	 * @return sb.toString() Devuelve la contrasenya encriptada
	 */
	public String convertirSHA256(String password) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}

		byte[] hash = md.digest(password.getBytes());
		StringBuffer sb = new StringBuffer();

		for (byte b : hash) {
			sb.append(String.format("%02x", b));
		}

		return sb.toString();
	}

	/**
	 * Metodo que comprueba si la contrasenya y el nombre de usuario que se
	 * introduce conincide con el que se encuentra en la base de datos
	 * 
	 * @param user     Nombre de usuario
	 * 
	 * @param password Contraseña del usuario
	 * 
	 * @return isAccess() Retorna un boolean con el permiso de acceso a la
	 *         aplicacion
	 */
	public boolean login(String user, String password) {

		MongoCursor<Document> cursor = getColeccionUsuarios().find().iterator();

		cursor = getColeccionUsuarios().find().iterator();

		while (cursor.hasNext()) {
			JSONObject obj = new JSONObject(cursor.next().toJson());
			if (obj.getString("user").equals(user) && obj.getString("pass").equals(password)) {
				setAccess(true);
			}
		}

		return isAccess();
	}

	/**
	 * Metodo que muestra el total de libros que se encuentran en la base de datos
	 * 
	 * @return libros Retorna el id y el titulo de cada uno de los libros
	 */
	public ArrayList<String> mostrarResumen() {

		ArrayList<String> libros = new ArrayList<String>();
		MongoCursor<Document> cursor = getColeccionLibros().find().iterator();

		cursor = getColeccionLibros().find().iterator();

		while (cursor.hasNext()) {
			JSONObject obj = new JSONObject(cursor.next().toJson());
			String id = String.valueOf(obj.getInt("Id"));
			String titulo = String.valueOf(obj.getString("Titulo"));
			libros.add(id + ". " + titulo);
		}

		return libros;
	}

	/**
	 * Metodo que anyade un nuevo documento a la base de datos
	 * 
	 * @param titulo     Titulo que introduce el usuario
	 * 
	 * @param autor      Nombre del autor/a del libro
	 * 
	 * @param anyoNac    Anyo de nacimiento del autor/a
	 * 
	 * @param anyoPub    Anyo de publicacion del libro
	 * 
	 * @param editorial  Editorial del libro
	 * 
	 * @param numPag     Numero de paginas del libro
	 * 
	 * @param rutaImagen Ruta al directorio en el que se encuentra la imagen de
	 *                   portada del libro
	 */
	public void anyadirLibro(String titulo, String autor, String anyoNac, String anyoPub, String editorial,
			String numPag, String rutaImagen) throws IOException {

		try {
			// Obtener ultimo id y sumarle 1
			Document lastDoc = getColeccionLibros().find().sort(new BasicDBObject("Id", -1)).first();
			Object lastId = lastDoc.get("Id");
			int id = (Integer) lastId + 1;

			int anyNacInt = 0;
			int anyPubInt = 0;
			int numPagInt = 0;
			String thumbnail = "Sin portada";

			if (titulo.equals(""))
				titulo = "Desconocido";
			if (autor.equals(""))
				autor = "Desconocido";
			if (anyoNac != null)
				anyNacInt = Integer.parseInt(anyoNac);
			if (anyoPub != null)
				anyPubInt = Integer.parseInt(anyoPub);
			if (editorial.equals(""))
				autor = "Desconocido";
			if (numPag != null)
				numPagInt = Integer.parseInt(numPag);
			if (numPag != null)
				thumbnail = imagenABytes(rutaImagen);

			Document doc = new Document();
			doc.append("Id", id);
			doc.append("Titulo", titulo);
			doc.append("Autor", autor);
			doc.append("Anyo_nacimiento", anyNacInt);
			doc.append("Anyo_publicacion", anyPubInt);
			doc.append("Editorial", editorial);
			doc.append("Numero_paginas", numPagInt);
			doc.append("Thumbnail", thumbnail);
			getColeccionLibros().insertOne(doc);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), "No se ha podido añadir el libro", "Datos erroneos",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * Metodo que recoge los datos de un determinado documento y los devuelve en un
	 * array de strings
	 * 
	 * @param libroSeleccionado Recibe como parametro el id del libro seleccionado
	 * 
	 * @return String[] data Devuelve todos los datos de un libro determinado
	 *         contenidos en un array de Strings
	 */
	public String[] cargarTextInput(Integer libroSeleccionado) {

		Document document = getColeccionLibros().find(Filters.eq("Id", libroSeleccionado)).first();

		String titulo = (String) document.get("Titulo");
		String autor = (String) document.get("Autor");

		int anyo_nac = 0;
		String anyNacStr = null;

		try {
			anyo_nac = (int) document.get("Anyo_nacimiento");
			anyNacStr = String.valueOf(anyo_nac);
		} catch (Exception e) {
			anyNacStr = "DESCONOCIDO";
		}

		int anyo_pub = (int) document.get("Anyo_publicacion");
		String editorial = (String) document.get("Editorial");
		int num_paginas = (int) document.get("Numero_paginas");
		String thumbnail = (String) document.get("Thumbnail");

		String anyPubStr = String.valueOf(anyo_pub);
		String numPagStr = String.valueOf(num_paginas);
		String idStr = String.valueOf(libroSeleccionado);

		try {

			// Crear BufferedImage y guardarlo como png
			BufferedImage imDecodificada = decodificarString(thumbnail);
			guardarPng(imDecodificada, idStr);

		} catch (IOException e) {
			e.printStackTrace();
		}

		String[] data = { titulo, autor, anyNacStr, anyPubStr, editorial, numPagStr, thumbnail, idStr };
		return data;
	}

	/**
	 * Metodo que decodifica un String en base 64 y lo transforma a un BufferedImage
	 * 
	 * @param string64 Recibe el string en base 64 desde la base de datos
	 * 
	 * @return retorna la imagen
	 */
	public BufferedImage decodificarString(String string64) throws IOException {
		byte[] btDataFile = Base64.decodeBase64(string64);
		BufferedImage imagen = ImageIO.read(new ByteArrayInputStream(btDataFile));
		return imagen;
	}

	/**
	 * Transforma una imagen que la aplicacion recibe desde una ruta determinada y
	 * codifica a encodeBase64String
	 * 
	 * @param ruta Ruta en la que se encuentra la imagen
	 * 
	 * @return encodedString Devuelve la imagen codificada
	 */
	public String imagenABytes(String ruta) throws IOException {

		File fichero = new File(ruta);
		String encodedString;

		try {
			byte[] fileContent = Files.readAllBytes(fichero.toPath());
			encodedString = Base64.encodeBase64String(fileContent);
		} catch (Exception e) {
			encodedString = "Sin Portada";
			JOptionPane.showMessageDialog(new JFrame(), "No se ha podido añadir una imagen al libro", "Ruta no valida",
					JOptionPane.ERROR_MESSAGE);
		}

		return encodedString;

	}

	/**
	 * Metodo que almacena una copia de la imagen de al base de datos en un
	 * directorio del proyecto
	 * 
	 * @param imagen Recibe la imagen decodificada
	 * 
	 * @param id     Recibe el id del libro para nombrar la imagen
	 * 
	 * 
	 */
	public void guardarPng(BufferedImage imagen, String id) {
		String nombre = id + ".png";
		File outputfile = new File("images/" + nombre);
		try {
			ImageIO.write(imagen, "png", outputfile);
		} catch (Exception e) {
			// No se puede guardar la imagen
		}
	}

	/**
	 * Metodo que permite mostrar la imagen en la interfaz
	 * 
	 * @param nombreImagen Recibe el nombre de la imagen que se quiere mostrar
	 * 
	 * @return imageIcon Devuelve el icon a mostrar en la interfaz
	 */
	public ImageIcon leerMostrarImagen(String nombreImagen) throws IOException {
		File fichero;
		Image imagen;

		fichero = new File("images/" + nombreImagen);

		try {
			imagen = ImageIO.read(fichero);
		} catch (Exception e) {
			fichero = new File("images/default-placeholder.png");
			imagen = ImageIO.read(fichero);
		}

		// imagen origen
		Image img = new ImageIcon(imagen).getImage();

		// escala imagen
		Image newimg = img.getScaledInstance(230, 280, java.awt.Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(newimg);

//		JOptionPane.showMessageDialog(null, "", "", 
//		JOptionPane.INFORMATION_MESSAGE, imagenIcono);

		return imageIcon;

	}

	/**
	 * Metodo que modifica el libro seleccionado por el usuario
	 * 
	 * @param titulo     Titulo que introduce el usuario
	 * 
	 * @param autor      Nombre del autor/a del libro
	 * 
	 * @param anyNac     Anyo de nacimiento del autor/a
	 * 
	 * @param anyPub     Anyo de publicacion del libro
	 * 
	 * @param editorial  Editorial del libro
	 * 
	 * @param numPag     Numero de paginas del libro
	 * 
	 * @param rutaImagen Ruta al directorio en el que se encuentra la imagen de
	 *                   portada del libro
	 * 
	 * @param id         Id de la imagen a modificar
	 */
	public void modificarLibro(String titulo, String autor, String anyNac, String anyPub, String editorial,
			String numPag, String rutaImagen, int id) {

		int anyNacInt = Integer.parseInt(anyNac);
		int anyPubInt = Integer.parseInt(anyPub);
		int numPagInt = Integer.parseInt(numPag);

		String thumbnail = "Sin portada";
		try {
			thumbnail = imagenABytes(rutaImagen);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(new JFrame(), "No se ha podido añadir una imagen al libro", "Ruta no valida",
					JOptionPane.ERROR_MESSAGE);
		}

		getColeccionLibros().updateOne(Filters.eq("Id", id), new Document("$set", new Document("Titulo", titulo)));
		getColeccionLibros().updateOne(Filters.eq("Id", id), new Document("$set", new Document("Autor", autor)));
		getColeccionLibros().updateOne(Filters.eq("Id", id),
				new Document("$set", new Document("Anyo_nacimiento", anyNacInt)));
		getColeccionLibros().updateOne(Filters.eq("Id", id),
				new Document("$set", new Document("Anyo_publicacion", anyPubInt)));
		getColeccionLibros().updateOne(Filters.eq("Id", id),
				new Document("$set", new Document("Editorial", editorial)));
		getColeccionLibros().updateOne(Filters.eq("Id", id),
				new Document("$set", new Document("Numero_paginas", numPagInt)));
		getColeccionLibros().updateOne(Filters.eq("Id", id),
				new Document("$set", new Document("Thumbnail", thumbnail)));

	}

	/**
	 * Metodo que elimnina un libro de la coleccion
	 * 
	 * @param id Recibe el id del titulo a eliminar
	 */
	public void eliminarLibro(int id) {
		getColeccionLibros().deleteMany(Filters.eq("Id", id));
	}

	/**
	 * Metodo para realizar diferentes consultas a la base de datos
	 * 
	 * @param indexCampo  Indice dentro del comboBox del campo seleccionado
	 * 
	 * @param indexFiltro Indice dentro del comboBox del filtro seleccionado
	 * 
	 * @param valor       Valor de la busqueda que introduce el usuario
	 * 
	 * @return libros Retorna los libros que coinciden con la consulta
	 */
	public ArrayList<String> consultas(int indexCampo, int indexFiltro, String valor) {

		String campo = null;
		int valorInt;
		String idStr = null, anyoNacStr = null, anyoPubStr = null, numPagStr = null;
		int anyoNac;
		ArrayList<String> libros = new ArrayList<String>();
		MongoCursor<Document> cursor = null;

		if (indexCampo == 0)
			campo = "Id";
		if (indexCampo == 1)
			campo = "Titulo";
		if (indexCampo == 2)
			campo = "Autor";
		if (indexCampo == 3)
			campo = "Anyo_nacimiento";
		if (indexCampo == 4)
			campo = "Anyo_publicacion";
		if (indexCampo == 5)
			campo = "Editorial";
		if (indexCampo == 6)
			campo = "Numero_paginas";

		if (campo.equals("Id") || campo.equals("Anyo_nacimiento") || campo.equals("Anyo_publicacion")
				|| campo.equals("Numero_paginas")) {
			valorInt = Integer.parseInt(valor);
			if (indexFiltro == 0)
				cursor = getColeccionLibros().find(Filters.and(Filters.eq(campo, valorInt))).iterator();
			if (indexFiltro == 1)
				cursor = getColeccionLibros().find(Filters.and(Filters.gte(campo, valorInt))).iterator();
			if (indexFiltro == 2)
				cursor = getColeccionLibros().find(Filters.and(Filters.lte(campo, valorInt))).iterator();
		} else {
			if (indexFiltro == 0)
				cursor = getColeccionLibros().find(Filters.and(Filters.eq(campo, valor))).iterator();
			if (indexFiltro == 1)
				cursor = getColeccionLibros().find(Filters.and(Filters.gte(campo, valor))).iterator();
			if (indexFiltro == 2)
				cursor = getColeccionLibros().find(Filters.and(Filters.lte(campo, valor))).iterator();
		}

		while (cursor.hasNext()) {

			JSONObject jsO = new JSONObject(cursor.next().toJson());

			int id = jsO.getInt("Id");
			String titulo = jsO.getString("Titulo");
			String autor = jsO.getString("Autor");
			int anyoPub = jsO.getInt("Anyo_publicacion");
			String editorial = jsO.getString("Editorial");
			int numPag = jsO.getInt("Numero_paginas");

			idStr = String.valueOf(id);
			anyoPubStr = String.valueOf(anyoPub);
			numPagStr = String.valueOf(numPag);

			try {
				anyoNac = jsO.getInt("Anyo_nacimiento");
				anyoNacStr = String.valueOf(anyoNac);
			} catch (Exception e) {
				anyoNacStr = "DESCONOCIDO";
			}

			libros.add("Id: " + idStr + "\n" + "Titulo: " + titulo + " \n" + "Autor: " + autor + "\n"
					+ "Año de nacimiento: " + anyoNacStr + "\n" + "Año de publicación" + anyoPubStr + "\n"
					+ "Editorial: " + editorial + "\n" + "Número de páginas: " + numPagStr + "\n");

		}
		return libros;
	}

	/**
	 * @return coleccionUsuarios Retorna la coleccion de usuarios de la base de
	 *         datos
	 */
	public MongoCollection<Document> getColeccionUsuarios() {
		return coleccionUsuarios;
	}

	/**
	 * @param coleccionUsuarios Recibe la coleccion de usuarios de la base de datos
	 */
	public void setColeccionUsuarios(MongoCollection<Document> coleccionUsuarios) {
		this.coleccionUsuarios = coleccionUsuarios;
	}

	/**
	 * @return coleccionLibros Retorna la coleccion de libros de la base de datos
	 */
	public MongoCollection<Document> getColeccionLibros() {
		return coleccionLibros;
	}

	/**
	 * @param coleccionLibros Recibe la coleccion de libros de la base de datos
	 */
	public void setColeccionLibros(MongoCollection<Document> coleccionLibros) {
		this.coleccionLibros = coleccionLibros;
	}

	/**
	 * @return the access 
	 */
	public boolean isAccess() {
		return access;
	}

	/**
	 * @param access the access to set
	 */
	public void setAccess(boolean access) {
		this.access = access;
	}

}
