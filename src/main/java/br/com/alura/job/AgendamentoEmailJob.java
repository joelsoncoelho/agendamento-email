package br.com.alura.job;

import java.util.List;

import br.com.alura.entidade.AgendamentoEmail;
import br.com.alura.servico.AgendamentoEmailServico;
import jakarta.annotation.Resource;
import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.inject.Inject;
import jakarta.jms.JMSConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;

//@Stateless
@Singleton
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AgendamentoEmailJob {
	
	//Padrão Singleton
	
	/*
	private static AgendamentoEmailJob instance;
		
	private AgendamentoEmailJob() {}
	
	public synchronized static AgendamentoEmailJob getInstance() {
		if(instance == null) {
			instance = new AgendamentoEmailJob();
		}
		return instance;
	}
	
	*/
	
	@Inject
	private AgendamentoEmailServico agendamentoEmailServico;
	
	@Inject
	@JMSConnectionFactory("java:jboss/DefaultJMSConnectionFactory")
	private JMSContext context;
	
	@Resource(mappedName = "java:/jms/queue/EmailQueue")
	private Queue queue;

	@Schedule(hour = "*", minute = "*", second = "*/10")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void enviarEmail() {
		List<AgendamentoEmail> listarPorNaoAgendado = agendamentoEmailServico.listarPorNaoAgendado();
		
		listarPorNaoAgendado.forEach(emailNaoAgendado -> {
			//agendamentoEmailServico.enviar(emailNaoAgendado);
			context.createProducer().send(queue, emailNaoAgendado);
			agendamentoEmailServico.alterar(emailNaoAgendado);
		});
	}
}
