package componente;

public class MeuCampoInteiger extends MeuCampoGenerico {

	public MeuCampoInteiger(boolean obrigatorio, String dica) {
		super(obrigatorio, dica, 12);

		setHorizontalAlignment(RIGHT);
		setText("");
	}

	@Override
	public boolean eVazio() {
		return getText().trim().equals("");
	}

	@Override
	public void setValor(Object valor) {
		setText("" + (Integer) valor);
	}

	@Override
	public Object getValor() {
		return (Integer.parseInt("" + getText()));
	}

	@Override
	public void limpar() {
		setText("");
	}

	@Override
	public boolean eValido() {
		try {
			int valor = Integer.parseInt(getText());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
