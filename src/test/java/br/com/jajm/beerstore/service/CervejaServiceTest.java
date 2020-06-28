package br.com.jajm.beerstore.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.jajm.beerstore.domain.model.Cerveja;
import br.com.jajm.beerstore.domain.model.TipoCerveja;
import br.com.jajm.beerstore.domain.service.CervejaService;
import br.com.jajm.beerstore.domain.service.exception.CervejaExistenteException;
import br.com.jajm.beerstore.infra.repository.CervejaRepository;

public class CervejaServiceTest {
	
	private CervejaService cervejaService;
	
	@Mock
	private CervejaRepository cervejaRepositoryMock;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		cervejaService = new CervejaService(cervejaRepositoryMock);
	}
	
	@Test(expected = CervejaExistenteException.class)	
	public void deve_negar_criacao_de_cerveja_existente( ) {
		Cerveja cervejaNoBancoDeDados = new Cerveja();
		cervejaNoBancoDeDados.setCodigo(10L);
		cervejaNoBancoDeDados.setNome("Skol");
		cervejaNoBancoDeDados.setTipo(TipoCerveja.PILSEN);
		cervejaNoBancoDeDados.setVolume(new BigDecimal("350"));
		
		when(cervejaRepositoryMock.findByNomeAndTipo("Skol", TipoCerveja.PILSEN)).thenReturn(Optional.of(cervejaNoBancoDeDados));
		
		Cerveja cerveja = new Cerveja();		
		cerveja.setNome("Skol");
		cerveja.setTipo(TipoCerveja.PILSEN);
		cerveja.setVolume(new BigDecimal("350"));
		
		cerveja = cervejaService.salvar(cerveja);
	}
	
	@Test
	public void deve_criar_nova_cerveja() {
		Cerveja cerveja = new Cerveja();		
		cerveja.setNome("Brhama");
		cerveja.setTipo(TipoCerveja.PILSEN);
		cerveja.setVolume(new BigDecimal("350"));
		
		Cerveja cervejaNoBancoDeDados = new Cerveja();
		cervejaNoBancoDeDados.setCodigo(10L);
		cervejaNoBancoDeDados.setNome("Brhama");
		cervejaNoBancoDeDados.setTipo(TipoCerveja.PILSEN);
		cervejaNoBancoDeDados.setVolume(new BigDecimal("350"));		
		when(cervejaRepositoryMock.save(cerveja)).thenReturn(cervejaNoBancoDeDados);
		
		cerveja = cervejaService.salvar(cerveja);
		
		assertThat(cerveja.getCodigo(), equalTo(10L));
		assertThat(cerveja.getNome(), equalTo("Brhama"));
		assertThat(cerveja.getTipo(), equalTo(TipoCerveja.PILSEN));
	}

}
