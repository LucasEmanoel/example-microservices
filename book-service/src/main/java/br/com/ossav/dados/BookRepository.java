package br.com.ossav.dados;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ossav.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

}
