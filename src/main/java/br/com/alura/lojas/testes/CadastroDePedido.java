package br.com.alura.lojas.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.lojas.dao.CategoriaDao;
import br.com.alura.lojas.dao.ClienteDao;
import br.com.alura.lojas.dao.PedidoDao;
import br.com.alura.lojas.dao.ProdutoDao;
import br.com.alura.lojas.modelo.Categoria;
import br.com.alura.lojas.modelo.Cliente;
import br.com.alura.lojas.modelo.ItemPedido;
import br.com.alura.lojas.modelo.Pedido;
import br.com.alura.lojas.modelo.Produto;
import br.com.alura.lojas.util.JPAUtil;
import br.com.alura.lojas.vo.RelatorioDeVendasVo;

public class CadastroDePedido {

	public static void main(String[] args) {
		popularBancoDeDados();

		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		ClienteDao clienteDao = new ClienteDao(em);
		
		Produto produto = produtoDao.buscarPorId(1l);
		Produto produto2 = produtoDao.buscarPorId(2l);
		Produto produto3 = produtoDao.buscarPorId(3l);
		Cliente cliente = clienteDao.buscarPorId(1l);
		
		em.getTransaction().begin();
		
		
		Pedido pedido = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(10, pedido, produto));
		pedido.adicionarItem(new ItemPedido(40, pedido, produto2));
		
		Pedido pedido2 = new Pedido(cliente);
		pedido2.adicionarItem(new ItemPedido(2, pedido, produto3));
		
		PedidoDao pedidoDao = new PedidoDao(em);
		pedidoDao.cadastrar(pedido);
		pedidoDao.cadastrar(pedido2);
		
		
		em.getTransaction().commit();
		
		BigDecimal totalVendido = pedidoDao.valorTotalVendido();
		System.out.println("VALOR TOTAL: " +totalVendido);
		
		List<RelatorioDeVendasVo> relatorio = pedidoDao.relatorioDeVendas();
		relatorio.forEach(System.out::println);
		
		//List<Object[]> relatorio = pedidoDao.relatorioDeVendas();
		//for (Object[] obj : relatorio) {
		//	System.out.println(obj[0]);
		//	System.out.println(obj[1]);
		//	System.out.println(obj[2]);
	//}
	}

	private static void popularBancoDeDados() {
		Categoria celulares = new Categoria("CELULARES");
		Categoria videogames = new Categoria("VIDEOGAMES");
		Categoria informatica = new Categoria("INFORMATICA");

		Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), celulares);
		Produto videogame = new Produto("PS5", "Playstation 5", new BigDecimal("8000"), videogames);
		Produto macbook = new Produto("Macbook", "Macbook pro retina", new BigDecimal("14000"), informatica);

		Cliente cliente = new Cliente("Rodrigo", "123456");
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		CategoriaDao categoriaDao = new CategoriaDao(em);
		ClienteDao clienteDao = new ClienteDao(em);

		em.getTransaction().begin();
		categoriaDao.cadastrar(celulares);
		categoriaDao.cadastrar(videogames);
		categoriaDao.cadastrar(informatica);
		
		produtoDao.cadastrar(celular);
		produtoDao.cadastrar(videogame);
		produtoDao.cadastrar(macbook);
		
		clienteDao.cadastrar(cliente);
		
		
		// em.persist(celulares);
		// celulares.setNome("XPTO");

		// em.flush();
		// em.clear();

		// celulares = em.merge(celulares);
		// celulares.setNome("1234");
		// em.flush();
		// em.clear();
		// em.remove(celulares);
		// em.flush();

		em.getTransaction().commit();
		em.close();
	}

}
