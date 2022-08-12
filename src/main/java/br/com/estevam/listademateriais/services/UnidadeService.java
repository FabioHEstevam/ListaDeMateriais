package br.com.estevam.listademateriais.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.estevam.listademateriais.model.Unidade;
import br.com.estevam.listademateriais.repository.UnidadeRepository;
import br.com.estevam.listademateriais.services.exception.ObjectNotFoundException;

@Service
public class UnidadeService {

	@Autowired
	private UnidadeRepository repo;
	
	public List<Unidade> findAll(){
		return repo.findAll();
	}
	
	public Unidade findById(String id) {
		Optional<Unidade> user = repo.findById(id);
		return user.orElseThrow(()->new ObjectNotFoundException("Unidade não encontrada"));
	}
	
	public Unidade insert(Unidade obj) {
		return repo.insert(obj);
	}
	
	public void delete(String id) {
		findById(id);
		repo.deleteById(id);
	}
	
	public Unidade update(Unidade obj) {
		Unidade newObj = findById(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void updateData(Unidade newObj, Unidade obj) {
		newObj.setNome(obj.getNome());
		newObj.setAbreviacao(obj.getAbreviacao());
	}
}
