package med.voll.api.infra.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.model.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.repository.PacienteRepository;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsulta {

  @Autowired
  private PacienteRepository pacienteRepository;

  public void validar(DadosAgendamentoConsulta daods) {
    var pacienteEstaAtivo = pacienteRepository.findAtivoById(daods.idPaciente());
    if (!pacienteEstaAtivo) {
      throw new ValidacaoException("Consulta não pode ser agendada com apciente excluido");
    }
  }
}
