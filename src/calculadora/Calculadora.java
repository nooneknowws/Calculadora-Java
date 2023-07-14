
package calculadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Thalyson Bruck Andreatta
 * GRR20206126
 */
public class Calculadora extends JFrame implements ActionListener {
    JFrame frame;
    JTextField display;
    Font texto = new Font("Times New Roman", Font.BOLD,22);
    Font botao = new Font("Times New Roman", Font.BOLD,15);
    
    enum EstadoCalculadora {
    INICIAL, ENTRADA1, OPERADOR, ENTRADA2, CALCULANDO, RESULTADO
    }
    
    private EstadoCalculadora estado;
    private String operador;
    private double num1,num2,resAnt;

    public Calculadora() {
        estado = EstadoCalculadora.INICIAL;
        initComponents();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents() {
       
        frame = new JFrame("Calculadora");
        frame.setSize(420,550);
        frame.setLayout(null);
        
        JPanel Display = new JPanel();
        JPanel Botões = new JPanel(new GridLayout(4, 4, 10, 10));
        Display.setBounds(50,25,300,50);
        Botões.setBounds(50,100,300,400);
        Botões.setFont(botao);
        display = new JTextField(15);
        display.setEditable(false);
        display.setFont(texto);
        Display.add(display);
        

        String[] buttonLabels = {
                "1", "2", "3", "+",
                "4", "5", "6", "-",
                "7", "8", "9", "*",
                "0", "←", "=", "/"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(this);
            Botões.add(button);
        }
        frame.add(Display);
        frame.add(Botões);
        frame.setVisible(true);
    }

    @Override
public void actionPerformed(ActionEvent e) {
    String ação = e.getActionCommand();

    switch (estado) {
        case INICIAL:
            if (Character.isDigit(ação.charAt(0))) {
                display.setText(ação);
                estado = EstadoCalculadora.ENTRADA1;
            }
            break;

        case ENTRADA1:
            if (Character.isDigit(ação.charAt(0))) {
                display.setText(display.getText() + ação);
            } else if (ação.equals("←")) {
                display.setText("");
                estado = EstadoCalculadora.INICIAL;
            } else if (isOperator(ação)) {
                operador = ação;
                num1 = Double.parseDouble(display.getText());
                display.setText(display.getText() + " " + operador + " ");
                estado = EstadoCalculadora.OPERADOR;
            }
            break;

        case OPERADOR:
        case CALCULANDO:
            if (Character.isDigit(ação.charAt(0))) {
                display.setText(ação);
                estado = EstadoCalculadora.ENTRADA2;
            } else if (ação.equals("←")) {
                display.setText("");
                estado = EstadoCalculadora.INICIAL;
            }
            break;

        case ENTRADA2:
            if (Character.isDigit(ação.charAt(0))) {
                display.setText(display.getText() + ação);
            } else if (ação.equals("←")) {
                display.setText("");
                estado = EstadoCalculadora.INICIAL;
            } else if (ação.equals("=")) {
                num2 = Double.parseDouble(display.getText());
                double resultado = calcular(num1, num2, operador);
                display.setText(Double.toString(resultado));
                resAnt = resultado;
                estado = EstadoCalculadora.RESULTADO;
            }
            break;

        case RESULTADO:
            if (Character.isDigit(ação.charAt(0))) {
                display.setText(ação);
                estado = EstadoCalculadora.ENTRADA1;
            } else if (ação.equals("←")) {
                display.setText("");
                estado = EstadoCalculadora.INICIAL;
            } else if (isOperator(ação)) {
                operador = ação;
                num1 = resAnt;
                display.setText(resAnt + " " + operador + " ");
                estado = EstadoCalculadora.OPERADOR;
            }
            break;
    }
}

private boolean isOperator(String str) {
    return "+-*/".contains(str);
}

private double calcular(double num1, double num2, String operador) {
    return switch (operador) {
        case "+" -> num1 + num2;
        case "-" -> num1 - num2;
        case "*" -> num1 * num2;
        case "/" -> num1 / num2;
        default -> 0;
    };
}

public static void main(String[] args) {
    Calculadora calc = new Calculadora();
}
}