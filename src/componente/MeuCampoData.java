package componente;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.event.CaretEvent;
import javax.swing.text.MaskFormatter;

public class MeuCampoData extends MeuCampoFormatado {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public MeuCampoData(String dica, boolean obrigatorio) {
        super(dica, obrigatorio, 12);
        addCaretListener(this);
        try {
            MaskFormatter mf = new MaskFormatter("##/##/####");
            mf.setPlaceholderCharacter('_');
            mf.install(this);
        } catch (ParseException e) {
            MensagensSistema.MensagemErro("NÃO FOI POSSÍVEL CRIAR O CAMPO DATA!");
        }
        if (eObrigatorio() == true) {
            
        }
    }

    @Override
    public boolean eValido() {
        return true;
    }

    @Override
    public void setValor(Object valor) {
        setText(sdf.format((Date) valor));
    }

    @Override
    public Object getValor() {
        try {
            String data = getText();
            return sdf.parse(data);
        } catch (ParseException ex) {
            return false;
        }
    }
    
    public void clearValue() {
        setText("");
    }
    
    public void setHabilitado(boolean habilitado) {
        setEditable(habilitado);
        if (habilitado && eObrigatorio()) {
            
        } else {
            setBackground(Color.WHITE);
        }
    }

    @Override
    public boolean eVazio() {
        return (getText().equals("__/__/____"));
    }

    @Override
    public void caretUpdate(CaretEvent ce) {
        if (eVazio()) {
            getCaret().setSelectionVisible(false);
            getCaret().setDot(0);
            getCaret().setSelectionVisible(true);
        }
    }
}
