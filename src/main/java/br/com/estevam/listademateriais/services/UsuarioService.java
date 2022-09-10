package br.com.estevam.listademateriais.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.estevam.listademateriais.dto.UsuarioDTO;
import br.com.estevam.listademateriais.model.Usuario;
import br.com.estevam.listademateriais.repository.UsuarioRepository;
import br.com.estevam.listademateriais.services.exception.ObjectNotFoundException;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repo;
	
	public List<Usuario> findAll(){
		return repo.findAll();
	}
	
	public Usuario findById(String id) {
		Optional<Usuario> obj = repo.findById(id);
		return obj.orElseThrow(()->new ObjectNotFoundException("Usuário não encontrado"));
	}
	
	public Usuario findByEmail(String email) {
		Usuario obj = repo.findByEmail(email);
		return obj;
	}
	
	public Usuario insert(Usuario obj) {
		return repo.insert(obj);
	}
	
	public Usuario fromDTO(UsuarioDTO objDto){
		return new Usuario(objDto.getId(),objDto.getNome(),objDto.getEmail(),null);
	}
	
	public void delete(String id) {
		if(findAll().size()>1) {
			findById(id);
			repo.deleteById(id);
		}
	}
	
	public Usuario update(Usuario obj) {
		Usuario newObj = findById(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void updateData(Usuario newObj, Usuario obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
