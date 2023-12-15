package componente;

import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.event.CaretEvent;
import javax.swing.text.MaskFormatter;

public class MeuCampoTelefone extends MeuCampoGenerico {

    public MeuCampoTelefone(boolean obrigatorio, String dica) {
        super(obrigatorio, dica, 12);
        try {
            MaskFormatter mf = new MaskFormatter("(##)##### - ####");
            mf.setPlaceholderCharacter('_');
            mf.install(this);
            setColumns(9);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível criar o campo MeuCampoData");
        }
        if (eObrigatorio() == true) {
            
        }
    }

    @Override
    public boolean eValido() {
        if (!eObrigatorio() && eVazio()) {
            return true;
        }
        return getText().replace("(", "").replace(")", "").replace("-", "").replace(" ", "").replace("_", "").trim().length() == 10;
    }

    public boolean eVazio() {
        return getText().equals("(__)____ - ____");
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
