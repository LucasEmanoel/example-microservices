package br.com.ossav.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ossav.dados.CambioRepository;
import br.com.ossav.model.Cambio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cambio Service API")
@RestController
@RequestMapping("cambio-service")
public class CambioController {
	
	private static final Logger logger = LoggerFactory.getLogger(CambioController.class);

	@Autowired
	private Environment environment;
	
	@Autowired
	private CambioRepository repository;
	
	@Operation(summary = "get cambio from currency ")
	@GetMapping(value = "/{amount}/{from}/{to}")
	public Cambio getCambio(
			@PathVariable("amount")BigDecimal amount,
			@PathVariable("from")String from,
			@PathVariable("to")String to) {
		
		logger.info("Cambio Called -> amount: {} from {} to {}", amount, from, to);
		var cambio = repository.findByFromAndTo(from, to);
		if (cambio == null) throw new RuntimeException("Currency Unsupported");
		
		BigDecimal conversionFactor = cambio.getconversionFactor();
		BigDecimal convertedValue = conversionFactor.multiply(amount);
		
		cambio.setConvertedValue(convertedValue.setScale(2, RoundingMode.CEILING));
		var port = environment.getProperty("local.server.port");
		cambio.setEnvironment(port);
		return cambio;
	}
}
