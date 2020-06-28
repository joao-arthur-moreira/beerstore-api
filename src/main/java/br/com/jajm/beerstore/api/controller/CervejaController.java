package br.com.jajm.beerstore.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.jajm.beerstore.domain.model.Cerveja;
import br.com.jajm.beerstore.domain.service.CervejaService;
import br.com.jajm.beerstore.domain.service.exception.CervejaNaoEncontradaException;
import br.com.jajm.beerstore.infra.repository.CervejaRepository;

@RestController
@RequestMapping(path = "/cervejas")
public class CervejaController {
	
	@Autowired
	private CervejaRepository cervejaRepository;
	
	@Autowired
	private CervejaService cervejaService;
	
	@GetMapping
	public List<Cerveja> listarTodas() {
		return cervejaRepository.findAll();
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Cerveja adicionar(@Valid @RequestBody Cerveja cerveja) {
		return cervejaService.salvar(cerveja);
	}
	
	@PutMapping("/{codigo}")
	public Cerveja atualizar(@PathVariable Long codigo, @Valid @RequestBody Cerveja cerveja) {
		cerveja.setCodigo(codigo);
		return cervejaService.salvar(cerveja);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		try {
			cervejaRepository.deleteById(codigo);
		} catch(EmptyResultDataAccessException e) {
			throw new CervejaNaoEncontradaException();
		}
	}

}
