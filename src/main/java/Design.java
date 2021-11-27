import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Design extends JPanel {

    private Logger log = Logger.getLogger("logger");
    private Icon icon;
    private ArrayList<JComponent> elements = new ArrayList<>();

    public Design() {
    }

    public Design(int x, int y, int w, int h, Color color, MouseListener m, MouseMotionListener mm, int layout) {
        setBounds(x, y, w, h);
        setBackground(color);
        addMouseListener(m);
        addMouseMotionListener(mm);
        if (layout == 0) {
            setLayout(null);
        }
    }
    public Design(int x, int y, int w, int h, Color color, int layout) {
        setBounds(x, y, w, h);
        setBackground(color);
        if (layout == 0) {
            setLayout(null);
        }
    }

    public void addLabel(String file, int x, int y, int w, int h, MouseListener m, MouseMotionListener mm) {
        JLabel label = new JLabel();
        icon = new ImageIcon(file);
        label.setIcon(icon);
        label.setBounds(x, y, w, h);
        label.setOpaque(true);
        label.addMouseListener(m);
        label.addMouseMotionListener(mm);
        add(label);
    }

    public void addButtonOrTextField(JComponent object, int x, int y, int w, int h, Color color,Color letra, MouseListener m) {
        object.setBounds(x, y, w, h);
        object.setBackground(color);
        object.setFont(new Font("Montserrat", Font.BOLD, 18));
        object.setForeground(letra);
        object.addMouseListener(m);
        elements.add(object);
        add(object);
    }

    public void removeBorder(JComponent object) {
        object.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    }


    public ArrayList<JComponent> getElements() {
        return elements;
    }

    public void setElements(ArrayList<JComponent> elements) {
        this.elements = elements;
    }
}
