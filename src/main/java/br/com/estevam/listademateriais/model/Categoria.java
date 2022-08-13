package br.com.estevam.listademateriais.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.estevam.listademateriais.dto.CategoriaDTO;

@Document
public class Categoria implements Serializable{

	private static final long serialVersionUID = 1l;
	
	@Id
	private String id;
	private String nome;
	private CategoriaDTO pai;
	
	@DBRef
	private Set<Categoria> filhos = new HashSet<>();

	public Categoria() {
		super();
	}
	
	public Categoria(String id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
		this.pai = null;
	}
	
	public Categoria(String id, String nome, CategoriaDTO pai) {
		super();
		this.id = id;
		this.nome = nome;
		this.pai = pai;
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

	public CategoriaDTO getPai() {
		return pai;
	}

	public void setPai(CategoriaDTO pai) {
		this.pai = pai;
	}

	public Set<Categoria> getFilhos() {
		return filhos;
	}

	public void setFilhos(Set<Categoria> filhos) {
		this.filhos = filhos;
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
		Categoria other = (Categoria) obj;
		return Objects.equals(id, other.id);
	}

}
