package br.com.estevam.listademateriais.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import br.com.estevam.listademateriais.dto.CategoriaDTO;
import br.com.estevam.listademateriais.model.Fabricante;
import br.com.estevam.listademateriais.model.Material;
import br.com.estevam.listademateriais.model.Referencia;
import br.com.estevam.listademateriais.services.CategoriaService;
import br.com.estevam.listademateriais.services.FabricanteService;
import br.com.estevam.listademateriais.services.MaterialService;

@Controller
@RequestMapping("materiais")
public class MaterialController {
	
	@Autowired
	private MaterialService service;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private FabricanteService fabricanteService;
	
	@GetMapping("/cadastro")
	public String showCadastro(HttpSession session, Model model) {
		List<Referencia> refs = new ArrayList<>();
		session.setAttribute("refs", refs);
		List<CategoriaDTO> categorias = categoriaService.findAll().stream().map(x->new CategoriaDTO(x)).collect(Collectors.toList());
		List<Fabricante> fabricantes = fabricanteService.findAll();
		model.addAttribute("material", new Material());
		model.addAttribute("referencia", new Referencia());
		model.addAttribute("lista",categorias);
		model.addAttribute("fabricantes", fabricantes);
		return "cadastro-materiais";
	}
	
	@GetMapping(value="/editar")
	public String findById(HttpSession session, String id, Model model) {
		List<Referencia> refs = new ArrayList<>();
		
		List<CategoriaDTO> categorias = categoriaService.findAll().stream().map(x->new CategoriaDTO(x)).collect(Collectors.toList());

		Material material = service.findById(id);
		refs = material.getReferencias();
		
		List<Fabricante> fabricantes = fabricanteService.findAll();
		session.setAttribute("refs", refs);
		model.addAttribute("material", material);
		model.addAttribute("referencia", new Referencia());
		model.addAttribute("lista",categorias);
		model.addAttribute("fabricantes", fabricantes);
		return "cadastro-materiais";
	}
	
	@PostMapping(value="/salvar", params="action=Salvar")
	public String save(@SessionAttribute List<Referencia> refs, @ModelAttribute Material material, Model model) {
		
		if(material.getId()=="") {
			material.setId(null);
			service.insert(material);
			service.setReferencias(material, refs);
		}
		else {
			service.update(material);
			service.setReferencias(material, refs);
		}
		refs.clear();
		return "redirect:/materiais";
	}
	
	@PostMapping(value="/salvar", params="action=Adicionar")
	public String addReferencia(@SessionAttribute List<Referencia> refs,@ModelAttribute Material material,@ModelAttribute Referencia referencia, Model model) {
		refs.add(referencia);
		material.setReferencias(refs);
		List<CategoriaDTO> categorias = categoriaService.findAll().stream().map(x->new CategoriaDTO(x)).collect(Collectors.toList());
		List<Fabricante> fabricantes = fabricanteService.findAll();
		model.addAttribute("material", material);
		model.addAttribute("referencia", new Referencia());
		model.addAttribute("lista",categorias);
		model.addAttribute("fabricantes", fabricantes);
		return "cadastro-materiais";
	}

	@GetMapping(value="/remover")
	@ResponseBody
	public void removeReferencia(@SessionAttribute List<Referencia> refs,String index) {
		refs.remove(Integer.parseInt(index));
	}
	
	@GetMapping
	public String findAll(Model model) {
		List<Material> list = service.findAll();
		model.addAttribute("lista",list);
		return "consulta-materiais";
	}
	
	@GetMapping("/excluir")
	public String delete(String id){
		service.delete(id);
		return "redirect:/materiais";
	}
	
}
