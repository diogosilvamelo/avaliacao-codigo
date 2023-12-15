package util;

import componente.MeuJPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import net.miginfocom.swing.MigLayout;
import static tela.TelaSistema.jdp;

public final class Splash {

    public JWindow janela = new JWindow();
    ImageIcon image;
    JLabel jlabel;
    private ImageIcon iconeprincipal = new ImageIcon(getClass().getResource(""));
    boolean sucesso = true;
    URL imagemLoading = getClass().getResource("/imagem/loading.png");
    MeuJPanel painelImagem = new MeuJPanel(imagemLoading);
    JLabel jb = new JLabel("");

    public Splash() {

        janela.setIconImage(iconeprincipal.getImage());
        janela.setLayout(new MigLayout());
        janela.add(painelImagem);
        painelImagem.setPreferredSize(new Dimension(600, 114));
        painelImagem.add(jb, BorderLayout.CENTER);
        janela.pack();
        janela.setVisible(true);
        janela.setLocationRelativeTo(null);
        janela.setAlwaysOnTop(true);
    }

    public void arquivoJL(String arquivo) {
        jb.setText(arquivo);
    }

    private class TransparentPanel extends JPanel {

        {
            setOpaque(false);
        }

        public void paintComponent(Graphics g) {
            g.setColor(new Color(0, 0, 0, 0));
        }
    }

    public static void centraliza(JDialog janela) {
        int larguraDesk = jdp.getWidth();
        int alturaDesk = jdp.getHeight();
        int larguraIFrame = janela.getWidth();
        int alturaIFrame = janela.getHeight();
        janela.setLocation(larguraDesk / 2 - larguraIFrame / 3, alturaDesk / 2 - alturaIFrame / 5);
    }

    public void fechar() {
        janela.dispose();
    }
}
