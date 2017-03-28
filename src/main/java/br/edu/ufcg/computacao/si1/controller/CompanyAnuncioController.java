package br.edu.ufcg.computacao.si1.controller;

import br.edu.ufcg.computacao.si1.model.Anuncio;
import br.edu.ufcg.computacao.si1.model.Usuario;
import br.edu.ufcg.computacao.si1.model.Anuncio.AnuncioBuilder;
import br.edu.ufcg.computacao.si1.model.form.AnuncioFilterForm;
import br.edu.ufcg.computacao.si1.model.form.AnuncioForm;
import br.edu.ufcg.computacao.si1.service.AnuncioServiceImpl;
import br.edu.ufcg.computacao.si1.service.UsuarioServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

@Controller
public class CompanyAnuncioController {

    @Autowired
    private AnuncioServiceImpl anuncioService;
    @Autowired
    private UsuarioServiceImpl usuarioService;

    @RequestMapping(value = "/company/cadastrar/anuncio", method = RequestMethod.GET)
    public ModelAndView getPageCadastarAnuncio(AnuncioForm anuncioForm){
        ModelAndView model = new ModelAndView();
        model.addObject("usuario", usuarioService.getLoggedUser());

        model.addObject("tipos", anuncioForm.getTiposCompany());
        model.setViewName("company/cadastrar_anuncio");

        return model;
    }

    @RequestMapping(value = {"/company/listar/anuncios", "/company/listar/resetFilter"}, method = RequestMethod.GET)
    public ModelAndView getPageListarAnuncios(){
        ModelAndView model = new ModelAndView();

        model.addObject("anuncios", anuncioService.getAnuncioRepository().findAll());
        model.addObject("usuario", usuarioService.getLoggedUser());

        model.setViewName("company/listar_anuncios");

        return model;
    }
    
    @InitBinder
    public void initBinder (WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-mm-dd"), true));
    }
    
    @RequestMapping(value = "/company/listar/filtrar", method = RequestMethod.POST)
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
        
        String type = anuncioFilterForm.getType();
        Collection<Anuncio> anunciosByType;
        if(type.equals("todos")){
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
        
        model.setViewName("company/listar_anuncios");

        return model;
    }

    @RequestMapping(value = "/company/cadastrar/anuncio", method = RequestMethod.POST)
    public ModelAndView cadastroAnuncio(@Valid AnuncioForm anuncioForm, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            return getPageCadastarAnuncio(anuncioForm);
        }

        String titulo = anuncioForm.getTitulo();
        double preco = anuncioForm.getPreco();
        String tipo = anuncioForm.getTipo();
        Usuario anunciante = usuarioService.getLoggedUser();

        Anuncio anuncio = new AnuncioBuilder(titulo,preco,tipo, anunciante).build();

        anuncioService.create(anuncio);

        attributes.addFlashAttribute("mensagem", "Anúncio cadastrado com sucesso!");
        return new ModelAndView("redirect:/company/cadastrar/anuncio");
    }
    
    @RequestMapping(value = "/company/listar/comprar/anuncio", method = RequestMethod.POST)
    public ModelAndView comprarAnuncio(RedirectAttributes attributes,
    		@RequestParam(value = "idAnuncio") long idAnuncio){   
    	
    	Anuncio anuncio = anuncioService.getById(idAnuncio).get();
    	
    	Usuario vendedor = anuncio.getAnunciante();
    	    	
    	Usuario comprador = usuarioService.getLoggedUser();
    	
    	double valorAnuncio = anuncio.getPreco();
    	
    	comprador.debitar(valorAnuncio);
    	
    	vendedor.creditar(valorAnuncio);
    	
    	anuncioService.delete(idAnuncio);
 
    	return new ModelAndView("redirect:/company/listar/anuncios");
    }


}
