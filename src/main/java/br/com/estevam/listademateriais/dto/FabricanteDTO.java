package br.com.estevam.listademateriais.dto;

import java.io.Serializable;
import java.util.Objects;

import br.com.estevam.listademateriais.model.Fabricante;

public class FabricanteDTO implements Serializable{

	private static final long serialVersionUID = 1l;
	
	private String id;
	private String nome;
	
	public FabricanteDTO() {
		super();
	}

	public FabricanteDTO(Fabricante fabricante) {
		super();
		this.id = fabricante.getId();
		this.nome = fabricante.getNome();
	}

	public FabricanteDTO(String id) {
		super();
		this.id = id;
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
		FabricanteDTO other = (FabricanteDTO) obj;
		return Objects.equals(id, other.id);
	}

}
