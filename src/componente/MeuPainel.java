package componente;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class MeuPainel extends JPanel implements MeuComponente {

    protected boolean obrigatorio;
    MeuComboBox campoClienteSac = new MeuComboBox(null, true, "Cliente");
    MeuComboBox campoEstadoSac = new MeuComboBox(null, false, "Estado");
    MeuCampoCEP campoCEPSac = new MeuCampoCEP(false, "CEP");
    MeuCampoCNPJ campoCNPJSac = new MeuCampoCNPJ(false, "CNPJ SACADO");
    MeuCampoFormatado campoBairroSac = new MeuCampoFormatado(false, "Bairro", 20, 30);
    MeuCampoFormatado campoRuaSac = new MeuCampoFormatado(false, "Rua", 20, 20);

    public MeuPainel() {
        super();
        setLayout(new MigLayout());
        this.add(campoClienteSac, "wrap");
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        //setBorder(BorderFactory.createTitledBorder(nome));  
        //setBorder(BorderFactory.createRaisedBevelBorder());
        //setBorder(BorderFactory.createTitledBorder(nome));
        setBorder(BorderFactory.createEtchedBorder());
    }

    public boolean eObrigatorio() {
        return obrigatorio;
    }

    public boolean eValido() {
        return true;
    }

    public boolean eVazio() {
        return false;
    }

    public String getDica() {
        return "";
    }

    @Override
    public void limpar() {
    }

    @Override
    public void setValor(Object valor) {
    }

    @Override
    public Object getValor() {
        return null;
    }

}
