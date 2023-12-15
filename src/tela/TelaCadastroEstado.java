package tela;

import javax.swing.JPanel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import componente.MeuCampoCodigo;
import componente.MeuCampoFormatado;
import componente.MeuCampoSigla;
import componente.MeuComboBox;
import dao.EstadoDao;
import pojo.Estado;
import util.FiltrosConsulta;

public class TelaCadastroEstado extends TelaCadastro {

	MeuCampoCodigo labelcodigo = new MeuCampoCodigo(true, "Código");

	private MeuCampoCodigo campoCodigoEstado = new MeuCampoCodigo(false, "Código");
	private MeuCampoFormatado campoNomeEstado = new MeuCampoFormatado(true, "Nome", 25, 80);

	JPanel painel = new JPanel();

	public boolean result;

	private Estado estado = new Estado();
	private EstadoDao estadoDao = new EstadoDao(estado);

	private static TelaCadastroEstado tela;

	public static TelaCadastroEstado getTela() {
		if (tela == null) {
			tela = new TelaCadastroEstado();
			tela.addInternalFrameListener(new InternalFrameAdapter() {

				@Override
				public void internalFrameClosed(InternalFrameEvent e) {
					TelaSistema.jdp.remove(tela);
					tela = null;
				}
			});
			TelaSistema.jdp.add(tela);
		}
		TelaSistema.jdp.setSelectedFrame(tela);
		TelaSistema.jdp.moveToFront(tela);
		TelaSistema.centraliza(tela);
		return tela;
	}

	public TelaCadastroEstado() {
		super("Tela Cadastro Estado");
		montarTela();
	}

	public void montarTela() {
		adicionaComponentes(1, 1, 1, 4, campoCodigoEstado, LADO);
		adicionaComponentes(2, 1, 1, 4, campoNomeEstado, LADO);
		habilitarCampos(false);
		pack();
		habilitaBotoes();
	}

	private void setPersistencia() {
		estado.setId(Integer.parseInt("0" + campoCodigoEstado.getValor()));
		estado.setNome((String) campoNomeEstado.getValor());
	}

	private void setGUI() {
		campoCodigoEstado.setValor(estado.getId());
		campoNomeEstado.setValor(estado.getNome());
	}

	@Override
	protected void novo() {
		super.novo();
		campoNomeEstado.requestFocusInWindow();
	}

	public boolean incluirBD() {
		setPersistencia();
		return estadoDao.incluir();
	}

	public boolean alterarBD() {
		setPersistencia();
		return estadoDao.alterar();
	}

	public boolean excluirBD() {
		return estadoDao.excluir();
	}

	public void consultar() {
		super.consultar();
		System.out.println("consultar");
		FiltrosConsulta[] filtros = new FiltrosConsulta[] { new FiltrosConsulta("Código", "ID", Integer.class),
				new FiltrosConsulta("Nome", "NOME", String.class), };
		new TelaPesquisa(this, "Pesquisa de Estado", new String[] { "Código", "Nome" }, filtros,
				"SELECT ID,NOME FROM ESTADO");
	}

	public void carregarDados(int pk) {
		super.carregarDados(pk);
		estado.setId(pk);
		estadoDao.consultar();
		setGUI();
	}
}
