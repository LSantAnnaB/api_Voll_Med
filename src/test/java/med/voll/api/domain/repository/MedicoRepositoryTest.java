package med.voll.api.domain.repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.medico.DadosCadastroMedico;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.DadosCadastroPaciente;
import med.voll.api.domain.paciente.Paciente;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class MedicoRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;
  @Autowired
  private MedicoRepository medicoRepository;

  @Test
  @DisplayName("Deve devolver null quando medico cadastrado nao esta disponivel na data")
  void testEscolherMedicoAleatorioLivreNaDataCenario1() {
    var proximaSegundaAsDez = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);

    var medico = cadastrarMedico("medico", "teste@email.com", "123123", Especialidade.CARDIOLOGIA);
    var paciente = cadastrarPaciente("paciente", "testePaciente@email.com", "11311211212");
    cadastrarConsulta(medico, paciente, proximaSegundaAsDez);
    var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA,
        proximaSegundaAsDez);

    assertThat(medicoLivre).isNull();
  }

  @Test
  @DisplayName("Deve devolver medico quando disponivel na data")
  void testEscolherMedicoAleatorioLivreNaDataCenario2() {
    var proximaSegundaAsDez = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);

    var medico = cadastrarMedico("medico", "teste@email.com", "123123", Especialidade.CARDIOLOGIA);

    var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA,
        proximaSegundaAsDez);

    assertThat(medicoLivre).isEqualTo(medico);
  }

  private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
    entityManager.persist(new Consulta(null, medico, paciente, data));
  }

  private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
    var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
    entityManager.persist(medico);
    return medico;
  }

  private Paciente cadastrarPaciente(String nome, String email, String cpf) {
    var paciente = new Paciente(dadosPaciente(nome, email, cpf));
    entityManager.persist(paciente);
    return paciente;
  }

  private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
    return new DadosCadastroMedico(
        nome,
        email,
        "61999999999",
        crm,
        especialidade,
        dadosEndereco());
  }

  private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
    return new DadosCadastroPaciente(
        nome,
        email,
        "61999999999",
        cpf,
        dadosEndereco());
  }

  private DadosEndereco dadosEndereco() {
    return new DadosEndereco(
        "rua xpto",
        "bairro",
        "00000000",
        "Brasilia",
        "DF",
        null,
        null);
  }
}
