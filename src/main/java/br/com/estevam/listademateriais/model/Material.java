package br.com.estevam.listademateriais.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.estevam.listademateriais.dto.CategoriaDTO;

@Document
public class Material implements Serializable{
	
	private static final long serialVersionUID = 1l;
	
	@Id
	private String id;
	private String descricao;
	private Set<CategoriaDTO> categorias = new HashSet<>();
	
	private List<Referencia> referencias = new ArrayList<>();
	
	public Material() {
		super();
	}
	
	public Material(String id, String descricao) {
		super();
		this.id = id;
		this.descricao = descricao;
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

	public Set<CategoriaDTO> getCategorias() {
		return categorias;
	}

	public void setCategorias(Set<CategoriaDTO> categorias) {
		this.categorias = categorias;
	}

	public List<Referencia> getReferencias() {
		return referencias;
	}

	public void setReferencias(List<Referencia> referencias) {
		this.referencias = referencias;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Material other = (Material) obj;
		return Objects.equals(id, other.id);
	}
	
}
