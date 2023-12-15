package componente;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextArea;

public class MeuCampoTexto extends JTextArea implements FocusListener, MeuComponente {

    private boolean obrigatorio;
    private String dica;

    public MeuCampoTexto(boolean obrigatorio, String dica, int largura, int altura) {
        this.obrigatorio = obrigatorio;
        this.dica = dica;
        setPreferredSize(new Dimension(largura, altura));
        setLineWrap(true);
        setWrapStyleWord(true);
        addFocusListener(this);

        if (eObrigatorio()) {
            
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (eObrigatorio()) {
            
        } else {
        }
    }

    @Override
    public void limpar() {
        setText("");
    }

    @Override
    public boolean eValido() {
        return true;
    }

    @Override
    public boolean eObrigatorio() {
        return obrigatorio;
    }

    @Override
    public boolean eVazio() {
        return getText().trim().isEmpty();
    }

    @Override
    public void setValor(Object valor) {
        setText("" + valor);
    }

    @Override
    public Object getValor() {
        return "" + getText();
    }

    @Override
    public String getDica() {
        return dica;
    }
}
