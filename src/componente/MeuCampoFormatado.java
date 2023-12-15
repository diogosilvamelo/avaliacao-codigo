package componente;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class MeuCampoFormatado extends JFormattedTextField implements FocusListener, MeuComponente, CaretListener {

	boolean obrigatorio;
	private boolean podeHabilitar;
	private String dica;

	public MeuCampoFormatado(boolean obrigatorio, String dica, int tamanho, int maxChar) {
		this.obrigatorio = obrigatorio;
		this.dica = dica;
		try {
			setDocument(new PlainDocument() {
				@Override
				public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
					String novoValor = getValor().toString();
					if (novoValor.length() >= 60) {
						Toolkit.getDefaultToolkit().beep();
						return;
					}
					super.insertString(offs, str.toUpperCase().replaceAll("[^A-Z|^ |0-9|^ |^À-Ú^| |^@^.^´^^`^]", ""),
							a);
				}
			});
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Campo Com valores Invalidos");
		}

		setColumns(tamanho);
		addFocusListener(this);
		if (eObrigatorio() == true) {
			
		}
	}

	public MeuCampoFormatado(String dica, boolean obrigatorio, int tamanho, int maxChar) {
		this.obrigatorio = obrigatorio;
		this.dica = dica;
		setColumns(tamanho);
		addFocusListener(this);
		try {
			setDocument(new PlainDocument() {
				@Override
				public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
					String novoValor = getValor().toString();
					if (novoValor.length() >= 60) {
						Toolkit.getDefaultToolkit().beep();
						return;
					}
					super.insertString(offs, str.toUpperCase(), a);
				}
			});
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Campo Com valores Invalidos");
		}

		if (eObrigatorio()) {
			
		}
	}

	public MeuCampoFormatado(String dica, boolean obrigatorio, int tamanho) {
		this.obrigatorio = obrigatorio;
		this.dica = dica;
		setColumns(tamanho);
		addFocusListener(this);
		try {
			setDocument(new PlainDocument() {
				@Override
				public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
					String novoValor = getValor().toString();
					super.insertString(offs, str.toUpperCase(), a);
				}
			});
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Campo Com valores Invalidos");
		}

		if (eObrigatorio()) {
			setBackground(Color.WHITE);
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		setBackground(new Color(255, 255, 255));
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (eObrigatorio()) {
			
		} else {
			setBackground(Color.WHITE);
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

	public void removeObrigatorio() {
		obrigatorio = false;
	}

	public void adicionarObrigatorio() {
		obrigatorio = true;
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

	@Override
	public void caretUpdate(CaretEvent ce) {
	}
}
