package med.voll.api.service;

import java.util.List;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.model.consulta.DadosAgendamentoConsulta;
import med.voll.api.model.consulta.DadosDetalhamentoConsulta;
import med.voll.api.infra.exception.ValidacaoException;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.infra.validacoes.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.repository.ConsultaRepository;
import med.voll.api.repository.PacienteRepository;

@Service
public class AgendaConsultaService {

  @Autowired
  private ConsultaRepository consultaRepository;
  @Autowired
  private MedicoRepository medicoRepository;
  @Autowired
  private PacienteRepository pacienteRepository;
  @Autowired
  private List<ValidadorAgendamentoDeConsulta> validadores;

  public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
    if (!pacienteRepository.existsById(dados.idPaciente())) {
      throw new ValidacaoException("Id nao encontrado");
    }
    if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
      throw new ValidacaoException("Id nao encontrado");
    }

    validadores.forEach(validador -> validador.validar(dados));

    var paciente = pacienteRepository.findById(dados.idPaciente()).get();
    var medico = escolherMedico(dados);
    var consulta = new Consulta(null, medico, paciente, dados.data());
    consultaRepository.save(consulta);
    return new DadosDetalhamentoConsulta(consulta);
  }

  private Medico escolherMedico(DadosAgendamentoConsulta dados) {
    if (dados.idMedico() != null) {
      return medicoRepository.getReferenceById(dados.idMedico());

    }

    if (dados.especialidade() == null) {
      throw new ValidacaoException("Especialidade obirgatoria, quando não escolhido um médico");

    }

    return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
  }

}
