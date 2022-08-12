package br.com.estevam.listademateriais.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.estevam.listademateriais.dto.AutorDTO;

@Document
public class ListaDeMateriais implements Serializable{

	private static final long serialVersionUID = 1l;
	
	@Id
	private String id;
	private String projeto;
	private Date data;
	private AutorDTO autor;
	
	private Set<ItemDaListaDeMateriais> lista = new HashSet<>();
	
	public ListaDeMateriais() {
		super();
	}
	
	public ListaDeMateriais(String id, String projeto, Date data, AutorDTO autor) {
		super();
		this.id = id;
		this.projeto = projeto;
		this.data = data;
		this.autor = autor;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProjeto() {
		return projeto;
	}

	public void setProjeto(String projeto) {
		this.projeto = projeto;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public AutorDTO getAutor() {
		return autor;
	}

	public void setAutor(AutorDTO autor) {
		this.autor = autor;
	}

	public Set<ItemDaListaDeMateriais> getLista() {
		return lista;
	}

	public void setLista(Set<ItemDaListaDeMateriais> lista) {
		this.lista = lista;
	}
	
	
	
}
