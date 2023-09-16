package med.voll.api.infra.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.model.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.repository.ConsultaRepository;

@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsulta {

  @Autowired
  private ConsultaRepository consultaRepository;

  public void validar(DadosAgendamentoConsulta dados) {
    var medicoPossuiOutraConsultaNoMesmoHorario = consultaRepository.existsByMedicoIdAndData(dados.idMedico(),
        dados.data());

    if (medicoPossuiOutraConsultaNoMesmoHorario) {
      throw new ValidacaoException("Médico já possui paciente nesse horario");
    }
  }
}
