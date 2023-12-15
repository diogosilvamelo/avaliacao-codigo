package componente;

import java.awt.Color;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class MeuCampoDecimal extends MeuCampoGenerico {

	private int iMaxLength;
	int retorno;

	public MeuCampoDecimal(boolean obrigatorio, String dica, int tamanho, int maxChar) {
		super(obrigatorio, dica, tamanho);
		iMaxLength = maxChar;
		setColumns(tamanho);
		setDocument(new PlainDocument() {
			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				if (iMaxLength <= 0) {
					super.insertString(offs, str.replaceAll("[^0-9,]", ""), a);
				}

				int ilen = (getLength() + str.length());
				if (ilen <= iMaxLength) {
					super.insertString(offs, str.replaceAll("[^0-9,]", ""), a);
				}
			}
		});
		setHorizontalAlignment(RIGHT);
		setText("");
		if (eObrigatorio() == true) {
			
		}
	}

	@Override
	public boolean eVazio() {
		return getText().trim().equals("0,00");
	}

	@Override
	public void setValor(Object valor) {
		setText("" + (Double) valor);
	}

	@Override
	public Object getValor() {
		try {
			retorno = (int) (Double.parseDouble(0 + getText().replace(",", ".")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public void limpar() {
		setText(" ");
	}

	@Override
	public boolean eValido() {
		if (obrigatorio == false) {
			return true;
		}
		try {
			double valor = Double.parseDouble(getText().replace(",", "."));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}