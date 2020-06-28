package br.com.jajm.beerstore.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jajm.beerstore.domain.model.Cerveja;
import br.com.jajm.beerstore.domain.service.exception.CervejaExistenteException;
import br.com.jajm.beerstore.infra.repository.CervejaRepository;

@Service
public class CervejaService {
	
	private CervejaRepository cervejaRepository;
	
	public CervejaService(@Autowired CervejaRepository cervejaRepository) {
		this.cervejaRepository = cervejaRepository;
	}
	
	public Cerveja salvar(Cerveja cerveja) {
		verifiacarCervejaExistente(cerveja);		
		return cervejaRepository.save(cerveja);		
	}

	private void verifiacarCervejaExistente(Cerveja cerveja) {
		Optional<Cerveja> cervejaExistenteOptional = cervejaRepository.findByNomeAndTipo(cerveja.getNome(), cerveja.getTipo());
		
		if (cervejaExistenteOptional.isPresent() && (cerveja.isNova() || isAtualizandoUmaCervejaDiferente(cerveja, cervejaExistenteOptional)) ) {			
			throw new CervejaExistenteException();
		}
	}
	
	private boolean isAtualizandoUmaCervejaDiferente(Cerveja cerveja, Optional<Cerveja> cervejaPorNomeETipo) {
		return cerveja.existente() && !cervejaPorNomeETipo.get().equals(cerveja);
	}

}
