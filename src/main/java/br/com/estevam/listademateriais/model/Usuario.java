package br.com.estevam.listademateriais.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.estevam.listademateriais.dto.ListaDeMateriaisDTO;

@Document
public class Usuario implements Serializable{

	private static final long serialVersionUID = 1l;
	
	@Id
	private String id;
	private String nome;
	@Indexed(unique = true)
	private String email;
	private String telefone;
	private String senha;
	
	private Set<ListaDeMateriaisDTO> projetos = new HashSet<>();
	
	public Usuario() {
		super();
	}

	public Usuario(String id, String nome, String telefone, String email) {
		super();
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.senha = "123";
	}
	
	public Usuario(String id, String nome, String telefone, String email, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.senha = senha;
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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<ListaDeMateriaisDTO> getProjetos() {
		return projetos;
	}

	public void setProjetos(Set<ListaDeMateriaisDTO> projetos) {
		this.projetos = projetos;
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
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome +", telefone=" + telefone + ", email=" + email +  "]";
	}

	
}
