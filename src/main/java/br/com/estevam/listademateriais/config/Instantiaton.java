package br.com.estevam.listademateriais.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.com.estevam.listademateriais.dto.AutorDTO;
import br.com.estevam.listademateriais.dto.CategoriaResumoDTO;
import br.com.estevam.listademateriais.dto.FabricanteDTO;
import br.com.estevam.listademateriais.dto.ListaDeMateriaisDTO;
import br.com.estevam.listademateriais.dto.MaterialDTO;
import br.com.estevam.listademateriais.model.Categoria;
import br.com.estevam.listademateriais.model.Fabricante;
import br.com.estevam.listademateriais.model.ItemDaListaDeMateriais;
import br.com.estevam.listademateriais.model.ListaDeMateriais;
import br.com.estevam.listademateriais.model.Material;
import br.com.estevam.listademateriais.model.Referencia;
import br.com.estevam.listademateriais.model.Unidade;
import br.com.estevam.listademateriais.model.Usuario;
import br.com.estevam.listademateriais.repository.CategoriaRepository;
import br.com.estevam.listademateriais.repository.FabricanteRepository;
import br.com.estevam.listademateriais.repository.ListaDeMateriaisRepository;
import br.com.estevam.listademateriais.repository.MaterialRepository;
import br.com.estevam.listademateriais.repository.UnidadeRepository;
import br.com.estevam.listademateriais.repository.UsuarioRepository;

@Configuration
public class Instantiaton implements CommandLineRunner{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UnidadeRepository unidadeRepository;
	
	@Autowired
	private MaterialRepository materialRepository;
	
	@Autowired
	private FabricanteRepository fabricanteRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ListaDeMateriaisRepository listaDeMateriaisRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		usuarioRepository.deleteAll();
		unidadeRepository.deleteAll();
		materialRepository.deleteAll();
		fabricanteRepository.deleteAll();
		categoriaRepository.deleteAll();
		listaDeMateriaisRepository.deleteAll();
		
		Usuario us1 = new Usuario(null, "Fábio", "fabio@gmail.com");
		
		usuarioRepository.save(us1);
		
		Unidade un1 = new Unidade(null, "Unitário", "unit");
		Unidade un2 = new Unidade(null, "Centena", "cento");
		
		unidadeRepository.saveAll(Arrays.asList(un1,un2));
		
		Fabricante fab1 = new Fabricante(null,"Hellermann Tycon");
		Fabricante fab2 = new Fabricante(null,"Permak");
		
		fabricanteRepository.saveAll(Arrays.asList(fab1,fab2));
		
		Categoria cat1 = new Categoria(null, "Elétrica");
		
		categoriaRepository.save(cat1);
		
		Categoria cat2 = new Categoria(null, "Conectores", new CategoriaResumoDTO(cat1));
		
		categoriaRepository.save(cat2);
		
		cat1.getFilhos().add(new CategoriaResumoDTO(cat2));
		
		categoriaRepository.save(cat1);
		
		Material mat1 = new Material(null,"Terminal de compressão 35 mm");
		mat1.getCategorias().addAll(Arrays.asList(new CategoriaResumoDTO(cat1),new CategoriaResumoDTO(cat2)));
		
		
		
		Material mat2 = new Material(null,"Terminal pré isolado garfo vermelho");
		mat2.getCategorias().addAll(Arrays.asList(new CategoriaResumoDTO(cat1),new CategoriaResumoDTO(cat2)));
		
		materialRepository.saveAll(Arrays.asList(mat1,mat2));
		
		cat1.getMateriais().addAll(Arrays.asList(mat1,mat2));
		cat2.getMateriais().addAll(Arrays.asList(mat1,mat2));
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		
		Referencia ref1 = new Referencia(new MaterialDTO(mat1),new FabricanteDTO(fab1),"A1");
		mat1.getReferencias().add(ref1);
		Referencia ref2 = new Referencia(new MaterialDTO(mat2),new FabricanteDTO(fab1),"A2");
		mat2.getReferencias().add(ref2);
		Referencia ref3 = new Referencia(new MaterialDTO(mat2),new FabricanteDTO(fab2),"A3");
		mat2.getReferencias().add(ref3);
		
		materialRepository.saveAll(Arrays.asList(mat1,mat2));
		
		fab1.getReferencias().addAll(Arrays.asList(ref1,ref2));
		fab2.getReferencias().addAll(Arrays.asList(ref3));
		
		fabricanteRepository.saveAll(Arrays.asList(fab1,fab2));
		
		ListaDeMateriais lsm1 = new ListaDeMateriais(null, "Painel elétrico", sdf.parse("11/08/2022"),new AutorDTO(us1));
		listaDeMateriaisRepository.save(lsm1);
		
		lsm1.getLista().add(new ItemDaListaDeMateriais(ref1,10,un1));
		lsm1.getLista().add(new ItemDaListaDeMateriais(ref3,1,un2));
		
		listaDeMateriaisRepository.save(lsm1);
		
		us1.getProjetos().add(new ListaDeMateriaisDTO(lsm1));
		
		usuarioRepository.save(us1);
		
	}

}
