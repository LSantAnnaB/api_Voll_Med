package med.voll.api.infra.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.model.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.repository.MedicoRepository;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsulta {
  @Autowired
  private MedicoRepository medicoRepository;

  public void validar(DadosAgendamentoConsulta dados) {

    if (dados.idMedico() == null) {
      return;
    }

    var medicoEstaAtivo = medicoRepository.findAtivoById(dados.idMedico());
    if (!medicoEstaAtivo) {
      throw new ValidacaoException("Consulta não pode ser agendada com esse medico");
    }
  }
}
