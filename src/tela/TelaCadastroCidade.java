package tela;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import componente.MeuCampoCodigo;
import componente.MeuCampoFormatado;
import componente.MeuComboBox;
import componente.MeuComboPesquisa;
import dao.CidadeDao;
import dao.EstadoDao;
import pojo.Cidade;
import util.FiltrosConsulta;

public class TelaCadastroCidade extends TelaCadastro {

	private MeuCampoFormatado campoNomeCidade = new MeuCampoFormatado(true, "Nome", 25, 80);
	private MeuComboPesquisa campoEstado = new MeuComboPesquisa(EstadoDao.COMBOSQL, true, "Estado", 125, TelaCadastroEstado.class);
	private MeuCampoCodigo campoCodigoCidade = new MeuCampoCodigo(false, "Código");
	
	public boolean result;
	private Cidade cidade = new Cidade();
	private CidadeDao cidadeDao = new CidadeDao(cidade);

	private static TelaCadastroCidade tela;

	public static TelaCadastroCidade getTela() {
		if (tela == null) {
			tela = new TelaCadastroCidade();
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

	public TelaCadastroCidade() {
		super("Tela Cadastro Cidade");
		campoNomeCidade.setColumns(30);
		montarTela();
	}

	public void montarTela() {
		adicionaComponentes(1, 1, 1, 4, campoCodigoCidade, LADO);
		adicionaComponentes(2, 1, 1, 4, campoNomeCidade, LADO);
		adicionaComponentes(3, 1, 1, 4, campoEstado, LADO);
		habilitarCampos(false);
		listenerAtualizar();
		pack();
		habilitaBotoes();
	}

	private void setPersistencia() {
		cidade.setId(Integer.parseInt((String) "0" + (String) campoCodigoCidade.getValor()));
		cidade.setNome((String) campoNomeCidade.getText());
		cidade.getEstado().setId((Integer) campoEstado.getValor());
	}

	private void setGUI() {
		campoCodigoCidade.setValor(cidade.getId());
		campoNomeCidade.setValor(cidade.getNome());
		campoEstado.setValor(cidade.getEstado().getId());
	}

	@Override
	protected void novo() {
		super.novo();
		campoNomeCidade.requestFocusInWindow();
	}

	public boolean incluirBD() {
		setPersistencia();
		return cidadeDao.incluir();
	}

	public boolean alterarBD() {
		setPersistencia();
		return cidadeDao.alterar();
	}

	public boolean excluirBD() {
		return cidadeDao.excluir();
	}

	public void listenerAtualizar() {
		campoEstado.combo.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				campoEstado.limpar();
				campoEstado.setSql(EstadoDao.COMBOSQL);
			}
		});
	}

	public void consultar() {
		super.consultar();
		FiltrosConsulta[] filtros = new FiltrosConsulta[] { new FiltrosConsulta("Código", "CIDADE.ID", Integer.class),
				new FiltrosConsulta("Nome", "CIDADE.NOME", String.class),
				new FiltrosConsulta("Estado", "ESTADO.NOME", String.class) };

		new TelaPesquisa(this, "Pesquisa de cidade", new String[] { "Código", "Nome", "Estado" }, filtros,
				CidadeDao.PESQUISARSQL);
	}

	public void carregarDados(int pk) {
		super.carregarDados(pk);
		cidade.setId(pk);
		cidadeDao.consultar();
		setGUI();
	}

}
