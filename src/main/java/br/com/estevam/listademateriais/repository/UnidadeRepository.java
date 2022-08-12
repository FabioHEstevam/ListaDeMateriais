package br.com.estevam.listademateriais.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.estevam.listademateriais.model.Unidade;

public interface UnidadeRepository extends MongoRepository<Unidade, String>{

}
