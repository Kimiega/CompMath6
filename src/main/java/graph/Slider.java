package graph;

// java Program to create a slider with min and
// max value and major and minor ticks painted.

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Slider extends JFrame implements ChangeListener {

    // frame
    static JFrame f;

    // slider
    static JSlider b;

    // label
    static JLabel l;

    // main class
    public static void init()
    {
        // create a new frame
        f = new JFrame("frame");

        // create a object
        Slider s = new Slider();

        // create label
        l = new JLabel();

        // create a panel
        JPanel p = new JPanel();

        // create a slider
        b = new JSlider(0, 200, 120);

        // paint the ticks and tracks
        b.setPaintTrack(true);
        b.setPaintTicks(true);
        b.setPaintLabels(true);

        // set spacing
        b.setMajorTickSpacing(50);
        b.setMinorTickSpacing(5);

        // setChangeListener
        b.addChangeListener(s);

        // add slider to panel
        p.add(b);
        p.add(l);

        f.add(p);

        // set the text of label
        l.setText("value of Slider is =" + b.getValue());

        // set the size of frame
        f.setSize(300, 300);

        f.show();
    }

    // if JSlider value is changed
    public void stateChanged(ChangeEvent e)
    {
        l.setText("value of Slider is =" + b.getValue());
    }
}
