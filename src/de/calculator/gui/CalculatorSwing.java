package de.calculator.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Diese Klasse implementiert einen einfachen Taschenrechner mit grafischer
 * Benutzeroberfläche.
 */
public class CalculatorSwing {

	private JFrame frame;
	private JTextField textField;

	private double result = 0; // Initialwert für das Ergebnis
	private String expression = ""; // Ausdruckszeichenkette für die gesamte Rechnung

	/**
	 * Die Hauptmethode, die die Anwendung startet.
	 *
	 * @param args Die Kommandozeilenargumente (nicht verwendet).
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalculatorSwing window = new CalculatorSwing();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Erstellt ein neues CalculatorSwing-Objekt und initialisiert die
	 * Benutzeroberfläche.
	 */
	public CalculatorSwing() {
		initialize();
	}

	/**
	 * Initialisiert die Benutzeroberfläche des Taschenrechners und erstellt die
	 * GUI-Komponenten. Dabei werden das Hauptfenster, Textfeld, Buttons und deren
	 * Positionen festgelegt. Das Fenster ist nicht skalierbar und trägt den Titel
	 * "Taschenrechner".
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Taschenrechner");
		frame.setBounds(100, 100, 304, 422);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setFont(new Font("Tahoma", Font.BOLD, 17));
		textField.setBounds(12, 11, 263, 58);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		createButton("BE", 12, 80);
		createButton("CE", 78, 80);
		createButton("00", 144, 80);
		createButton("+", 210, 80);

		createButton("7", 12, 139);
		createButton("8", 78, 139);
		createButton("9", 144, 139);
		createButton("-", 210, 139);

		createButton("4", 12, 198);
		createButton("5", 78, 198);
		createButton("6", 144, 198);
		createButton("*", 210, 198);

		createButton("1", 12, 257);
		createButton("2", 78, 257);
		createButton("3", 144, 257);
		createButton("/", 210, 257);

		createButton("%", 12, 316);
		createButton("0", 78, 316);
		createButton(".", 144, 316);
		createButton("=", 210, 316);
	}

	/**
	 * Erstellt einen Button mit dem angegebenen Label an den gegebenen Koordinaten.
	 *
	 * @param label Das Label des Buttons.
	 * @param x     Die X-Koordinate.
	 * @param y     Die Y-Koordinate.
	 */
	private void createButton(String label, int x, int y) {
		JButton button = new JButton(label);
		button.setFont(new Font("Tahoma", Font.BOLD, 18));
		button.setBounds(x, y, 65, 57);
		frame.getContentPane().add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleButtonClick(label);
			}
		});
	}

	/**
	 * Verarbeitet den Klick auf einen Button.
	 *
	 * @param buttonLabel Das Label des geklickten Buttons.
	 */
	private void handleButtonClick(String buttonLabel) {
		if (buttonLabel.matches("[0-9]")) {
			String number = textField.getText() + buttonLabel;
			textField.setText(number);
		} else if (buttonLabel.equals(".")) {
			if (!textField.getText().contains(".")) {
				String number = textField.getText() + ".";
				textField.setText(number);
			}
		} else if (buttonLabel.equals("CE")) {
			textField.setText("");
		} else if (buttonLabel.equals("BE")) {
			if (textField.getText().length() > 0) {
				textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
			}
		} else if (buttonLabel.equals("+") || buttonLabel.equals("-") || buttonLabel.equals("*")
				|| buttonLabel.equals("/") || buttonLabel.equals("%")) {
			handleOperationClick(buttonLabel);
		} else if (buttonLabel.equals("=")) {
			calculateResult();
		}
	}

	/**
	 * Verarbeitet den Klick auf einen Operator-Button (+, -, *, /, %). Fügt den
	 * ausgewählten Operator zur Ausdruckszeichenkette hinzu und setzt das Textfeld
	 * zurück.
	 *
	 * @param op Der ausgewählte Operator-Button.
	 */
	private void handleOperationClick(String op) {
		expression = textField.getText() + " " + op + " ";
		textField.setText("");
	}

	/**
	 * Berechnet das Ergebnis der eingegebenen Rechnung.
	 */
	private void calculateResult() {
		if (!expression.isEmpty()) {
			expression += textField.getText();
			String[] tokens = expression.split(" ");
			if (tokens.length == 3) {
				double firstOperand = Double.parseDouble(tokens[0].replace(",", "."));
				double secondOperand = Double.parseDouble(tokens[2].replace(",", "."));
				String operator = tokens[1];

				switch (operator) {
				case "+":
					result = firstOperand + secondOperand;
					break;
				case "-":
					result = firstOperand - secondOperand;
					break;
				case "*":
					result = firstOperand * secondOperand;
					break;
				case "/":
					if (secondOperand == 0) {
						textField.setText("Fehler: Division durch Null");
						return;
					} else {
						result = firstOperand / secondOperand;
					}
					break;
				case "%":
					if (secondOperand == 0) {
						textField.setText("Fehler: Division durch Null");
						return;
					} else {
						result = firstOperand % secondOperand;
					}
					break;
				}
				String answer = String.format("%.2f", result);
				textField.setText(answer);
				expression = "";
			}
		}
	}
}
