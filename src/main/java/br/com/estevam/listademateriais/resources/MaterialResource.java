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

import br.com.estevam.listademateriais.dto.CategoriaDTO;
import br.com.estevam.listademateriais.dto.MaterialDTO;
import br.com.estevam.listademateriais.model.Material;
import br.com.estevam.listademateriais.model.Referencia;
import br.com.estevam.listademateriais.services.MaterialService;

@RestController
@RequestMapping(value="REST/materiais")
public class MaterialResource {

	@Autowired
	private MaterialService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<MaterialDTO>> findAll(){
		List<Material> list = service.findAll();
		List<MaterialDTO> listDTO = list.stream().map(x->new MaterialDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<MaterialDTO> findById(@PathVariable String id){
		Material obj = service.findById(id);
		return ResponseEntity.ok().body(new MaterialDTO(obj));
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> inset(@RequestBody Material obj){
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody MaterialDTO objDto,@PathVariable String id){
		Material obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}/referencias", method = RequestMethod.GET)
	public ResponseEntity<List<Referencia>> findReferencias(@PathVariable String id){
		Material obj = service.findById(id);
		return ResponseEntity.ok().body(obj.getReferencias().stream().collect(Collectors.toList()));
	}
	
	@RequestMapping(value="/{id}/categorias", method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findCategorias(@PathVariable String id){
		Material obj = service.findById(id);
		return ResponseEntity.ok().body(obj.getCategorias().stream().collect(Collectors.toList()));
	}
}
