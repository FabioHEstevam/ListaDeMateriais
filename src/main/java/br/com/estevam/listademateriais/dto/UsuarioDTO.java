package br.com.estevam.listademateriais.dto;

import java.io.Serializable;

import br.com.estevam.listademateriais.model.Usuario;

public class UsuarioDTO implements Serializable{

	private static final long serialVersionUID = 1l;
	
	private String id;
	private String nome;
	private String email;
	
	public UsuarioDTO() {
		super();
	}
	
	public UsuarioDTO(Usuario usuario) {
		super();
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.email =usuario.getEmail();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
