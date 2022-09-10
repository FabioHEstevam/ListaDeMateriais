package br.com.estevam.listademateriais.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.estevam.listademateriais.dto.ListaDeMateriaisDTO;
import br.com.estevam.listademateriais.model.ItemDaListaDeMateriais;
import br.com.estevam.listademateriais.model.ListaDeMateriais;
import br.com.estevam.listademateriais.services.ListaDeMateriaisService;

@RestController
@RequestMapping(value="REST/lista-de-materiais")
public class ListaDeMateriaisResource {

	@Autowired
	private ListaDeMateriaisService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ListaDeMateriaisDTO>> findAll(){
		List<ListaDeMateriais> list = service.findAll();
		List<ListaDeMateriaisDTO> listDTO = list.stream().map(x->new ListaDeMateriaisDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<ListaDeMateriaisDTO> findById(@PathVariable String id){
		ListaDeMateriais obj = service.findById(id);
		return ResponseEntity.ok().body(new ListaDeMateriaisDTO(obj));
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody ListaDeMateriaisDTO objDto){
		ListaDeMateriais obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable String id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value="/{id}",method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody ListaDeMateriaisDTO objDto,@PathVariable String id){
		ListaDeMateriais obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}/itens", method = RequestMethod.GET)
	public ResponseEntity<List<ItemDaListaDeMateriais>> findItens(@PathVariable String id){
		ListaDeMateriais obj = service.findById(id);
		return ResponseEntity.ok().body(obj.getLista().stream().collect(Collectors.toList()));
	}
	
	@RequestMapping(value="/{id}/itens", method = RequestMethod.PUT)
	public ResponseEntity<List<ItemDaListaDeMateriais>> addItens(@RequestBody ItemDaListaDeMateriais item, @PathVariable String id){
		ListaDeMateriais obj = service.findById(id);
		service.addItem(obj,item);
		return  ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}/itens", method = RequestMethod.DELETE)
	public ResponseEntity<List<ItemDaListaDeMateriais>> findSubcategorias(@RequestBody ItemDaListaDeMateriais item, @PathVariable String id){
		ListaDeMateriais obj = service.findById(id);
		service.deleteItem(obj,item);
		return  ResponseEntity.noContent().build();
	}
}
