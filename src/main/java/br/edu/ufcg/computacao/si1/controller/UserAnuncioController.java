package br.edu.ufcg.computacao.si1.controller;

import br.edu.ufcg.computacao.si1.model.Anuncio;
import br.edu.ufcg.computacao.si1.model.RazaoSocial;
import br.edu.ufcg.computacao.si1.model.TipoAnuncio;
import br.edu.ufcg.computacao.si1.model.Anuncio.AnuncioBuilder;
import br.edu.ufcg.computacao.si1.model.Usuario;
import br.edu.ufcg.computacao.si1.model.form.AnuncioFilterForm;
import br.edu.ufcg.computacao.si1.model.form.AnuncioForm;
import br.edu.ufcg.computacao.si1.repository.AnuncioRepository;
import br.edu.ufcg.computacao.si1.repository.UsuarioRepository;
import br.edu.ufcg.computacao.si1.service.AnuncioServiceImpl;
import br.edu.ufcg.computacao.si1.service.UsuarioServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@Controller
public class UserAnuncioController {

    @Autowired
    private AnuncioServiceImpl anuncioService;
    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private AnuncioRepository anuncioRep;

    @RequestMapping(value = "/user/cadastrar/anuncio", method = RequestMethod.GET)
    public ModelAndView getPageCadastrarAnuncio(AnuncioForm anuncioForm){
        ModelAndView model = new ModelAndView();

        Usuario loggedUser = usuarioService.getLoggedUser();
        
        if(loggedUser.getRole().equals(RazaoSocial.COMPANY)){
        	model.addObject("tiposAnuncio", anuncioForm.PERM_COMPANY);
        }
        else if(loggedUser.getRole().equals(RazaoSocial.USER)){
        	model.addObject("tiposAnuncio", anuncioForm.PERM_USER);
        }
        
        model.addObject("usuario", loggedUser);
        model.setViewName("user/cadastrar_anuncio");

        return model;
    }

    @RequestMapping(value = {"/user/listar/anuncios","/user/listar/resetFilter"}, method = RequestMethod.GET)
    public ModelAndView getPageListarAnuncios(){
        ModelAndView model = new ModelAndView();
        
        model.addObject("tiposAnuncio", AnuncioForm.ALL);
        model.addObject("anuncios", anuncioRep.findAll());
        model.addObject("usuario", usuarioService.getLoggedUser());
       
        model.setViewName("user/listar_anuncios");

        return model;
    }
    
    @InitBinder
    public void initBinder (WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-mm-dd"), true));
    }
    
    @RequestMapping(value = "/user/listar/filtrar", method = RequestMethod.POST)
    public ModelAndView filterPageAnuncios(@Valid AnuncioFilterForm anuncioFilterForm){
    	
        ModelAndView model = new ModelAndView();
        
        Collection<Anuncio> anuncios;
        
        if(anuncioFilterForm.shouldShowOwned()){
        	long idUsuario = anuncioFilterForm.getIdUsuario();
        	anuncios = anuncioService.getByUserId(idUsuario);
        }
        else{
        	anuncios = anuncioService.getAll();
        }
        
        TipoAnuncio type = anuncioFilterForm.getType();
        
        Collection<Anuncio> anunciosByType;
        if(type.equals(TipoAnuncio.TODOS)){
        	anunciosByType = anuncios;
        }else{
        	
        	anunciosByType = anuncioService.getByType(type);
        }
        
        Date from = anuncioFilterForm.getFromDate();
        Date until = anuncioFilterForm.getToDate();
        
        Collection<Anuncio> anunciosByDate = anuncioService.getByDateBetween(from, until);
        
        anuncios.retainAll(anunciosByDate);
        
        anuncios.retainAll(anunciosByType);
        
        model.addObject("usuario", usuarioService.getLoggedUser());
        model.addObject("anuncios", anuncios);
        
        model.setViewName("user/listar_anuncios");

        return model;
    }

    @RequestMapping(value = "/user/cadastrar/anuncio", method = RequestMethod.POST)
    public ModelAndView cadastroAnuncio(@Valid AnuncioForm anuncioForm, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            return getPageCadastrarAnuncio(anuncioForm);
        }
        
        String titulo = anuncioForm.getTitulo();
        double preco = anuncioForm.getPreco();
        TipoAnuncio tipo = anuncioForm.getTipo();
        Usuario anunciante = usuarioService.getLoggedUser();

        Anuncio anuncio = new AnuncioBuilder(titulo,preco,tipo, anunciante).build();

        anuncioService.create(anuncio);

        attributes.addFlashAttribute("mensagem", "Anúncio cadastrado com sucesso!");
        return new ModelAndView("redirect:/user/cadastrar/anuncio");
    }
    
    @RequestMapping(value = "/user/listar/comprar/anuncio", method = RequestMethod.POST)
    public ModelAndView comprarAnuncio(RedirectAttributes attributes,
    		@RequestParam(value = "idAnuncio") long idAnuncio){   
    	
    	Anuncio anuncio = anuncioService.getById(idAnuncio).get();
    	
    	Usuario vendedor = anuncio.getAnunciante();
    	    	
    	Usuario comprador = usuarioService.getLoggedUser();
    	
    	double valorAnuncio = anuncio.getPreco();
    	
    	if(vendedor.equals(comprador)){
    		
    		return new ModelAndView("redirect:/user/listar/anuncios");
    	}
    	
    	if (anuncio.getTipo().equals("emprego")) {
			vendedor.debitar(valorAnuncio);
			comprador.creditar(valorAnuncio);
		}
    	else{
			comprador.debitar(valorAnuncio);
			vendedor.creditar(valorAnuncio);
    	}
    	
		anuncioService.delete(idAnuncio);
 
    	return new ModelAndView("redirect:/user/listar/anuncios");
    }
    
    @RequestMapping(value = "/oi", method = RequestMethod.POST)
    public ModelAndView comprarAnuncioServico(RedirectAttributes attributes,
    		@RequestParam(value = "idAnuncio") long idAnuncio, @RequestParam(value ="data") Date data){
    	
    	System.out.println("oi");
    	System.out.println(data.toString());
    	
    	Anuncio anuncio = anuncioService.getById(idAnuncio).get();
    	
    	Usuario vendedor = anuncio.getAnunciante();
    	    	
    	Usuario comprador = usuarioService.getLoggedUser();
    	
    	double valorAnuncio = anuncio.getPreco();
    	
    	if(vendedor.equals(comprador)){
    		
    		return new ModelAndView("redirect:/user/listar/anuncios");
    	}
    	
    	if (anuncio.getTipo().equals("emprego")) {
			vendedor.debitar(valorAnuncio);
			comprador.creditar(valorAnuncio);
		}
    	else{
			comprador.debitar(valorAnuncio);
			vendedor.creditar(valorAnuncio);
    	}
    	
		anuncioService.delete(idAnuncio);
		
		//criar serviço
 
    	return new ModelAndView("redirect:/user/listar/anuncios");
    	
    }

}
