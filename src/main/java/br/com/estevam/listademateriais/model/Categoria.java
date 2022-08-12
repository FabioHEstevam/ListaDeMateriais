package br.com.estevam.listademateriais.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Categoria implements Serializable{

	private static final long serialVersionUID = 1l;
	
	@Id
	private String id;
	private String nome;
	private Categoria pai;
	private Set<Categoria> filhos = new HashSet<>();
	
	@DBRef(lazy = true)
	private Set<Material> materiais = new HashSet<>();
	
	public Categoria() {
		super();
	}
	
	public Categoria(String id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
		this.pai = null;
	}
	
	public Categoria(String id, String nome, Categoria pai) {
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

	public Categoria getPai() {
		return pai;
	}

	public void setPai(Categoria pai) {
		this.pai = pai;
	}

	public Set<Categoria> getFilhos() {
		return filhos;
	}

	public void setFilhos(Set<Categoria> filhos) {
		this.filhos = filhos;
	}

	public Set<Material> getMateriais() {
		return materiais;
	}

	public void setMateriais(Set<Material> materiais) {
		this.materiais = materiais;
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
