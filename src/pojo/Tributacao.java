package pojo;

public class Tributacao {

	private Integer id;
	private String finalidade;
	private Estado estadoOrigem = new Estado();
	private Estado estadoConsumo = new Estado();
	private GrupoProduto grupoProduto = new GrupoProduto();
	private TipoPessoa tipoPessoa = new TipoPessoa();
	private Double aliquotaIcms;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFinalidade() {
		return finalidade;
	}

	public void setFinalidade(String finalidade) {
		this.finalidade = finalidade;
	}

	public Estado getEstadoOrigem() {
		return estadoOrigem;
	}

	public void setEstadoOrigem(Estado estadoOrigem) {
		this.estadoOrigem = estadoOrigem;
	}

	public Estado getEstadoConsumo() {
		return estadoConsumo;
	}

	public void setEstadoConsumo(Estado estadoConsumo) {
		this.estadoConsumo = estadoConsumo;
	}

	public GrupoProduto getGrupoProduto() {
		return grupoProduto;
	}

	public void setGrupoProduto(GrupoProduto grupoProduto) {
		this.grupoProduto = grupoProduto;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public Double getAliquotaIcms() {
		return aliquotaIcms;
	}

	public void setAliquotaIcms(Double aliquotaIcms) {
		this.aliquotaIcms = aliquotaIcms;
	}

}
