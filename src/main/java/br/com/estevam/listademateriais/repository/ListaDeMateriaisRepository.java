package br.com.estevam.listademateriais.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.estevam.listademateriais.model.ListaDeMateriais;

@Repository
public interface ListaDeMateriaisRepository extends MongoRepository<ListaDeMateriais, String>{

}
