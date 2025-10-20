package resources.data;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import model.Agendamento;
import model.HorarioLivre;
import model.Profissional;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MockDataLoader {

    // Armazenamento em memória (simula o DB)
    public static List<Profissional> profissionais = new ArrayList<>();
    public static List<HorarioLivre> horariosLivres = new ArrayList<>();
    public static List<Agendamento> agendamentos = new ArrayList<>();

    @PostConstruct
    public void loadData() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        // Registra o módulo para ler corretamente LocalDateTime
        mapper.registerModule(new JavaTimeModule());

        // 1. Carregar Profissionais
        profissionais = mapper.readValue(
                new ClassPathResource("data/profissionais.json").getFile(),
                new TypeReference<List<Profissional>>() {}
        );

        // 2. Carregar Horários Livres
        horariosLivres = mapper.readValue(
                new ClassPathResource("data/horarios-livres.json").getFile(),
                new TypeReference<List<HorarioLivre>>() {}
        );

        // 3. Carregar Agendamentos
        agendamentos = mapper.readValue(
                new ClassPathResource("data/agendamentos.json").getFile(),
                new TypeReference<List<Agendamento>>() {}
        );

        System.out.println("Dados mockados carregados: " + profissionais.size() + " profissionais, " + horariosLivres.size() + " horários.");
    }

    // Métodos para persistência
    public Agendamento saveAgendamento(Agendamento novoAgendamento) {
        // Simular a criação de um ID
        long nextId = agendamentos.stream().mapToLong(Agendamento::getId).max().orElse(0L) + 1;
        novoAgendamento.setId(nextId);
        agendamentos.add(novoAgendamento);
        return novoAgendamento;
    }

    // Método de exemplo para atualizar o status do horário
    public HorarioLivre updateHorario(Long horarioId, boolean agendado) {
        return horariosLivres.stream()
                .filter(h -> h.getId().equals(horarioId))
                .findFirst()
                .map(horario -> {
                    horario.setAgendado(agendado);
                    return horario;
                })
                .orElse(null);
    }
}