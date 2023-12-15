package componente;

import java.awt.Color;
import java.awt.Toolkit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class MeuCampoRG extends MeuCampoFormatado {

	public MeuCampoRG(boolean obrigatorio, String dica) {
		super(dica, obrigatorio, 15);
		setDocument(new MeuDocument(11));
		if (eObrigatorio() == true) {

		}
	}

	@Override
	public boolean eValido() {
		return true;
	}

	class MeuDocument extends PlainDocument {

		private int tamanho;

		public MeuDocument(int tamanho) {
			this.tamanho = tamanho;
		}

		@Override
		public void insertString(int offs, String str, AttributeSet a) {
			try {
				Pattern padrao = Pattern.compile("[0-9]");
				Matcher matcher = padrao.matcher(str);

				String novoValor = getValor() + str;

				if (!matcher.find()) {
					Toolkit.getDefaultToolkit().beep();
					return;
				}
				if (novoValor.length() > tamanho) {
					Toolkit.getDefaultToolkit().beep();
					return;
				}
				super.insertString(offs, str.toUpperCase(), a);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean eVazio() {
		return getText().trim().isEmpty();
	}
}
