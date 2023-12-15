package pojo;

import java.math.BigDecimal;

public class ValidarFinalidade {

	private Integer idEstadoOrigem;
	private Integer idEstadoConsumo;
	private Integer idGrupoProduto;
	private Integer idTipoPessoa;
	private Boolean validado = false;
	private BigDecimal aliquota;

	public Integer getIdEstadoOrigem() {
		return idEstadoOrigem;
	}

	public void setIdEstadoOrigem(Integer idEstadoOrigem) {
		this.idEstadoOrigem = idEstadoOrigem;
	}

	public Integer getIdEstadoConsumo() {
		return idEstadoConsumo;
	}

	public void setIdEstadoConsumo(Integer idEstadoConsumo) {
		this.idEstadoConsumo = idEstadoConsumo;
	}

	public Integer getIdGrupoProduto() {
		return idGrupoProduto;
	}

	public void setIdGrupoProduto(Integer idGrupoProduto) {
		this.idGrupoProduto = idGrupoProduto;
	}

	public Integer getIdTipoPessoa() {
		return idTipoPessoa;
	}

	public void setIdTipoPessoa(Integer idTipoPessoa) {
		this.idTipoPessoa = idTipoPessoa;
	}

	public Boolean getValidado() {
		return validado;
	}

	public void setValidado(Boolean validado) {
		this.validado = validado;
	}

	public BigDecimal getAliquota() {
		return aliquota;
	}

	public void setAliquota(BigDecimal aliquota) {
		this.aliquota = aliquota;
	}

}
