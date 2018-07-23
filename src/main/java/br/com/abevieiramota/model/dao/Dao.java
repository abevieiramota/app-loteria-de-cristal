package br.com.abevieiramota.model.dao;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.abevieiramota.gui.wmain.Parametros;
import br.com.abevieiramota.model.Dezena;
import br.com.abevieiramota.model.Loteria;
import br.com.abevieiramota.model.Resultado;
import br.com.abevieiramota.model.Turno;

public class Dao {

	private static class EMF {

		private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
				.createEntityManagerFactory("loteria");

		public static EntityManager buildManager() {
			return ENTITY_MANAGER_FACTORY.createEntityManager();
		}

	}

	public List<Loteria> allLoteria() {
		EntityManager manager = EMF.buildManager();

		List<Loteria> loterias = manager.createQuery("from Loteria", Loteria.class).getResultList();

		manager.close();

		return loterias;
	}

	public List<Resultado> allResultados(Set<Turno> turnos, Loteria loteria) {
		EntityManager manager = EMF.buildManager();

		TypedQuery<Resultado> query = manager
				.createQuery("from Resultado where turno in (:turnos) and loteria = :loteria"
						+ " order by substr(data, 7) || substr(data, 4, 2) || substr(data, 1, 2) asc", Resultado.class);
		query.setParameter("turnos", turnos);
		query.setParameter("loteria", loteria);

		List<Resultado> resultados = query.getResultList();

		manager.close();

		return resultados;
	}

	public List<Resultado> resultadoByData(String data, Loteria loteria) {
		EntityManager manager = EMF.buildManager();

		TypedQuery<Resultado> query = manager.createQuery("from Resultado where data = :data and loteria = :loteria",
				Resultado.class);
		query.setParameter("data", data);
		query.setParameter("loteria", loteria);

		List<Resultado> resultados = query.getResultList();

		manager.close();

		return resultados;
	}

	public void salvarResultados(List<Resultado> resultados) {
		EntityManager manager = EMF.buildManager();

		try {
			manager.getTransaction().begin();

			for (Resultado resultado : resultados) {
				manager.persist(resultado);
			}

			manager.getTransaction().commit();
		} catch (Exception ex) {
			if (manager.getTransaction().isActive()) {
				manager.getTransaction().rollback();
			}

			throw ex;
		}
	}

	public List<Turno> turnosDaLoteria(Loteria loteria) {
		EntityManager manager = EMF.buildManager();

		TypedQuery<Turno> qTurnosDaLoteria = manager.createQuery("from Turno where loteria = :loteria", Turno.class);
		qTurnosDaLoteria.setParameter("loteria", Parametros.getLoteria());

		List<Turno> turnosDaLoteria = qTurnosDaLoteria.getResultList();

		manager.close();

		return turnosDaLoteria;
	}

	public List<Dezena> dezenas() {
		EntityManager manager = EMF.buildManager();

		List<Dezena> dezenas = manager.createQuery("from Dezena", Dezena.class).getResultList();

		manager.close();

		return dezenas;
	}

	public <E> void atualizar(E entity) {

		EntityManager manager = EMF.buildManager();

		try {
			manager.getTransaction().begin();
			manager.merge(entity);
			manager.getTransaction().commit();
		} catch (Exception ex) {
			if (manager.getTransaction().isActive()) {
				manager.getTransaction().rollback();
			}
			throw ex;
		}

		manager.close();
	}

	public <E> void persistir(E entity) {
		EntityManager manager = EMF.buildManager();

		try {
			manager.getTransaction().begin();
			manager.persist(entity);
			manager.getTransaction().commit();
		} catch (Exception ex) {
			if (manager.getTransaction().isActive()) {
				manager.getTransaction().rollback();
			}
			throw ex;
		}

		manager.close();
	}

	public boolean existsResultado(String data, Turno turno, Loteria loteria) {
		EntityManager manager = EMF.buildManager();

		TypedQuery<Resultado> query = manager.createQuery(
				"from Resultado where data = :data and turno = :turno and loteria = :loteria", Resultado.class);
		query.setParameter("data", data);
		query.setParameter("turno", turno);
		query.setParameter("loteria", loteria);

		List<Resultado> resultado = query.getResultList();

		return !resultado.isEmpty();
	}

	public boolean deletarResultado(Loteria loteria, Turno turno, String data) {
		
		EntityManager manager = EMF.buildManager();
		
		Query query = manager.createQuery("delete from Resultado where data = :data and turno = :turno and loteria = :loteria");
		query.setParameter("data", data);
		query.setParameter("loteria", loteria);
		query.setParameter("turno", turno);
		
		try {
			manager.getTransaction().begin();
			int nUpdated = query.executeUpdate();
			manager.getTransaction().commit();
			
			return nUpdated == 0 ? false: true;
		} catch (Exception ex) {
			if (manager.getTransaction().isActive()) {
				manager.getTransaction().rollback();
			}
			throw ex;
		}
	}

}
