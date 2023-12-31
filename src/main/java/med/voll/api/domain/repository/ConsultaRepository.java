package med.voll.api.domain.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import med.voll.api.domain.consulta.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

  Boolean existsByMedicoIdAndData(Long idMedico, LocalDateTime data);

  Boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primeiroHorario, LocalDateTime ultimoHoraio);

}
