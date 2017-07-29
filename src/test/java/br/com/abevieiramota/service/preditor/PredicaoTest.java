package br.com.abevieiramota.service.preditor;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import br.com.abevieiramota.model.Resultado;
import br.com.abevieiramota.model.Resultado.Bicho;
import br.com.abevieiramota.model.Resultado.Premio;
import br.com.abevieiramota.model.Resultado.ResultadoBuilder;
import br.com.abevieiramota.model.Resultado.Turno;
import br.com.abevieiramota.model.dao.ResultadoDAO;

@RunWith(JUnit4.class)
public class PredicaoTest {
	
	private static Resultado resultado1 = new ResultadoBuilder().data("27/07/2013").premios(new String[]{
			"0728",
			"0383",
			"8157",
			"0863",
			"7532",
			"0874",
			"4668",
			"3967",
			"5237",
			"0375"
			
	}).turno(Turno.NOTURNO).build();
	
	private static Resultado resultado2 = new ResultadoBuilder().data("27/07/2013").premios(new String[]{
			"6865",
			"0383",
			"8157",
			"0863",
			"7532",
			"0874",
			"4668",
			"3967",
			"5237",
			"0375"
			
	}).turno(Turno.NOTURNO).build();
	
	@Test
	public void predicaoMaisVelhosTamanho25() {
		Resultado[] maisVelhos = new Predicao(Premio._1).ordenadoPorVelhice();
		
		assertEquals(0, maisVelhos.length);
	}

	@Test
	public void predicaoVazia() {
		Predicao predicao = new Predicao(Premio._1);
		predicao.atualizar(resultado1);
		
		Resultado[] maisVelhos = predicao.ordenadoPorVelhice();
		
		assertEquals(resultado1.premio(Premio._1), maisVelhos[0].premio(Premio._1));
	}
	
	@Test
	public void predicaoVaziaDoisResultadosIguais() {
		Predicao predicao = new Predicao(Premio._1);
		predicao.atualizar(resultado1);
		predicao.atualizar(resultado1);
		
		Resultado[] maisVelhos = predicao.ordenadoPorVelhice();
		
		assertEquals(resultado1.premio(Premio._1), maisVelhos[0].premio(Premio._1));
	}
	
	@Test
	public void predicaoVaziaDoisResultadosDiferentes() {
		Predicao predicao = new Predicao(Premio._1);
		predicao.atualizar(resultado1);
		predicao.atualizar(resultado2);
		
		Resultado[] maisVelhos = predicao.ordenadoPorVelhice();
		
		assertEquals(resultado1.premio(Premio._1), maisVelhos[0].premio(Premio._1));
		assertEquals(resultado2.premio(Premio._1), maisVelhos[1].premio(Premio._1));
	}
	
	@Test
	public void predicao10ResultadosIguais() {
		Predicao predicao = new Predicao(Premio._1);
		predicao.atualizar(resultado1);
		predicao.atualizar(resultado1);
		predicao.atualizar(resultado1);
		predicao.atualizar(resultado1);
		predicao.atualizar(resultado1);
		predicao.atualizar(resultado1);
		predicao.atualizar(resultado1);
		predicao.atualizar(resultado1);
		predicao.atualizar(resultado1);
		predicao.atualizar(resultado1);
		
		Resultado[] maisVelhos = predicao.ordenadoPorVelhice();
		
		assertEquals(resultado1.premio(Premio._1), maisVelhos[0].premio(Premio._1));
	}
	
	// apenas para teste temporário
//	@Test
	public void predicaoAPartir01012013() throws ClassNotFoundException, SQLException {
		ResultadoDAO resDAO = new ResultadoDAO();
		List<Resultado> resultados = resDAO.all();
		Predicao predicao = new Predicao(Premio._1);
		for(Resultado resultado: resultados) {
			predicao.atualizar(resultado);
		}
		Set<Bicho> bichosNaPredicao = new HashSet<Bicho>();
		for(Resultado valorResultado: predicao.ordenadoPorVelhice()) {
			bichosNaPredicao.add(Bicho.fromResultado(valorResultado.premio(Premio._1)));
			System.out.println(Bicho.fromResultado(valorResultado.premio(Premio._1)) + " : " + valorResultado.data);
		}
		assertEquals(25, bichosNaPredicao.size());
	}
}
