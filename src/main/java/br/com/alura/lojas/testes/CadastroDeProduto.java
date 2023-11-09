package br.com.alura.lojas.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.lojas.dao.CategoriaDao;
import br.com.alura.lojas.dao.ProdutoDao;
import br.com.alura.lojas.modelo.Categoria;
import br.com.alura.lojas.modelo.CategoriaId;
import br.com.alura.lojas.modelo.Produto;
import br.com.alura.lojas.util.JPAUtil;


public class CadastroDeProduto {
	
	public static void main(String[] args) {
		cadastrarProduto();
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		
		Produto p = produtoDao.buscarPorId(1l);
		System.out.println(p.getPreco());
		
		List<Produto> todos =  produtoDao.buscarPorNomeDaCategoria("CELULARES");
		todos.forEach(p2 -> System.out.println(p.getNome()));
		
		BigDecimal precoDoProduto = produtoDao.buscarPrecoDoProdutoComNome("Xiaomi Redmi");
		System.out.println("Preço do produto: " + precoDoProduto);
	}

	private static void cadastrarProduto() {
		Categoria celulares = new Categoria("CELULARES");
		
		
		Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), celulares);
		
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		CategoriaDao categoriaDao = new CategoriaDao(em);
		
		em.getTransaction().begin();
		categoriaDao.cadastrar(celulares);
		produtoDao.cadastrar(celular);
		//em.persist(celulares);
		//celulares.setNome("XPTO");
		
		//em.flush();
		//em.clear();
		
		//celulares = em.merge(celulares);
		//celulares.setNome("1234");
		//em.flush();
		//em.clear();
		//em.remove(celulares);
		//em.flush();
		
		em.getTransaction().commit();
		
		em.find(Categoria.class, new CategoriaId("CELULARES", "xpto"));
		
		em.close();
	}

}
