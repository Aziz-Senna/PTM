package PTM;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.Border;

class RoundBorder implements Border {
    private int r;
    RoundBorder(int r) {
        this.r = r;
    }
    public Insets getBorderInsets(Component c) {
        return new Insets(this.r+1, this.r+1, this.r+2, this.r);
    }
    public boolean isBorderOpaque() {
        return true;
    }
    public void paintBorder(Component c, Graphics g, int x, int y, 
    int width, int height) {
        g.setColor(Color.BLACK); // couleur de la bordure
        g.drawRoundRect(x, y, width-1, height-1, r, r);
    }
}

