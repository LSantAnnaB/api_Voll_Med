package med.voll.api.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.ValidacaoException;
import med.voll.api.domain.repository.MedicoRepository;

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
