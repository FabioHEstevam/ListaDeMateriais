package br.com.estevam.listademateriais.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.estevam.listademateriais.model.Fabricante;
import br.com.estevam.listademateriais.services.FabricanteService;

@Controller
@RequestMapping("fabricantes")
public class FabricanteController {

	@Autowired
	private FabricanteService service;
	
	@GetMapping("/cadastro")
	public String showCadastro(Fabricante fabricante) {
		return "cadastro-fabricantes";
	}
	
	@GetMapping(value="/editar")
	public String findById(String id, Model model) {
		model.addAttribute("fabricante",service.findById(id));
		return "cadastro-fabricantes";
	}
	
	@PostMapping("/salvar")
	public String save(Fabricante fabricante) {

		if(fabricante.getId()=="") {
			fabricante.setId(null);
			service.insert(fabricante);
			System.out.println(fabricante);
		}
		else {
			service.update(fabricante);
			
		}
		return "redirect:/fabricantes";
	}
	
	@GetMapping
	public String findAll(Model model) {
		List<Fabricante> list = service.findAll();
		model.addAttribute("lista",list);
		return "consulta-fabricantes";
	}
	
	@GetMapping("/excluir")
	public String delete(String id){
		service.delete(id);
		return "redirect:/fabricantes";
	}
}
