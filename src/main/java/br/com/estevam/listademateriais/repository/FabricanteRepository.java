package br.com.estevam.listademateriais.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.estevam.listademateriais.model.Fabricante;

@Repository
public interface FabricanteRepository extends MongoRepository<Fabricante, String>{

}
