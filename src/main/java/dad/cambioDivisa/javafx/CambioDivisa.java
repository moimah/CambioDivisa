package dad.cambioDivisa.javafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CambioDivisa extends Application {

	private TextField txtDivisaIn;
	private TextField txtDivisaOut;
	private ComboBox cmbIn;
	private ComboBox cmbOut;
	private Button btnCambio;

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Inicializar componentes y definir sus propìedades

		txtDivisaIn = new TextField();
		txtDivisaIn.setPromptText("0");
		txtDivisaIn.setMaxWidth(50);

		txtDivisaOut = new TextField();
		txtDivisaOut.setPromptText("0");
		txtDivisaOut.setEditable(false); // Campo de texto no editable
		txtDivisaOut.setMaxWidth(50);

		cmbIn = new ComboBox();
		cmbIn.getItems().addAll("EUR", "GBP", "JPY", "USD");
		cmbIn.setMinWidth(50);
		cmbIn.setPromptText("Moneda");

		cmbOut = new ComboBox();
		cmbOut.getItems().addAll("EUR", "GBP", "JPY", "USD");
		cmbOut.setMinWidth(50);
		cmbOut.setPromptText("Moneda");

		btnCambio = new Button();
		btnCambio.setMinWidth(40);
		btnCambio.setText("Cambio");
		btnCambio.setDefaultButton(true);
		btnCambio.setOnAction(e -> onCambioButtonAction(e)); // Llama al método onLoginButtonAction

		// Definir elementos del layout

		HBox hboxDivisaIn = new HBox(5, txtDivisaIn, cmbIn);
		hboxDivisaIn.setAlignment(Pos.CENTER);
		hboxDivisaIn.setStyle("-fx-padding: 10px 20px");

		HBox hboxDivisaOut = new HBox(5, txtDivisaOut, cmbOut);
		hboxDivisaOut.setAlignment(Pos.CENTER);
		hboxDivisaOut.setStyle("-fx-padding: 10px 20px");

		HBox hboxCambio = new HBox(5, btnCambio);
		hboxCambio.setAlignment(Pos.CENTER);
		hboxCambio.setStyle("-fx-padding: 20px 20px");

		// LayOutGeneral

		VBox root = new VBox(hboxDivisaIn, hboxDivisaOut, hboxCambio);
		root.setAlignment(Pos.CENTER);

		// Definir Scena y atributos

		Scene scene = new Scene(root, 320, 200);

		// Definir titulo, seleccionar escena, y mostrar el Stage

		primaryStage.setTitle("Cambio de divisa");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private void onCambioButtonAction(ActionEvent e) {

		cambioMoneda(); //Llama al método cambio moneda que es el encargado de la ejecución del programa

	}

	public void cambioMoneda() {

//		try {

		// Inicialización de los objetos Divisa; Su valor con respecto al euro

		Divisa euro = new Divisa("EUR", 1.0);
		Divisa libra = new Divisa("GBP", 0.89);
		Divisa yen = new Divisa("JPY", 117.76);
		Divisa dolar = new Divisa("USD", 1.09);

		Divisa origen = null; // Almacena el objeto divisa de destino
		Divisa destino = null; // Almacena el objeto divisa de origen

		String monedaEntrada = null; // Almacena en String el valor al cambio de la moneda de entrada seleccionada en el combo
										
		try { // Captura la excepción en caso de que no obtenga valor
			monedaEntrada = (String) cmbIn.getValue();

			// Condicional que evalua los posibles casos de moneda de entrada asignandole el tipo de moneda al objeto origen
			

			switch (monedaEntrada) {
			case "EUR":
				origen = euro;
				break;
			case "GBP":
				origen = libra;
				break;
			case "JPY":
				origen = yen;
				break;
			case "USD":
				origen = dolar;
				break;

			}

			

			String monedaSalida = null; // Almacena en String el valor al cambio de la moneda de salida seleccionada en el combo
										

			try { //Captura la exepción en caso de que no obtenga valor 

				monedaSalida = (String) cmbOut.getValue();

				// Condicional que evalua los posibles casos de moneda de salida asignandole el tipo de moneda al objeto destino
				 

				switch (monedaSalida) {
				case "EUR":
					destino = euro;
					break;
				case "GBP":
					destino = libra;
					break;
				case "JPY":
					destino = yen;
					break;
				case "USD":
					destino = dolar;
					break;

				}

				double origenDouble = origen.getTasa(); // Pasa a un double el valor de cambio del objeto origen "tipo divisa"
														
				double destinoDouble = destino.getTasa(); // Pasa a un double el valor de cambio del objeto destino "tipo divisa"
															
				double resultado = 0; // Variable double que almacenará el resultado
				String resultadoCadena = null; // Variable String que almacenará el resultado

				double valorEntrada = 0; // Obtiene un double parseado del texto de entrada 

				try { //Captura el error en caso de que no exista valor de entrada

					valorEntrada = Double.parseDouble(this.txtDivisaIn.getText());

					String origenText = origen.getNombre(); // Obtiene el tipo de moneda de origen en String del objeto origen
															
					String destinoText = destino.getNombre(); // Obiene el tipo de moneda de destino en String del  objeto destino
																

					// Condicional que evalua las diferentes opciones de entrada(Origen) aplicandole
					// la formula de salida para cada caso

					switch (origenText) {

					case "EUR":

						resultado = (valorEntrada * origenDouble) / destinoDouble;
						resultadoCadena = String.valueOf(resultado);
						this.txtDivisaOut.setText(resultadoCadena);

					case "GBP":

						resultado = (valorEntrada * origenDouble) * destinoDouble;
						resultadoCadena = String.valueOf(resultado);
						this.txtDivisaOut.setText(resultadoCadena);

					case "JPY":

						resultado = (valorEntrada * destinoDouble) / origenDouble;
						resultadoCadena = String.valueOf(resultado);
						this.txtDivisaOut.setText(resultadoCadena);

					case "USD":

						resultado = (valorEntrada * destinoDouble) / origenDouble;
						resultadoCadena = String.valueOf(resultado);
						this.txtDivisaOut.setText(resultadoCadena);

					}

				} catch (Exception e) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Cambio divisa");
					alert.setHeaderText("Problema");
					alert.setContentText("Introduce un valor de entrada numerico");
					alert.showAndWait();
				}

			} catch (Exception e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Cambio divisa");
				alert.setHeaderText("Problema");
				alert.setContentText("No ha seleccionado divisa de salida");
				alert.showAndWait();
			}

			

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Cambio divisa");
			alert.setHeaderText("Problema");
			alert.setContentText("No ha seleccionado divisa de entrada");
			alert.showAndWait();
		}

	

	}

	public static void main(String[] args) {

		launch(args);

	}

}
