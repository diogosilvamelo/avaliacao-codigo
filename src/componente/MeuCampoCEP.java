package componente;

import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.event.CaretEvent;
import javax.swing.text.MaskFormatter;

public class MeuCampoCEP extends MeuCampoGenerico {

    public MeuCampoCEP(boolean obrigatorio, String dica) {
        super(obrigatorio, dica, 14);
        if (eObrigatorio() == true) {
            
        }
        try {
            MaskFormatter mf = new MaskFormatter("#####-###");
            mf.setPlaceholderCharacter('_');
            mf.install(this);
            setColumns(6);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível criar o campo MeuCampoData");
        }
    }

    public boolean eValido() {
        if (!eObrigatorio() && eVazio()) {
            return true;
        }

        return getText().replace("_", "").replace("-", "").trim().length() == 8;
    }

    public boolean eVazio() {
        return getText().equals("_____-___");
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
