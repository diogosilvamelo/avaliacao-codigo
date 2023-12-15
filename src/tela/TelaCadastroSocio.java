package tela;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import componente.MeuCampoCheckBox;
import componente.MeuCampoCodigo;
import componente.MeuCampoFormatado;
import dao.SocioDao;
import pojo.Socio;
import util.FiltrosConsulta;

public class TelaCadastroSocio extends TelaCadastro {

	private MeuCampoFormatado campoNome = new MeuCampoFormatado(true, "Nome", 25, 80);
	private MeuCampoFormatado campoTelefone = new MeuCampoFormatado(true, "Telefone", 15, 80);
	private MeuCampoFormatado campoCpf = new MeuCampoFormatado(true, "CPF", 14, 80);
	private MeuCampoCodigo campoId = new MeuCampoCodigo(false, "Código");
	private MeuCampoCheckBox isEstrangeiro = new MeuCampoCheckBox("Estrangeiro");

	private Socio socio = new Socio();
	private SocioDao socioDao = new SocioDao(socio);
	private int idCooperado;

	private static TelaCadastroSocio tela;

	public static TelaCadastroSocio getTela() {
		if (tela == null) {
			tela = new TelaCadastroSocio();
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

	public TelaCadastroSocio() {
		super("Tela Cadastro Sócio");
		campoNome.setColumns(30);
		montarTela();
	}

	public void montarTela() {
		adicionaComponentes(1, 1, 1, 4, campoId, LADO);
		adicionaComponentes(2, 1, 1, 4, isEstrangeiro, LADO);
		adicionaComponentes(3, 1, 1, 4, campoNome, LADO);
		adicionaComponentes(4, 1, 1, 4, campoTelefone, LADO);
		adicionaComponentes(5, 1, 1, 4, campoCpf, LADO);
		novo();
		habilitarCampos(true);
		pack();
		habilitaBotoes();
		obrigatorioEstrangeiro();
		
		isEstrangeiro.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				obrigatorioEstrangeiro();
			}
		});

	}

	public void obrigatorioEstrangeiro() {
		if (isEstrangeiro.getValor()) {
			campoCpf.removeObrigatorio();
			campoTelefone.removeObrigatorio();
		} else {
			campoCpf.adicionarObrigatorio();
			campoTelefone.adicionarObrigatorio();
		}
	}

	private void setPersistencia() {
		socio.setId(Integer.parseInt((String) "0" + (String) campoId.getValor()));
		socio.setNome((String) campoNome.getText());
		socio.setTelefone((String) campoTelefone.getText());
		socio.setCpf((String) campoCpf.getText());
		socio.setIdCooperado(this.idCooperado);
		socio.setIsEstrangeiro(isEstrangeiro.getValor());
	}

	private void setGUI() {
		campoId.setValor(socio.getId());
		campoNome.setValor(socio.getNome());
		campoTelefone.setValor(socio.getTelefone());
		campoCpf.setValor(socio.getCpf());
	}

	@Override
	protected void novo() {
		super.novo();
	}

	public boolean incluirBD() {

		setPersistencia();
		return socioDao.incluir();
	}

	public boolean alterarBD() {
		setPersistencia();
		return socioDao.alterar();
	}

	public boolean excluirBD() {
		return socioDao.excluir();
	}

	public void consultar() {
		super.consultar();
		FiltrosConsulta[] filtros = new FiltrosConsulta[] { new FiltrosConsulta("Código", "SOCIO.ID", Integer.class),
				new FiltrosConsulta("Nome", "SOCIO.NOME", String.class),
				new FiltrosConsulta("CPF", "SOCIO.CPF", String.class) };

		new TelaPesquisa(this, "Pesquisa de sócio", new String[] { "Código", "Nome", "CPF" }, filtros,
				SocioDao.PESQUISARSQL);
	}

	public void carregarDados(int pk) {
		super.carregarDados(pk);
		socio.setId(pk);
		socioDao.consultar();
		setGUI();
	}

	public void setIdCooperado(int idCooperado) {
		this.idCooperado = (idCooperado);
	}
}
