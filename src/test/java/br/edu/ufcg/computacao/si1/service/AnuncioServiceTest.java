package br.edu.ufcg.computacao.si1.service;

import br.edu.ufcg.computacao.si1.model.Anuncio;
import br.edu.ufcg.computacao.si1.model.Notas;
import br.edu.ufcg.computacao.si1.model.RazaoSocial;
import br.edu.ufcg.computacao.si1.model.TipoAnuncio;
import br.edu.ufcg.computacao.si1.model.Usuario;
import br.edu.ufcg.computacao.si1.repository.AnuncioRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static junit.framework.TestCase.*;

/**
 * Created by Marcus Oliveira on 28/12/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AnuncioServiceTest {

    @Autowired
    private AnuncioService anuncioService;

    @Autowired
    private AnuncioRepository anuncioRepository;

    private Anuncio anuncio1, anuncio2, anuncio3;

    private Usuario anunciante;


    @Before
    public void setUp() {
        anunciante = new Usuario("Gabriel", "gabrielmaracaj@gmail.com", "gabriel", RazaoSocial.USER);
        anuncio1 = new Anuncio("Anuncio de Movel", new Date(), 100, Notas.ESTRELA2, TipoAnuncio.MOVEL, anunciante);
        anuncio2 = new Anuncio("Anuncio de Imovel", new Date(), 100000, Notas.ESTRELA3, TipoAnuncio.IMOVEL, anunciante);
        anuncio3 = new Anuncio("Anuncio de Emprego", new Date(), 0, Notas.ESTRELA1, TipoAnuncio.EMPREGO, anunciante);
    }

    @After
    public void tearDown() {
        anuncioRepository.deleteAll();
    }

    @Test
    public void testInicializacao() {
        assertNotNull("AnuncioService não foi instânciado corretamente", anuncioService);
        assertTrue("AnuncioService não deveria conter nenhum item", anuncioService.getAll().isEmpty());
    }


    @Test
    public void testCreateAd() {
        Anuncio anuncio1FromDB = anuncioService.create(anuncio1);
        Anuncio anuncio2FromDB = anuncioService.create(anuncio2);
        Anuncio anuncio3FromDB = anuncioService.create(anuncio3);

        assertNotNull(anuncio1FromDB);
        assertNotNull(anuncio2FromDB);
        assertNotNull(anuncio3FromDB);

        assertTrue(anuncioRepository.exists(anuncio1FromDB.getId()));
        assertTrue(anuncioRepository.exists(anuncio2FromDB.getId()));
        assertTrue(anuncioRepository.exists(anuncio3FromDB.getId()));

        assertEquals(anuncio1FromDB, anuncio1);
        assertEquals(anuncio2FromDB, anuncio2);
        assertEquals(anuncio3FromDB, anuncio3);

        assertEquals(anuncio1FromDB, anuncioService.getById(anuncio1FromDB.getId()).get());
        assertEquals(anuncio2FromDB, anuncioService.getById(anuncio2FromDB.getId()).get());
        assertEquals(anuncio3FromDB, anuncioService.getById(anuncio3FromDB.getId()).get());
    }

    @Test
    public void testGetPorTipo() {

        int QTDE_ANUNCIOS_ESPERADA = 1;

        Anuncio anuncioMovel = anuncioService.create(anuncio1);
        Anuncio anuncioImovel = anuncioService.create(anuncio2);
        Anuncio anuncioEmprego = anuncioService.create(anuncio3);

        assertNotNull(anuncioMovel);
        assertNotNull(anuncioImovel);
        assertNotNull(anuncioEmprego);

        assertEquals(anuncioMovel.getTipo(), "movel");
        assertEquals(anuncioImovel.getTipo(), "imovel");
        assertEquals(anuncioEmprego.getTipo(), "emprego");

        assertEquals(QTDE_ANUNCIOS_ESPERADA, anuncioService.getByType(TipoAnuncio.MOVEL).size());
        assertEquals(QTDE_ANUNCIOS_ESPERADA, anuncioService.getByType(TipoAnuncio.IMOVEL).size());
        assertEquals(QTDE_ANUNCIOS_ESPERADA, anuncioService.getByType(TipoAnuncio.EMPREGO).size());

        assertTrue(anuncioService.getByType(TipoAnuncio.MOVEL).contains(anuncioMovel));
        assertTrue(anuncioService.getByType(TipoAnuncio.IMOVEL).contains(anuncioImovel));
        assertTrue(anuncioService.getByType(TipoAnuncio.EMPREGO).contains(anuncioEmprego));
    }

    @Test
    public void testGetAll() {
        int QTDE_ANUNCIOS_ESPERADA = 3;

        Anuncio anuncioMovel = anuncioService.create(anuncio1);
        Anuncio anuncioImovel = anuncioService.create(anuncio2);
        Anuncio anuncioEmprego = anuncioService.create(anuncio3);

        assertNotNull(anuncioMovel);
        assertNotNull(anuncioImovel);
        assertNotNull(anuncioEmprego);

        assertEquals(QTDE_ANUNCIOS_ESPERADA, anuncioService.getAll().size());

        assertTrue(anuncioService.getAll().contains(anuncioMovel));
        assertTrue(anuncioService.getAll().contains(anuncioImovel));
        assertTrue(anuncioService.getAll().contains(anuncioEmprego));

    }

    @Test
    public void testDelete() {
        int QTDE_ANUNCIOS_ESPERADA = 3;

        Anuncio anuncioMovel = anuncioService.create(anuncio1);
        Anuncio anuncioImovel = anuncioService.create(anuncio2);
        Anuncio anuncioEmprego = anuncioService.create(anuncio3);

        assertEquals(QTDE_ANUNCIOS_ESPERADA, anuncioService.getAll().size());
        assertTrue(anuncioService.getAll().contains(anuncioMovel));
        assertTrue(anuncioService.getAll().contains(anuncioImovel));
        assertTrue(anuncioService.getAll().contains(anuncioEmprego));

        assertTrue(anuncioService.delete(anuncioMovel.getId()));
        QTDE_ANUNCIOS_ESPERADA-=1;
        assertEquals(QTDE_ANUNCIOS_ESPERADA, anuncioService.getAll().size());
        assertFalse(anuncioService.getAll().contains(anuncioMovel));
        assertTrue(anuncioService.getAll().contains(anuncioImovel));
        assertTrue(anuncioService.getAll().contains(anuncioEmprego));

        assertTrue(anuncioService.delete(anuncioImovel.getId()));
        QTDE_ANUNCIOS_ESPERADA-=1;
        assertEquals(QTDE_ANUNCIOS_ESPERADA, anuncioService.getAll().size());
        assertFalse(anuncioService.getAll().contains(anuncioMovel));
        assertFalse(anuncioService.getAll().contains(anuncioImovel));
        assertTrue(anuncioService.getAll().contains(anuncioEmprego));

        assertTrue(anuncioService.delete(anuncioEmprego.getId()));
        QTDE_ANUNCIOS_ESPERADA-=1;
        assertEquals(QTDE_ANUNCIOS_ESPERADA, anuncioService.getAll().size());
        assertFalse(anuncioService.getAll().contains(anuncioMovel));
        assertFalse(anuncioService.getAll().contains(anuncioImovel));
        assertFalse(anuncioService.getAll().contains(anuncioEmprego));

        assertEquals(0, QTDE_ANUNCIOS_ESPERADA);

        assertFalse(anuncioService.delete(anuncioMovel.getId()));
        assertFalse(anuncioService.delete(anuncioImovel.getId()));
        assertFalse(anuncioService.delete(anuncioEmprego.getId()));
    }

    @Test
    public void testUpdate() {

        String SUFIXO = " editado";

        Anuncio anuncioMovel = anuncioService.create(anuncio1);
        Anuncio anuncioImovel = anuncioService.create(anuncio2);
        Anuncio anuncioEmprego = anuncioService.create(anuncio3);

        assertEquals(anuncioMovel, anuncio1);
        assertEquals(anuncioImovel, anuncio2);
        assertEquals(anuncioEmprego, anuncio3);

        //Update Titulo
        anuncioMovel.setTitulo(anuncioMovel.getTitulo()+SUFIXO);
        anuncioImovel.setTitulo(anuncioImovel.getTitulo()+SUFIXO);
        anuncioEmprego.setTitulo(anuncioEmprego.getTitulo()+SUFIXO);

        assertTrue(anuncioService.update(anuncioMovel));
        assertTrue(anuncioService.update(anuncioImovel));
        assertTrue(anuncioService.update(anuncioEmprego));

        assertEquals(anuncioMovel.getTitulo(), anuncioService.getById(anuncioMovel.getId()).get().getTitulo());
        assertEquals(anuncioImovel.getTitulo(), anuncioService.getById(anuncioImovel.getId()).get().getTitulo());
        assertEquals(anuncioEmprego.getTitulo(), anuncioService.getById(anuncioEmprego.getId()).get().getTitulo());

        //Update preço
        anuncioMovel.setPreco(anuncioMovel.getPreco()*2);
        anuncioImovel.setPreco(anuncioImovel.getPreco()*2);
        anuncioEmprego.setPreco(anuncioEmprego.getPreco()*2);

        assertTrue(anuncioService.update(anuncioMovel));
        assertTrue(anuncioService.update(anuncioImovel));
        assertTrue(anuncioService.update(anuncioEmprego));

        assertEquals(anuncioMovel.getPreco(), anuncioService.getById(anuncioMovel.getId()).get().getPreco());
        assertEquals(anuncioImovel.getPreco(), anuncioService.getById(anuncioImovel.getId()).get().getPreco());
        assertEquals(anuncioEmprego.getPreco(), anuncioService.getById(anuncioEmprego.getId()).get().getPreco());

        //Update nota
        anuncioMovel.setNota(Notas.ESTRELA4);
        anuncioImovel.setNota(Notas.ESTRELA4);
        anuncioEmprego.setNota(Notas.ESTRELA4);

        assertTrue(anuncioService.update(anuncioMovel));
        assertTrue(anuncioService.update(anuncioImovel));
        assertTrue(anuncioService.update(anuncioEmprego));

        assertEquals(Notas.ESTRELA4, anuncioService.getById(anuncioMovel.getId()).get().getNota());
        assertEquals(Notas.ESTRELA4, anuncioService.getById(anuncioImovel.getId()).get().getNota());
        assertEquals(Notas.ESTRELA4, anuncioService.getById(anuncioEmprego.getId()).get().getNota());
    }

}
