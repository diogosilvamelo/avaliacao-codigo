package componente;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MeuJButtonCadastro extends JButton implements MouseListener {

    private Icon iconeMenor;
    private Icon iconeMaior;
    public boolean status;

    public MeuJButtonCadastro(String texto, Icon iconeMenor, Icon iconeMaior) {
        setIcon(iconeMenor);
        this.iconeMenor = iconeMenor;
        this.iconeMaior = iconeMaior;
        setToolTipText(texto);
        setBorder(null);
        setFocusable(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        addMouseListener(this);
    }

    public MeuJButtonCadastro(String titulo, ImageIcon iconeMenor, String texto) {
        setIcon(iconeMenor);
        setText(titulo.toUpperCase());
        setToolTipText(texto);
        setFont(new Font("Times New Roman", Font.BOLD, 12));
        setFocusable(true);
    }
    
    public MeuJButtonCadastro(String texto, ImageIcon iconeMenor, ImageIcon iconeMaior, boolean status) {
        setIcon(iconeMenor);
        this.iconeMenor = iconeMenor;
        this.iconeMaior = iconeMaior;
        this.status=status;
        setToolTipText(texto);
        setBorder(null);
        setFocusable(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        setIcon(iconeMenor);
    }

    @Override
    public void mousePressed(MouseEvent me) {
        if(status==true){
            setIcon(iconeMaior);
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        if (status == true) {
            setIcon(iconeMaior);
        }
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        if (status==true) {
            setIcon(iconeMaior);
        }   
    }

    @Override
    public void mouseExited(MouseEvent me) {
        setIcon(iconeMenor);
    }
    
    public void mudaStatus(boolean status){
        this.status=status;
    }

}
