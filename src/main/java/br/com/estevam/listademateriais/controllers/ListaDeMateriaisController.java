package br.com.estevam.listademateriais.controllers;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.estevam.listademateriais.dto.AutorDTO;
import br.com.estevam.listademateriais.dto.CategoriaDTO;
import br.com.estevam.listademateriais.dto.FabricanteDTO;
import br.com.estevam.listademateriais.dto.MaterialDTO;
import br.com.estevam.listademateriais.model.ItemDaListaDeMateriais;
import br.com.estevam.listademateriais.model.ListaDeMateriais;
import br.com.estevam.listademateriais.model.Material;
import br.com.estevam.listademateriais.model.Referencia;
import br.com.estevam.listademateriais.model.Unidade;
import br.com.estevam.listademateriais.model.Usuario;
import br.com.estevam.listademateriais.services.CategoriaService;
import br.com.estevam.listademateriais.services.FabricanteService;
import br.com.estevam.listademateriais.services.ListaDeMateriaisService;
import br.com.estevam.listademateriais.services.MaterialService;
import br.com.estevam.listademateriais.services.UnidadeService;

@Controller
@RequestMapping("listas")
public class ListaDeMateriaisController {

	@Autowired
	private ListaDeMateriaisService service;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private MaterialService materialService;
	
	@Autowired
	private FabricanteService fabricanteService;
	
	@Autowired
	private UnidadeService unidadeService;
	
	@GetMapping
	public String findAll(Model model) {
		List<ListaDeMateriais> list = service.findAll();
		model.addAttribute("lista",list);
		return "consulta-listas";
	}
	
	@GetMapping("/cadastro")
	public String showCadastro(HttpSession session, @SessionAttribute Usuario usuario, Model model) {

		List<CategoriaDTO> categorias = categoriaService.findAll().stream().map(x->new CategoriaDTO(x)).collect(Collectors.toList());
		List<FabricanteDTO> fabricantes = fabricanteService.findAll().stream().map(x->new FabricanteDTO(x)).collect(Collectors.toList());
		List<MaterialDTO> materiais = materialService.findAll().stream().map(x->new MaterialDTO(x)).collect(Collectors.toList());
		List<Unidade> unidades = unidadeService.findAll();
		
		ListaDeMateriais projeto = new ListaDeMateriais();
		projeto.setId(null);
		projeto.setAutor(new AutorDTO(usuario));
		
		session.setAttribute("prototipo", projeto);
		model.addAttribute("referencia", new ArrayList<Referencia>());
		model.addAttribute("projeto", projeto);
		model.addAttribute("materiais", materiais);
		model.addAttribute("categorias",categorias);
		model.addAttribute("fabricantes", fabricantes);
		model.addAttribute("unidades", unidades);
		return "cadastro-listas";
	}
	
	@GetMapping(value="/editar")
	public String findById(HttpSession session, String id, Model model) {
		
		List<CategoriaDTO> categorias = categoriaService.findAll().stream().map(x->new CategoriaDTO(x)).collect(Collectors.toList());
		List<FabricanteDTO> fabricantes = fabricanteService.findAll().stream().map(x->new FabricanteDTO(x)).collect(Collectors.toList());
		List<MaterialDTO> materiais = materialService.findAll().stream().map(x->new MaterialDTO(x)).collect(Collectors.toList());
		List<Unidade> unidades = unidadeService.findAll();
		
		ListaDeMateriais projeto = service.findById(id);
		
		session.setAttribute("prototipo", projeto);
		model.addAttribute("referencia", new ArrayList<Referencia>());
		model.addAttribute("projeto", projeto);
		model.addAttribute("materiais", materiais);
		model.addAttribute("categorias",categorias);
		model.addAttribute("fabricantes", fabricantes);
		model.addAttribute("unidades", unidades);

		return "cadastro-listas";
	}
	
	@PostMapping(value="/salvar", params="action=Salvar")
	public String save(@SessionAttribute ListaDeMateriais prototipo, @ModelAttribute ListaDeMateriais projeto, Model model) {
		
		projeto.setAutor(prototipo.getAutor());
			
		if(projeto.getId()==""||projeto.getId()==null) {
			projeto.setId(null);
			projeto.getLista().clear();
			projeto.getLista().addAll(prototipo.getLista());
			service.insert(projeto);
		}
		else {
			projeto.getLista().addAll(prototipo.getLista());
			service.update(projeto);
		}

		return "redirect:/listas";
	}
	
	@GetMapping("/excluir")
	public String delete(String id){
		service.delete(id);
		return "redirect:/listas";
	}
	
	@GetMapping("/materiais")
	@ResponseBody
	public List<MaterialDTO> delete(){
		return materialService.findAll().stream().map(x->new MaterialDTO(x)).collect(Collectors.toList());
	}
	
	
	@PostMapping("/filtrar")
	@ResponseBody
	public List<MaterialDTO> filtro(@RequestBody String categorias) throws JsonMappingException, JsonProcessingException, UnsupportedEncodingException{
		String decodedString = java.net.URLDecoder.decode(categorias, "UTF-8");
		String[] ids = new ObjectMapper().readValue(decodedString, String[].class);  	
		List<Material> materiais = materialService.findByCategorias(Arrays.asList(ids).stream().map(x->new CategoriaDTO(x)).collect(Collectors.toList()));
		return materiais.stream().map(x->new MaterialDTO(x)).collect(Collectors.toList());
	}
	
	@GetMapping("/referencias")
	@ResponseBody
	public List<Referencia> referencias(String id){
		return materialService.findById(id).getReferencias();
	}
	
	@PostMapping(value = "/salvar-item")
	@ResponseBody
	public List<ItemDaListaDeMateriais> salvarItem(@SessionAttribute ListaDeMateriais prototipo, @RequestBody String item) throws UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
		
		String decodedString = java.net.URLDecoder.decode(item, "UTF-8");
		
		ItemDaListaDeMateriais newItem = new ObjectMapper().readValue(decodedString, ItemDaListaDeMateriais.class);
		
		newItem.getReferencia().setMaterial(new MaterialDTO(materialService.findById(newItem.getReferencia().getMaterial().getId())));
		newItem.getReferencia().setFabricante(new FabricanteDTO(fabricanteService.findById(newItem.getReferencia().getFabricante().getId())));
		newItem.setUnidade(unidadeService.findById(newItem.getUnidade().getId()));
	
		if(prototipo.getLista().contains(newItem)) {
			prototipo.getLista().remove(newItem);
		};
		
		prototipo.getLista().add(newItem);

		return prototipo.getLista().stream().collect(Collectors.toList());
	}
	
	@PostMapping(value = "/obter-item")
	@ResponseBody
	public ItemDaListaDeMateriais getItem(@SessionAttribute ListaDeMateriais prototipo, @RequestBody String item) throws UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
		
		String decodedString = java.net.URLDecoder.decode(item, "UTF-8");
		
		ItemDaListaDeMateriais newItem = new ObjectMapper().readValue(decodedString, ItemDaListaDeMateriais.class);
		
		newItem.getReferencia().setMaterial(new MaterialDTO(materialService.findById(newItem.getReferencia().getMaterial().getId())));
		newItem.getReferencia().setFabricante(new FabricanteDTO(fabricanteService.findById(newItem.getReferencia().getFabricante().getId())));
		newItem.setUnidade(unidadeService.findById(newItem.getUnidade().getId()));

		return prototipo.getLista().stream().filter(x->x.equals(newItem)).findAny().orElseThrow();
	}
	
	@PostMapping(value = "/remover-item")
	@ResponseBody
	public List<ItemDaListaDeMateriais> removerItem(@SessionAttribute ListaDeMateriais prototipo, @RequestBody String item) throws UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
		
		String decodedString = java.net.URLDecoder.decode(item, "UTF-8");
		
		ItemDaListaDeMateriais newItem = new ObjectMapper().readValue(decodedString, ItemDaListaDeMateriais.class);
		
		prototipo.getLista().remove(newItem);
		
		return prototipo.getLista().stream().collect(Collectors.toList());
		
	}
	
	
}
