package componente;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MeuJPanel extends JPanel {

    private Image image;

    public MeuJPanel(URL url) {
        setImage(url);
        setOpaque(false);
    }

    public void setImage(URL url) {
        image = new ImageIcon(url).getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, this);
        super.paintComponent(g);
    }

    @Override
    public int getWidth() {
        return image.getWidth(this);
    }

    @Override
    public int getHeight() {
        return image.getHeight(this);
    }
}
