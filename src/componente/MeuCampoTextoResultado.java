package componente;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MeuCampoTextoResultado extends JLabel implements MeuComponente {

    private String dica;

    public MeuCampoTextoResultado(String dica, int largura, int altura) {
        this.dica = dica;
        setPreferredSize(new Dimension(largura, altura));
        setHorizontalAlignment(SwingConstants.LEFT);
        setForeground(Color.RED);
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

	@Override
	public void limpar() {
		
	}

	@Override
	public boolean eValido() {
		return false;
	}

	@Override
	public boolean eObrigatorio() {
		return false;
	}

	@Override
	public boolean eVazio() {
		return false;
	}
}
