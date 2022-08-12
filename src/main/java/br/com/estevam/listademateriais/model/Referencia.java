package br.com.estevam.listademateriais.model;

import java.io.Serializable;
import java.util.Objects;

import br.com.estevam.listademateriais.dto.FabricanteDTO;
import br.com.estevam.listademateriais.dto.MaterialDTO;

public class Referencia implements Serializable{

	private static final long serialVersionUID = 1l;
	
	private MaterialDTO material;
	private FabricanteDTO fabricante;
	private String referencia;
	
	public Referencia() {
		super();
	}
	
	public Referencia(MaterialDTO material, FabricanteDTO fabricante, String referencia) {
		super();
		this.material = material;
		this.fabricante = fabricante;
		this.referencia = referencia;
	}

	public MaterialDTO getMaterial() {
		return material;
	}

	public void setMaterial(MaterialDTO material) {
		this.material = material;
	}

	public FabricanteDTO getFabricante() {
		return fabricante;
	}

	public void setFabricante(FabricanteDTO fabricante) {
		this.fabricante = fabricante;
	}
	
	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fabricante, material, referencia);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Referencia other = (Referencia) obj;
		return Objects.equals(fabricante.getId(), other.fabricante.getId()) && Objects.equals(material.getId(), other.material.getId())
				&& Objects.equals(referencia, other.referencia);
	}
	
}
