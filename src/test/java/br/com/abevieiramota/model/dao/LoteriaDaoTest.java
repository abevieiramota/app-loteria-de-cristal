package br.com.abevieiramota.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import br.com.abevieiramota.model.Loteria;
import br.com.abevieiramota.model.Resultado;
import br.com.abevieiramota.model.Turno;

@RunWith(JUnit4.class)
public class LoteriaDaoTest {

	private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
			.createEntityManagerFactory("loteria");

	@Test
	public void test() {

		EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();

		try {
			EntityTransaction transaction = manager.getTransaction();

			transaction.begin();

			List<Loteria> loterias = manager.createQuery("from Loteria", Loteria.class).getResultList();

			for (Loteria loteria : loterias) {
				System.out.println(loteria.getLabel());
			}
		} catch (Exception ex) {

		}
	}

	@Test
	public void test2() {

		EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();

		try {
			EntityTransaction transaction = manager.getTransaction();

			transaction.begin();

			List<Turno> loterias = manager.createQuery("from Turno", Turno.class).getResultList();

			for (Turno loteria : loterias) {
				System.out.println(loteria.getLabel());
			}
		} catch (Exception ex) {

		}
	}

	@Test
	public void test3() {

		EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();

		try {
			EntityTransaction transaction = manager.getTransaction();

			transaction.begin();

			TypedQuery<Resultado> query = manager.createQuery("from Resultado", Resultado.class);
			query.setMaxResults(100);
			
			List<Resultado> resultados = query.getResultList();

			for (Resultado resultado : resultados) {
				System.out.println(resultado);
			}
		} catch (Exception ex) {

		}
	}
}
