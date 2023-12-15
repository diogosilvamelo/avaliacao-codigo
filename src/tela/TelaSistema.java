package tela;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import componente.MeuStatusBar;
import componente.MeuToolBar;
import dao.DataBaseManager;

public class TelaSistema extends JFrame implements ActionListener {

	public static MeuJDesktopPane jdp = new MeuJDesktopPane();

	JMenuBar menu = new JMenuBar();
	JMenu cadastro = new JMenu("Cadastro");

	private MeuToolBar meuToolBar = new MeuToolBar();
	private MeuStatusBar meuStatusBar = new MeuStatusBar();

	Icon icoPessoas = new ImageIcon(getClass().getResource("/imagem/empregado.png"));
	Icon ico = new ImageIcon(getClass().getResource("/imagem/family.png"));

	private ImageIcon icoEstado = new ImageIcon(getClass().getResource("/imagem/estado.png"));
	private ImageIcon icoCidade = new ImageIcon(getClass().getResource("/imagem/cidade.png"));

	JMenuItem cooperado = new JMenuItem("Cooperado", icoPessoas);
	JMenuItem estado = new JMenuItem("Estado", icoEstado);
	JMenuItem cidade = new JMenuItem("Cidade", icoCidade);
	JMenuItem venda = new JMenuItem("Venda", ico);

	ImageIcon iconeprincipal = new ImageIcon(getClass().getResource(""));

	public static void main(String args[]) throws IOException {
		try {
			 DataBaseManager.initializeDatabase(); 
			TelaSistema telaSistema = new TelaSistema();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public TelaSistema() {
		setTitle("Sistema");
		setJMenuBar(menu);
		setIconImage(iconeprincipal.getImage());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().add(jdp);
		setVisible(true);
		setExtendedState(MAXIMIZED_BOTH);
		barraMenu();
		listeners();

		this.getContentPane().add("South", meuStatusBar);
		this.getContentPane().add("North", meuToolBar);
	}

	public static void centraliza(JInternalFrame janela) {
		int larguraDesk = jdp.getWidth();
		int alturaDesk = jdp.getHeight();
		int larguraIFrame = janela.getWidth();
		int alturaIFrame = janela.getHeight();
		janela.setLocation(larguraDesk / 2 - larguraIFrame / 2, alturaDesk / 2 - alturaIFrame / 2);
	}

	private void barraMenu() {
		menu.add(cadastro);

		cadastro.add(cooperado);
		cadastro.add(cidade);
		cadastro.add(estado);
		cadastro.add(venda);
	}

	private void listeners() {
		cidade.addActionListener(this);
		estado.addActionListener(this);
		cooperado.addActionListener(this);
		venda.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cidade) {
			TelaCadastroCidade.getTela();
		}
		if (e.getSource() == estado) {
			TelaCadastroEstado.getTela();
		}
		if (e.getSource() == cooperado) {
			TelaCadastroCooperado.getTela();
		}
		if (e.getSource() == venda) {
			TelaCadastroVenda.getTela();
		}
	}

}
