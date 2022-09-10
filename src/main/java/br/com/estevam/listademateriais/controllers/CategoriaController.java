package br.com.estevam.listademateriais.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.estevam.listademateriais.model.Categoria;
import br.com.estevam.listademateriais.services.CategoriaService;

@Controller
@RequestMapping("categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService service;
	
	@GetMapping("/cadastro")
	public String showCadastro(Categoria categoria, Model model) {
		model.addAttribute("lista", service.findAll());
		return "cadastro-categorias";
	}
	
	@GetMapping(value="/editar")
	public String findById(String id, Model model) {
		model.addAttribute("lista", service.findAll());
		model.addAttribute("categoria",service.findById(id));
		return "cadastro-categorias";
	}
	
	@PostMapping("/salvar")
	public String save(Categoria categoria) {
		
		//Categoria obj = service.fromDTO(categoria);
		if(categoria.getId()=="") {
			categoria.setId(null);
			service.insert(categoria);
		}
		else {
			service.update(categoria);	
		}
		return "redirect:/categorias";
	}
	
	@GetMapping
	public String findAll(Model model) {
		List<Categoria> list = service.findAll();
		model.addAttribute("lista",list);
		return "consulta-categorias";
	}
	
	@GetMapping("/excluir")
	public String delete(String id){
		service.delete(id);
		return "redirect:/categorias";
	}
}
