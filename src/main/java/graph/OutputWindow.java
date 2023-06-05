package graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OutputWindow extends JFrame implements ActionListener {
    static JFrame frame;
    static JLabel label;
    static JTextArea textArea;
    private static OutputWindow outputWindow;

    public static void init(String result) {
        outputWindow = new OutputWindow();
        frame = new JFrame("Output");
        label = new JLabel(result);
        textArea = new JTextArea(result);
        textArea.setEditable(false);
        JPanel panel = new JPanel();
        JScrollPane sp = new JScrollPane(textArea);
        panel.add(sp);
        frame.getContentPane().add(sp);
        frame.setJMenuBar(createMenuBar());
        frame.setSize(400, 600);
        frame.setVisible(true);
    }

    private static JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menuBar.add(menu);
        JMenuItem menuItem1 = new JMenuItem(" Save...   ");
        menuItem1.addActionListener(outputWindow);
        menuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        menu.add(menuItem1);
        return menuBar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FileDialog chooser = new FileDialog(frame, "Use a .txt or other", FileDialog.SAVE);
        chooser.setVisible(true);
        String filename = chooser.getFile();
        if (filename != null) {
            save(chooser.getDirectory() + File.separator + chooser.getFile());
        }
    }
    public static void save(String filename) {
        validateNotNull(filename, "filename");
        if (filename.length() == 0) throw new IllegalArgumentException("argument to save() is the empty string");
        File file = new File(filename);
        String suffix = filename.substring(filename.lastIndexOf('.') + 1);
        if (!filename.contains(".")) suffix = "";

        try {
            FileWriter fw = new FileWriter(file);
            fw.write(textArea.getText());
            fw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void validateNotNull(Object x, String name) {
        if (x == null) throw new IllegalArgumentException(name + " is null");
    }
}
