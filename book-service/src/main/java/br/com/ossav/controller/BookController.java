package br.com.ossav.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ossav.dados.BookRepository;
import br.com.ossav.model.Book;
import br.com.ossav.proxy.CambioProxy;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Book Endpoint")
@RestController
@RequestMapping("book-service")
public class BookController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private BookRepository repository;
	
	@Autowired
	private CambioProxy proxy;
	
	@Operation(summary = "Find Especific Book By Id")
	@GetMapping(value = "{id}/{currency}")
	public Book findBook(
			@PathVariable Long id,
			@PathVariable String currency) {
		
		@SuppressWarnings("deprecation")
		var book = repository.getById(id);
		if(book == null) throw new RuntimeException("Book not Found");
		
		
		var cambio = proxy.getCambio(book.getPrice(), "USD", currency);
		var port = environment.getProperty("local.server.port");
		book.setEnvironment("Book:" + port + " - Cambio:" + cambio.getEnvironment());
		book.setPrice(cambio.getConvertedValue());
		return book;
	}
}

