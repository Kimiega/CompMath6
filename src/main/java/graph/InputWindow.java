package graph;

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

public class InputWindow extends JFrame implements ActionListener, ChangeListener, FocusListener {
    private static final int minAmountDots = 8;
    private static final int maxAmountDots = 12;
    static JTextField[] textX;
    static JTextField[] textY;
    static JTextField textField;

    static JFrame frame;

    static JButton submit;

    static JButton fromFile;

    static JButton drawPerfect;

    static JSlider slider;

    // default constructor
    InputWindow() {}

    public static void init()
    {
        InputWindow inputWindow = new InputWindow();

        frame = new JFrame("Input dots");
        submit = new JButton("submit");
        fromFile = new JButton("fromFile");
        drawPerfect = new JButton("drawPerfect");

        JPanel firstGrid = new JPanel(new GridLayout(2, 1));
        JPanel gridDots = new JPanel(new GridLayout(maxAmountDots + 1, 3));
        JPanel lastGrid = new JPanel(new GridLayout(1, 3));

        //slider
        slider = new JSlider(minAmountDots, maxAmountDots, maxAmountDots);
        slider.setPaintTrack(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(1);
        slider.setMinorTickSpacing(1);

        //input dots
        textX = new JTextField[maxAmountDots];
        textY = new JTextField[maxAmountDots];
        for (int i = 0; i < maxAmountDots; ++i) {
            textX[i] = new JTextField(8);
            textX[i].addFocusListener(inputWindow);
            textY[i] = new JTextField(8);
            textY[i].addFocusListener(inputWindow);

        }

        slider.addChangeListener(inputWindow);
        submit.addActionListener(inputWindow);
        fromFile.addActionListener(inputWindow);
        drawPerfect.addActionListener(inputWindow);

        textField = new JTextField(16);

        JPanel p = new JPanel();

        firstGrid.add(new JLabel("Amount of dots", SwingConstants.CENTER));
        firstGrid.add(slider);

        gridDots.add(new JLabel("", SwingConstants.CENTER));
        gridDots.add(new JLabel("X", SwingConstants.CENTER));
        gridDots.add(new JLabel("Y", SwingConstants.CENTER));
        for (int i = 0; i < maxAmountDots; ++i) {
            gridDots.add(new JLabel(Integer.toString(i+1), SwingConstants.CENTER));
            gridDots.add(textX[i]);
            gridDots.add(textY[i]);
        }

        lastGrid.add(submit);
        lastGrid.add(fromFile);
        lastGrid.add(drawPerfect);
        // add panel to frame
        p.add(firstGrid);
        p.add(gridDots);
        p.add(lastGrid);
        frame.add(p);
        // set the size of frame
        frame.setSize(350, 600);

        frame.setVisible(true);
    }

    // if the button is pressed
    public void actionPerformed(ActionEvent e)
    {
        clearColors();
        String s = e.getActionCommand();
        if (s.equals(submit.getText())) {
            Double[][] dots = new Double[slider.getValue()][2];
            boolean flag = true;
            for (int i = 0; i < slider.getValue(); ++i) {
                JTextField field = textX[i];
                String x = field.getText();
                double parsedX = 0d;
                try {
                    parsedX = Double.parseDouble(x.replace(",", "."));
                } catch (Exception ex) {
                    field.setBackground(Color.RED);
                    flag = false;
                }
                if (flag)
                    dots[i][0] = parsedX;
                field = textY[i];
                x = field.getText();
                double parsedY = 0d;
                try {
                    parsedY = Double.parseDouble(x.replace(",", "."));
                } catch (Exception ex) {
                    field.setBackground(Color.RED);
                    flag = false;
                }
                if (flag)
                    dots[i][1] = parsedY;
            }
            if (flag) {
//                String result = SuperMegaSolver.solveAllProblems(dots);
//                OutputWindow.init(result);
            }
        }
        if (s.equals(fromFile.getText())) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                clearText();
                InputStream inputStream = null;
                try {
                    inputStream = new FileInputStream(selectedFile);
                    Scanner scanner = new Scanner(inputStream);
                    int i = 0;
                    while (scanner.hasNext() && i < maxAmountDots) {
                        textX[i].setText(scanner.next());
                        textY[i].setText(scanner.next());
                        i++;
                    }
                    slider.setValue(i);
                }
                catch (Exception ignored) {}
                finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException ignored) {}
                    }
                }
            }
        }
    }
    public void stateChanged(ChangeEvent e)
    {
        clearColors();
        for (int i = 0; i < maxAmountDots; ++i) {
            if (i < slider.getValue()) {
                textX[i].setEnabled(true);
                textY[i].setEnabled(true);
            }
            else {

                textX[i].setEnabled(false);
                textY[i].setEnabled(false);
            }
        }
    }
    private static void clearColors() {
        for (int i = 0; i < maxAmountDots; ++i) {
            textX[i].setBackground(Color.WHITE);
            textY[i].setBackground(Color.WHITE);
        }
    }
    private static void clearText() {
        for (int i = 0; i < maxAmountDots; ++i) {
            textX[i].setText("");
            textY[i].setText("");
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        clearColors();
    }

    @Override
    public void focusLost(FocusEvent e) {
    }
}