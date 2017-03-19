package br.edu.ufcg.computacao.si1.controller;

import br.edu.ufcg.computacao.si1.model.RazaoSocial;
import br.edu.ufcg.computacao.si1.model.Usuario;
import br.edu.ufcg.computacao.si1.service.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebPagesController {

	@Autowired
	private UsuarioServiceImpl usuarioService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getPageIndex() {

		if (usuarioService.userIsLogged()) {

			return redirectToIndex();

		} else {

			ModelAndView model = new ModelAndView();
			model.setViewName("index");

			return model;
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView getPageLogin() {
		
		ModelAndView model = new ModelAndView();
		
		if(usuarioService.userIsLogged()){
			
			return redirectToIndex();
		}
		
		model.setViewName("login");

		return model;
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ModelAndView getPageIndexUser() {
		
		ModelAndView model = new ModelAndView();
		
		model.addObject("usuario", usuarioService.getLoggedUser());
		model.setViewName("user/index");
		
		return model;
	}

	@RequestMapping(value = "/company", method = RequestMethod.GET)
	public ModelAndView getPageIndexCompany() {
		
		ModelAndView model = new ModelAndView();
		
		model.addObject("usuario", usuarioService.getLoggedUser());
		model.setViewName("company/index");

		return model;
	}

	private ModelAndView redirectToIndex() {

		Usuario user = usuarioService.getLoggedUser();

		String roleRedirect = user.getRole().name();

		return new ModelAndView("redirect:/" + roleRedirect.toLowerCase());

	}

}
