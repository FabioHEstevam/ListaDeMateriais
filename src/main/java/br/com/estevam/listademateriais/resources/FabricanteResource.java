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

import br.com.estevam.listademateriais.dto.FabricanteDTO;
import br.com.estevam.listademateriais.model.Fabricante;
import br.com.estevam.listademateriais.model.Referencia;
import br.com.estevam.listademateriais.services.FabricanteService;

@RestController
@RequestMapping(value="REST/fabricantes")
public class FabricanteResource {
	
	@Autowired
	private FabricanteService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<FabricanteDTO>> findAll(){
		List<Fabricante> list = service.findAll();
		List<FabricanteDTO> listDTO = list.stream().map(x->new FabricanteDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<FabricanteDTO> findById(@PathVariable String id){
		Fabricante obj = service.findById(id);
		return ResponseEntity.ok().body(new FabricanteDTO(obj));
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody FabricanteDTO objDto){
		Fabricante obj = service.fromDTO(objDto);
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
	public ResponseEntity<Void> update(@RequestBody FabricanteDTO objDto,@PathVariable String id){
		Fabricante obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}/materiais", method = RequestMethod.GET)
	public ResponseEntity<List<Referencia>> findMateriais(@PathVariable String id){
		Fabricante obj = service.findById(id);
		return ResponseEntity.ok().body(obj.getReferencias().stream().collect(Collectors.toList()));
	}

}
