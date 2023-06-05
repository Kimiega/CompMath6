package graph;

import linal.Diffurchik;
import linal.Diffurchiki;
import linal.SuperMegaSolver;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Vector;

public class InputWindow extends JFrame implements ActionListener, FocusListener {

    static JLabel diffChoiceBoxLabel;
    static JComboBox<Diffurchik> diffChoiceBox;

    static JLabel y0Label;
    static JLabel x0Label;
    static JLabel xnLabel;
    static JTextField y0Field;
    static JTextField x0Field;
    static JTextField xnField;

    static JLabel hLabel;
    static JLabel epsLabel;
    static JTextField hField;
    static JTextField epsField;

    static JFrame frame;
    static JPanel mainGrid;
    static JButton submit;

    static Thread solver = null;
    InputWindow() {}

    public static void init()
    {
        //start
        InputWindow inputWindow = new InputWindow();
        frame = new JFrame("Diffurchik solver");

        //grid
        JPanel methodGrid = new JPanel(new GridLayout(2, 1));
        JPanel initialGrid = new JPanel(new GridLayout(2, 3));
        JPanel additionalGrid = new JPanel(new GridLayout(2, 2));
        JPanel buttonGrid = new JPanel(new GridLayout(1, 1));
        mainGrid = new JPanel(new GridLayout(4,1));
        mainGrid.add(methodGrid);
        mainGrid.add(initialGrid);
        mainGrid.add(additionalGrid);
        mainGrid.add(buttonGrid);

        //methodChoice
        diffChoiceBoxLabel = new JLabel("Choose the method", SwingConstants.CENTER);
        diffChoiceBox = new JComboBox<>(Diffurchiki.diffurchikiList.toArray(new Diffurchik[0]));
        methodGrid.add(diffChoiceBoxLabel);
        methodGrid.add(diffChoiceBox);

        //initial params
        y0Label = new JLabel("y0", SwingConstants.CENTER);
        x0Label = new JLabel("x0", SwingConstants.CENTER);
        xnLabel = new JLabel("xn", SwingConstants.CENTER);
        y0Field = new JTextField(8);
        x0Field = new JTextField(8);
        xnField = new JTextField(8);
        initialGrid.add(y0Label);
        initialGrid.add(x0Label);
        initialGrid.add(xnLabel);
        initialGrid.add(y0Field);
        initialGrid.add(x0Field);
        initialGrid.add(xnField);

        //additional params
        hLabel = new JLabel("h", SwingConstants.CENTER);
        epsLabel = new JLabel("eps", SwingConstants.CENTER);
        hField = new JTextField(12);
        epsField = new JTextField(12);
        additionalGrid.add(hLabel);
        additionalGrid.add(epsLabel);
        additionalGrid.add(hField);
        additionalGrid.add(epsField);

        //button
        submit = new JButton("submit");
        buttonGrid.add(submit);

        //Listeners
        diffChoiceBox.addActionListener(inputWindow);
        y0Field.addFocusListener(inputWindow);
        x0Field.addFocusListener(inputWindow);
        xnField.addFocusListener(inputWindow);
        hField.addFocusListener(inputWindow);
        epsField.addFocusListener(inputWindow);
        submit.addActionListener(inputWindow);

        JPanel p = new JPanel();

        // add panel to frame
        p.add(mainGrid);
        frame.add(p);
        // set the size of frame
        frame.setSize(350, 600);
        frame.setAlwaysOnTop(true);
        frame.setVisible(true);
    }

    // if the button is pressed
    public void actionPerformed(ActionEvent e)
    {
        clearColors();
        String s = e.getActionCommand();
        if (s.equals(submit.getText())) {
            boolean flag = true;
            Diffurchik diffurchik = null;
            if (!(diffChoiceBox.getSelectedItem() instanceof Diffurchik)) {
                diffChoiceBox.setBackground(Color.RED);
                flag = false;
            }
            else {
                diffurchik = (Diffurchik) diffChoiceBox.getSelectedItem();
            }
            Double y0 = checkTextFieldIsNumber(y0Field);
            Double x0 = checkTextFieldIsNumber(x0Field);
            Double xn = checkTextFieldIsNumber(xnField);
            Double h = checkTextFieldIsNumber(hField);
            Double eps = checkTextFieldIsNumber(epsField);

            if (y0 == null) {
                y0Field.setBackground(Color.RED);
                flag = false;
            }
            if (x0 == null) {
                x0Field.setBackground(Color.RED);
                flag = false;
            }
            if (xn == null) {
                xnField.setBackground(Color.RED);
                flag = false;
            }
            if (h == null) {
                hField.setBackground(Color.RED);
                flag = false;
            }
            if (eps == null) {
                epsField.setBackground(Color.RED);
                flag = false;
            }
            if (flag && x0 >= xn) {
                x0Field.setBackground(Color.RED);
                xnField.setBackground(Color.RED);
                flag = false;
            }
            if (flag && h <= 0) {
                hField.setBackground(Color.RED);
                flag = false;
            }
            if (flag && eps <= 0) {
                epsField.setBackground(Color.RED);
                flag = false;
            }
            if (flag) {
                if (solver != null)
                    solver.stop();
                OutputWindow.closeWindow();
                Diffurchik finalDiffurchik = diffurchik;
                solver = new Thread(() -> {
                    String result = SuperMegaSolver.solveAllProblems(finalDiffurchik, y0, x0, xn, h, eps);
                    OutputWindow.init(result);
                }
                );
                solver.start();
            }
        }
        if (s.equals(diffChoiceBox.toString())) {
            diffChoiceBox.setBackground(Color.WHITE);
        }
    }
    private static void clearColors() {
        diffChoiceBox.setBackground(Color.WHITE);
        y0Field.setBackground(Color.WHITE);
        x0Field.setBackground(Color.WHITE);
        xnField.setBackground(Color.WHITE);
        hField.setBackground(Color.WHITE);
        epsField.setBackground(Color.WHITE);
    }

    @Override
    public void focusGained(FocusEvent e) {
        e.getOppositeComponent().setBackground(Color.WHITE);
    }

    @Override
    public void focusLost(FocusEvent e) {
    }

    private Double checkTextFieldIsNumber(JTextField field) {
        String x = field.getText();
        double parsed;
        try {
            parsed = Double.parseDouble(x.replace(",", "."));
        } catch (Exception ex) {
            field.setBackground(Color.RED);
            return null;
        }
        return parsed;
    }
}