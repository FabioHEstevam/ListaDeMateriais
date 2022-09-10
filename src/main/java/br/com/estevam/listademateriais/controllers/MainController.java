package br.com.estevam.listademateriais.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.estevam.listademateriais.model.Usuario;
import br.com.estevam.listademateriais.services.UsuarioService;
import br.com.estevam.listademateriais.services.util.Util;

@Controller
public class MainController {
	
	@Autowired
	private UsuarioService service;
	
	@GetMapping("/")
	public String showIndex(Usuario usuario) {
		return "index";
	}
	
	@GetMapping("/home")
	public String showHome() {
		return "home";
	}
	
	@PostMapping("/login")
	public String login(Usuario usuario, RedirectAttributes ra, HttpSession session) {
		
		Usuario authUsuario = service.findByEmail(usuario.getEmail());
		
		if(authUsuario!=null && Util.md5(usuario.getSenha()).equals(authUsuario.getSenha())) {
			session.setAttribute("usuario", authUsuario);
			return "redirect:/home";
		}
		
		ra.addFlashAttribute("error", "E-mail ou senha inválios");
		return "redirect:/";
	}
	
	@PostMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	

}
