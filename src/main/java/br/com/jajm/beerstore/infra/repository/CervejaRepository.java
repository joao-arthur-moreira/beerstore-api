package br.com.jajm.beerstore.infra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jajm.beerstore.domain.model.Cerveja;
import br.com.jajm.beerstore.domain.model.TipoCerveja;

public interface CervejaRepository extends JpaRepository<Cerveja, Long> {
	
	Optional<Cerveja> findByNomeAndTipo(String nome, TipoCerveja tipo);

}
