package componente;

import java.awt.Color;
import java.awt.event.FocusEvent;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class MeuCampoGenerico extends MeuCampoFormatado {

    public MeuCampoGenerico(boolean obrigatorio, String dica, int tamanho, int maxChar) {
        super(dica, obrigatorio, tamanho, maxChar);
        addCaretListener(this);

        setDocument(new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a)
                    throws BadLocationException {
                super.insertString(offs, str.toUpperCase(), a);
            }
        });

        addFocusListener(this);
        if (eObrigatorio() == true) {
            
        }
    }

    public MeuCampoGenerico(boolean obrigatorio, String dica, int tamanho) {
        super(dica, obrigatorio, tamanho);
        addCaretListener(this);

        setDocument(new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a)
                    throws BadLocationException {
                super.insertString(offs, str.toUpperCase(), a);
            }
        });

        addFocusListener(this);
        if (eObrigatorio() == true) {
            
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        setBackground(new Color(255, 255, 255));
        //     setBackground(new Color(255, 255, 255));
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (eObrigatorio()) {
            
        } else {
            setBackground(Color.WHITE);
        }

    }
}
