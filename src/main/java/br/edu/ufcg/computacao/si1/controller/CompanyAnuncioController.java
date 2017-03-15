package br.edu.ufcg.computacao.si1.controller;

import br.edu.ufcg.computacao.si1.model.Anuncio;
import br.edu.ufcg.computacao.si1.model.Usuario;
import br.edu.ufcg.computacao.si1.model.Anuncio.AnuncioBuilder;
import br.edu.ufcg.computacao.si1.model.form.AnuncioForm;
import br.edu.ufcg.computacao.si1.service.AnuncioServiceImpl;
import br.edu.ufcg.computacao.si1.service.UsuarioServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addObject("usuario", usuarioService.getUsuarioLogado());

        model.addObject("tipos", anuncioForm.getTipos());
        model.setViewName("company/cadastrar_anuncio");

        return model;
    }

    @RequestMapping(value = "/company/listar/anuncios", method = RequestMethod.GET)
    public ModelAndView getPageListarAnuncios(){
        ModelAndView model = new ModelAndView();

        model.addObject("anuncios", anuncioService.getAnuncioRepository().findAll());
        model.addObject("usuario", usuarioService.getUsuarioLogado());

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
        Usuario anunciante = usuarioService.getUsuarioLogado();

        Anuncio anuncio = new AnuncioBuilder(titulo,preco,tipo, anunciante).build();

        anuncioService.create(anuncio);

        attributes.addFlashAttribute("mensagem", "Anúncio cadastrado com sucesso!");
        return new ModelAndView("redirect:/company/cadastrar/anuncio");
    }


}
