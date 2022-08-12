package br.com.estevam.listademateriais.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.springframework.data.annotation.Id;

import br.com.estevam.listademateriais.model.ListaDeMateriais;

public class ListaDeMateriaisDTO implements Serializable{

	private static final long serialVersionUID = 1l;
	
	@Id
	private String id;
	private String projeto;
	private Date data;
	private AutorDTO autor;

	public ListaDeMateriaisDTO() {
		super();
	}
	
	public ListaDeMateriaisDTO(ListaDeMateriais lista) {
		super();
		this.id = lista.getId();
		this.projeto = lista.getProjeto();
		this.data = lista.getData();
		this.autor = lista.getAutor();
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
		ListaDeMateriaisDTO other = (ListaDeMateriaisDTO) obj;
		return Objects.equals(id, other.id);
	}
	
}