package br.com.estevam.listademateriais.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.estevam.listademateriais.dto.AutorDTO;
import br.com.estevam.listademateriais.dto.ListaDeMateriaisDTO;
import br.com.estevam.listademateriais.model.ItemDaListaDeMateriais;
import br.com.estevam.listademateriais.model.ListaDeMateriais;
import br.com.estevam.listademateriais.model.Material;
import br.com.estevam.listademateriais.model.Usuario;
import br.com.estevam.listademateriais.repository.ListaDeMateriaisRepository;
import br.com.estevam.listademateriais.repository.MaterialRepository;
import br.com.estevam.listademateriais.repository.UnidadeRepository;
import br.com.estevam.listademateriais.repository.UsuarioRepository;
import br.com.estevam.listademateriais.services.exception.ObjectNotFoundException;

@Service
public class ListaDeMateriaisService {

	@Autowired
	private ListaDeMateriaisRepository repo;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private MaterialRepository materialRepository;
	
	@Autowired
	private UnidadeRepository unidadeRepository;
	
	public List<ListaDeMateriais> findAll(){
		return repo.findAll();
	}
	
	public ListaDeMateriais findById(String id) {
		Optional<ListaDeMateriais> obj = repo.findById(id);;
		return obj.orElseThrow(()->new ObjectNotFoundException("Lista de materiais não encontrada"));
	}
	
	public ListaDeMateriais insert(ListaDeMateriais obj) {
		Usuario autor = usuarioRepository.findById(obj.getAutor().getId()).orElse(null);
		obj.setAutor(null);
		obj = repo.insert(obj);
		if(autor!=null) {
			autor.getProjetos().add(new ListaDeMateriaisDTO(obj));
			obj.setAutor(new AutorDTO(autor));
			usuarioRepository.save(autor);
		}
		repo.save(obj);
		return obj; 
	}
	
	public void delete(String id) {
		ListaDeMateriais obj = findById(id);
		Usuario autor = usuarioRepository.findById(obj.getAutor().getId()).orElse(null);
		if(autor!=null) {
			autor.getProjetos().remove(new ListaDeMateriaisDTO(obj));
			usuarioRepository.save(autor);
		}
		repo.delete(obj);
	}
	
	public ListaDeMateriais update(ListaDeMateriais obj) {
		ListaDeMateriais newObj = findById(obj.getId());
		updateData(newObj,obj);	
		
		return repo.save(newObj);
	}
	
	public void updateData(ListaDeMateriais newObj, ListaDeMateriais obj) {
		
		newObj.setProjeto(obj.getProjeto());
		newObj.setData(obj.getData());
		newObj.setLista(obj.getLista());
		
		
		Usuario autor = null;
		
		if(newObj.getAutor()!=null) {
			autor = usuarioRepository.findById(newObj.getAutor().getId()).orElse(null);
		}
		
		Usuario newAutor =null;
		
		if(obj.getAutor()!=null) {
			newAutor = usuarioRepository.findById(obj.getAutor().getId()).orElse(null);
		}
		
		if(newAutor!=null) {
			if(autor!=null) {
				autor.getProjetos().remove(new ListaDeMateriaisDTO(newObj));
				usuarioRepository.save(autor);
			}
			newAutor.getProjetos().add(new ListaDeMateriaisDTO(newObj));
			usuarioRepository.save(newAutor);
			newObj.setAutor(new AutorDTO(newAutor));
		}
	}
	
	public ListaDeMateriais fromDTO(ListaDeMateriaisDTO objDto) {
		return new ListaDeMateriais(objDto.getId(),objDto.getProjeto(),objDto.getData(),objDto.getAutor());
	}
	
	public ItemDaListaDeMateriais addItem(ListaDeMateriais obj, ItemDaListaDeMateriais referencia) {
		updateItem(referencia);
		ItemDaListaDeMateriais item = obj.getLista().stream().filter(x->x.equals(referencia)).findFirst().orElse(null);
		if(item!=null) {
			if(referencia.getQuantidade()>0) {
				item.setQuantidade(referencia.getQuantidade());
			}else {
				obj.getLista().remove(item);
			}
			repo.save(obj);
		}else {
			if(referencia.getQuantidade()>0) {
				obj.getLista().add(referencia);
				repo.save(obj);
			}
		}
		return item;
	}
	
	public void deleteItem(ListaDeMateriais obj, ItemDaListaDeMateriais referencia) {
		ItemDaListaDeMateriais item = obj.getLista().stream().filter(x->x.equals(referencia)).findFirst().orElse(null);
		if(item!=null) {
			obj.getLista().remove(item);
			repo.save(obj);
		}
	}
	
	public void updateItem(ItemDaListaDeMateriais item) {
		Material mat = materialRepository.findById(item.getReferencia().getMaterial().getId()).orElseThrow(()->new ObjectNotFoundException("Material não encontrado"));
		item.setUnidade(unidadeRepository.findById(item.getUnidade().getId()).orElseThrow(()->new ObjectNotFoundException("Material não encontrado")));
		item.setReferencia(mat.getReferencias().stream().filter(x->item.getReferencia().equals(x)).findFirst().orElseThrow(()->new ObjectNotFoundException("Referencia não encontrada")));
	}
	
	
}
