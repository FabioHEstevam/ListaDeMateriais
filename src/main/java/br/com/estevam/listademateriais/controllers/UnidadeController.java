package br.com.estevam.listademateriais.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.estevam.listademateriais.model.Unidade;
import br.com.estevam.listademateriais.services.UnidadeService;

@Controller
@RequestMapping("unidades")
public class UnidadeController {

	@Autowired
	private UnidadeService service;
	
	@GetMapping("/cadastro")
	public String showCadastro(Unidade unidade) {
		return "cadastro-unidades";
	}
	
	@GetMapping(value="/editar")
	public String findById(String id, Model model) {
		model.addAttribute("unidade",service.findById(id));
		return "cadastro-unidades";
	}
	
	@PostMapping("/salvar")
	public String save(Unidade unidade) {

		if(unidade.getId()=="") {
			unidade.setId(null);
			service.insert(unidade);
			System.out.println(unidade);
		}
		else {
			service.update(unidade);
			
		}
		return "redirect:/unidades";
	}
	
	@GetMapping
	public String findAll(Model model) {
		List<Unidade> list = service.findAll();
		model.addAttribute("lista",list);
		return "consulta-unidades";
	}
	
	@GetMapping("/excluir")
	public String delete(String id){
		service.delete(id);
		return "redirect:/unidades";
	}
}
