package br.com.estevam.listademateriais.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.estevam.listademateriais.model.Usuario;
import br.com.estevam.listademateriais.services.UsuarioService;
import br.com.estevam.listademateriais.services.util.Util;

@Controller
@RequestMapping("usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService service;
	
	@GetMapping("/cadastro")
	public String showCadastro(Usuario usuario) {
		return "cadastro-usuarios";
	}
	
	@GetMapping(value="/editar")
	public String findById(String id, Model model) {
		model.addAttribute("usuario",service.findById(id));
		return "cadastro-usuarios";
	}
	
	@PostMapping("/salvar")
	public String save(Usuario usuario) {

		if(usuario.getId()=="") {
			usuario.setId(null);
			usuario.setSenha(Util.md5(usuario.getSenha()));
			service.insert(usuario);
		}
		else {
			usuario.setSenha(Util.md5(usuario.getSenha()));
			service.update(usuario);
			
		}
		return "redirect:/usuarios";
	}
	
	@GetMapping
	public String findAll(Model model) {
		List<Usuario> list = service.findAll();
		model.addAttribute("lista",list);
		return "consulta-usuarios";
	}
	
	@GetMapping("/excluir")
	public String delete(String id, RedirectAttributes ra, HttpSession session){
		if(((Usuario) session.getAttribute("usuario")).equals(service.findById(id))) {
			ra.addFlashAttribute("error","Um usuario não pode se auto-excluir");
			return "redirect:/usuarios";
		}
		
		service.delete(id);
		return "redirect:/usuarios";
	}
}
