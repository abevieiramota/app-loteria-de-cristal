package br.com.abevieiramota.model.dao;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.abevieiramota.model.Loteria;
import br.com.abevieiramota.model.Resultado;
import br.com.abevieiramota.model.Turno;

public class ResultadoDao {

	public List<Resultado> all(Set<Turno> turnos, Loteria loteria) {

		EntityManager manager = EMF.buildManager();

		TypedQuery<Resultado> query = manager
				.createQuery("from Resultado where turno in (:turnos) and loteria = :loteria", Resultado.class);
		query.setParameter("turnos", turnos);
		query.setParameter("loteria", loteria);

		List<Resultado> resultados = query.getResultList();

		manager.close();

		return resultados;
	}

	public List<Resultado> byData(String data, Loteria loteria) {
		EntityManager manager = EMF.buildManager();

		TypedQuery<Resultado> query = manager.createQuery("from Resultado where data = :data and loteria = :loteria",
				Resultado.class);
		query.setParameter("data", data);
		query.setParameter("loteria", loteria);

		List<Resultado> resultados = query.getResultList();

		manager.close();

		return resultados;
	}

	public void salvar(Resultado resultado) {
		EntityManager manager = EMF.buildManager();

		try {
			manager.getTransaction().begin();
			manager.persist(resultado);
			manager.getTransaction().commit();
		} catch (Exception ex) {
			if (manager.getTransaction().isActive()) {
				manager.getTransaction().rollback();
			}

			throw ex;
		}
	}

}
