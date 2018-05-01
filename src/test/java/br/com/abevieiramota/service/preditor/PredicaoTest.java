package br.com.abevieiramota.service.preditor;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import br.com.abevieiramota.model.Loteria;
import br.com.abevieiramota.model.Resultado;
import br.com.abevieiramota.model.Resultado.ResultadoBuilder;
import br.com.abevieiramota.model.Turno;

@RunWith(JUnit4.class)
public class PredicaoTest {

	private static final Turno TURNO_NOTURNO = new Turno();
	static {
//		TURNO_NOTURNO.setId(0);
//		TURNO_NOTURNO.setLabel("Noturno");
//		TURNO_NOTURNO.setHoraMinima(10);
//		TURNO_NOTURNO.setHoraMinima(14);
	}
	private static final Loteria LOTERIA_FEDERAL = new Loteria();
	static {
//		LOTERIA_FEDERAL.setId(0);
//		LOTERIA_FEDERAL.setLabel("Federal");
	}

	private static Resultado resultado1 = new ResultadoBuilder().data("27/07/2013")
			.premios(new String[] { "0728", "0383", "8157", "0863", "7532", "0874", "4668", "3967", "5237", "0375"

			}).turno(TURNO_NOTURNO).loteria(LOTERIA_FEDERAL).build();

	private static Resultado resultado2 = new ResultadoBuilder().data("27/07/2013")
			.premios(new String[] { "6865", "0383", "8157", "0863", "7532", "0874", "4668", "3967", "5237", "0375"

			}).turno(TURNO_NOTURNO).loteria(LOTERIA_FEDERAL).build();

	// @Test
	// public void predicaoMaisVelhosTamanho25() {
	// Resultado[] maisVelhos = new PredicaoBuilder(Premio._1,
	// Dezena.TERCEIRA).build().ordenadoPorVelhice();
	//
	// assertEquals(0, maisVelhos.length);
	// }
	//
	// @Test
	// public void predicaoVazia() {
	// Predicao predicao = new PredicaoBuilder(Premio._1,
	// Dezena.TERCEIRA).atualizar(resultado1).build();
	//
	// Resultado[] maisVelhos = predicao.ordenadoPorVelhice();
	//
	// assertEquals(resultado1.premio(Premio._1), maisVelhos[0].premio(Premio._1));
	// }
	//
	// @Test
	// public void predicaoVaziaDoisResultadosIguais() {
	// Predicao predicao = new PredicaoBuilder(Premio._1,
	// Dezena.TERCEIRA).atualizar(resultado1).atualizar(resultado2)
	// .build();
	//
	// Resultado[] maisVelhos = predicao.ordenadoPorVelhice();
	//
	// assertEquals(resultado1.premio(Premio._1), maisVelhos[0].premio(Premio._1));
	// }
	//
	// @Test
	// public void predicaoVaziaDoisResultadosDiferentes() {
	// Predicao predicao = new PredicaoBuilder(Premio._1,
	// Dezena.TERCEIRA).atualizar(resultado1).atualizar(resultado2)
	// .build();
	//
	// Resultado[] maisVelhos = predicao.ordenadoPorVelhice();
	//
	// assertEquals(resultado1.premio(Premio._1), maisVelhos[0].premio(Premio._1));
	// assertEquals(resultado2.premio(Premio._1), maisVelhos[1].premio(Premio._1));
	// }
	//
	// @Test
	// public void predicao10ResultadosIguais() {
	// Predicao predicao = new PredicaoBuilder(Premio._1,
	// Dezena.TERCEIRA).atualizar(resultado1).atualizar(resultado1)
	// .atualizar(resultado1).atualizar(resultado1).atualizar(resultado1).atualizar(resultado1).build();
	//
	// Resultado[] maisVelhos = predicao.ordenadoPorVelhice();
	//
	// assertEquals(resultado1.premio(Premio._1), maisVelhos[0].premio(Premio._1));
	// }
	//
	// // apenas para teste temporário
	// // @Test
	// public void predicaoAPartir01012013() throws ClassNotFoundException,
	// SQLException {
	// ResultadoDao resDAO = new ResultadoDao();
	// List<Resultado> resultados = resDAO.all(LOTERIA_FEDERAL);
	// PredicaoBuilder predicaoBuilder = new PredicaoBuilder(Premio._1,
	// Dezena.TERCEIRA);
	// for (Resultado resultado : resultados) {
	// predicaoBuilder.atualizar(resultado);
	// }
	//
	// Predicao predicao = predicaoBuilder.build();
	//
	// Set<Bicho> bichosNaPredicao = new HashSet<Bicho>();
	// for (Resultado valorResultado : predicao.ordenadoPorVelhice()) {
	// bichosNaPredicao.add(Bicho.fromResultado(valorResultado.premio(Premio._1),
	// Dezena.TERCEIRA));
	// System.out.println(Bicho.fromResultado(valorResultado.premio(Premio._1),
	// Dezena.TERCEIRA) + " : "
	// + valorResultado.getData());
	// }
	// assertEquals(25, bichosNaPredicao.size());
	// }
}
