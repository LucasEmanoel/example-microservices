package br.com.ossav.dados;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ossav.model.Cambio;

public interface CambioRepository extends JpaRepository<Cambio, Long>{
	Cambio findByFromAndTo(String from, String to);
}
