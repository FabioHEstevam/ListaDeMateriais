package br.com.estevam.listademateriais.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.estevam.listademateriais.model.Categoria;

@Repository
public interface CategoriaRepository extends MongoRepository<Categoria, String>{

}
