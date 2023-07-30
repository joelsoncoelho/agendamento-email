package br.com.alura.mdb;

import br.com.alura.entidade.AgendamentoEmail;
import br.com.alura.servico.AgendamentoEmailServico;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationLookup",
				propertyValue = "java:/jms/queue/EmailQueue"),
		@ActivationConfigProperty(propertyName = "destinationType", 
				propertyValue = "jakarta.jms.Queue")
})
public class AgendamentoEmailMDB implements MessageListener {
	
	@Inject
	private AgendamentoEmailServico agendamentoEmailServico;

	@Override
	public void onMessage(Message message) {
		try {
			AgendamentoEmail agendamentoEmail = message.getBody(AgendamentoEmail.class);
			agendamentoEmailServico.enviar(agendamentoEmail);
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}
	

}
