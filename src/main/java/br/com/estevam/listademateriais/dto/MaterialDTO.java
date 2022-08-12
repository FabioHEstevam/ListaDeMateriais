package br.com.estevam.listademateriais.dto;

import java.io.Serializable;

import br.com.estevam.listademateriais.model.Material;

public class MaterialDTO implements Serializable{
	
	private static final long serialVersionUID = 1l;
	
	private String id;
	private String descricao;
	
	public MaterialDTO() {
		super();
	}
	
	public MaterialDTO(Material material) {
		super();
		this.id = material.getId();
		this.descricao = material.getDescricao();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
