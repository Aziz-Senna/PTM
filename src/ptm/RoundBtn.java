package PTM;

import PTM.Main.Clicklistener;
import java.awt.*;
import javax.swing.*;

public class RoundBtn extends JButton {

    private int radius;

    public RoundBtn(String text, int radius, Clicklistener click, Font f, int x, int y, int height, int width) {
        super(text);
        this.radius = radius;
        setContentAreaFilled(false); // on désactive le fond par défaut
        setFocusPainted(false);
        setBorder(new RoundBorder(radius)); // ta bordure
        addActionListener(click);
        setBounds(x, y, height, width);
        setFont(f);
        setFocusable(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // couleur du fond (utilise la couleur définie avec setBackground)
        if (getModel().isArmed()) {
            g2.setColor(getBackground().darker());
        } else {
            g2.setColor(getBackground());
        }

        // dessiner le fond arrondi
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        g2.dispose();
        super.paintComponent(g);
    }
}

