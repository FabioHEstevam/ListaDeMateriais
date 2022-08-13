package br.com.estevam.listademateriais.model;

import java.io.Serializable;
import java.util.Objects;

public class ItemDaListaDeMateriais implements Serializable{

	private static final long serialVersionUID = 1l;
	
	private Referencia referencia;
	private Integer quantidade;
	private Unidade unidade;
	
	public ItemDaListaDeMateriais() {
		super();
	}

	public ItemDaListaDeMateriais(Referencia referencia, Unidade unidade) {
		super();
		this.referencia = referencia;
		this.quantidade = 0;
		this.unidade = unidade;
	}
	
	public ItemDaListaDeMateriais(Referencia referencia, Unidade unidade, Integer quantidade) {
		super();
		this.referencia = referencia;
		this.quantidade = quantidade;
		this.unidade = unidade;
	}

	public Referencia getReferencia() {
		return referencia;
	}

	public void setReferencia(Referencia referencia) {
		this.referencia = referencia;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	@Override
	public int hashCode() {
		return Objects.hash(referencia, unidade);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemDaListaDeMateriais other = (ItemDaListaDeMateriais) obj;
		return Objects.equals(referencia, other.referencia) && Objects.equals(unidade, other.unidade);
	}
	
}
