package br.com.estevam.listademateriais.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.estevam.listademateriais.dto.CategoriaResumoDTO;
import br.com.estevam.listademateriais.dto.CategoriaDTO;
import br.com.estevam.listademateriais.model.Categoria;
import br.com.estevam.listademateriais.repository.CategoriaRepository;
import br.com.estevam.listademateriais.services.exception.OperationException;

@Service
public class CategoriaService {

	@Autowired
	CategoriaRepository repo;
	
	public List<Categoria> findAll(){
		return repo.findAll();
	}
	
	public Categoria findById(String id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElse(null);
	}
	
	public Categoria insert(Categoria obj) {
		Categoria pai = findById(obj.getPai().getId());
		obj.setPai(null);
		obj = repo.insert(obj);
		if(pai!=null) {
				pai.getFilhos().add(new CategoriaResumoDTO(obj));
				obj.setPai(new CategoriaResumoDTO(pai));
				repo.save(pai);
				repo.save(obj);
		}
		return obj;
	}
	
	public void delete(String id) {
		Categoria obj = findById(id);
		if(!obj.getFilhos().isEmpty()) {
			throw new OperationException("A categoria não pode ser deletada pois possui categorias associados a ela");
		}
		if(!obj.getMateriais().isEmpty()) {
			throw new OperationException("A categoria não pode ser deletada pois possui materiais associados a ela");
		}
		if(obj.getPai()!=null) {
			Categoria pai = findById(obj.getPai().getId());
			if(pai!=null) {
				pai.getFilhos().remove(new CategoriaResumoDTO(obj));
				repo.save(pai);
			}
			
		}
		repo.delete(obj);
	}
	
	public Categoria update(Categoria obj) {
		Categoria newObj = findById(obj.getId());
		updateDate(newObj, obj);
		return repo.save(newObj);
	}
	
	public void updateDate(Categoria newObj, Categoria obj) {
		Categoria pai = findById(newObj.getPai().getId());
		Categoria newPai = findById(obj.getPai().getId());
		if(newPai!=null) {
			if(pai!=null) {
				pai.getFilhos().remove(new CategoriaResumoDTO(newObj));
				repo.save(pai);
			}
			newObj.setPai(new CategoriaResumoDTO (newPai));
			newPai.getFilhos().add(new CategoriaResumoDTO(newObj));
			repo.save(newPai);
		}
		newObj.setNome(obj.getNome());
	}
	
	public Categoria fromDTO(CategoriaDTO objDto){
		return new Categoria(objDto.getId(),objDto.getNome(),objDto.getPai());
	}
}
