package br.com.estevam.listademateriais.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import br.com.estevam.listademateriais.model.Categoria;

public class CategoriaDTO implements Serializable{

	private static final long serialVersionUID = 1l;
	
	private String id;
	private String nome;
	private Set<CategoriaDTO> filhos = new HashSet<>();
		
	public CategoriaDTO() {
		super();
	}
	
	public CategoriaDTO(Categoria categoria) {
		super();
		this.id = categoria.getId();
		this.nome = categoria.getNome();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<CategoriaDTO> getFilhos() {
		return filhos;
	}

	public void setFilhos(Set<CategoriaDTO> filhos) {
		this.filhos = filhos;
	}

}
