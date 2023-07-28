package br.com.alura.dao;

import java.util.List;

import br.com.alura.entidade.AgendamentoEmail;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class AgendamentoEmailDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	/*
	public AgendamentoEmailDAO() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AgendamentoEmailDS");
		this.entityManager = entityManagerFactory.createEntityManager();
	}
	*/
	
	public List<AgendamentoEmail> listar(){
		return entityManager.createQuery("SELECT ae FROM AgendamentoEmail ae", AgendamentoEmail.class).getResultList();
	}
	
	public void inserir(AgendamentoEmail agendamentoEmail) {
		entityManager.persist(agendamentoEmail);
	}

}
