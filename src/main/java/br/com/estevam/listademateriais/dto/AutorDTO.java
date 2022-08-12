package br.com.estevam.listademateriais.dto;

import java.io.Serializable;

import br.com.estevam.listademateriais.model.Usuario;

public class AutorDTO implements Serializable{

	private static final long serialVersionUID = 1l;
	
	private String id;
	private String nome;
	
	public AutorDTO() {
		super();
	}
	
	public AutorDTO(Usuario usuario) {
		super();
		this.id = usuario.getId();
		this.nome = usuario.getNome();
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
}
