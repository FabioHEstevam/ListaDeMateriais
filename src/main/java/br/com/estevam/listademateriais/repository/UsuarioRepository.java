package br.com.estevam.listademateriais.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.estevam.listademateriais.model.Usuario;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String>{

	@Query("{ 'email': ?0}")
	Usuario findByEmail(String text);
	
}
