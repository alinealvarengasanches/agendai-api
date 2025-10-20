package service;


import model.Agendamento;
import model.HorarioLivre;
import model.Profissional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import resources.data.MockDataLoader;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendamentoService {

    @Autowired
    private MockDataLoader dataLoader;

    private final Long ID_PROFISSIONAL = 1L; // ID fixo do profissional (ajuste se necessário)


    public Profissional getProfissionalPerfil() {
        return MockDataLoader.profissionais.stream()
                .filter(p -> p.getId().equals(ID_PROFISSIONAL))
                .findFirst()
                .orElse(null);
    }

    public List<HorarioLivre> getHorariosDisponiveis() {
        // Retorna apenas os horários que pertencem ao profissional e *não* estão agendados
        return MockDataLoader.horariosLivres.stream()
                .filter(h -> h.getProfissionalId().equals(ID_PROFISSIONAL))
                .filter(h -> !h.isAgendado())
                .collect(Collectors.toList());
    }

    // ---------------------- Agendamento de Cliente ----------------------

    public Agendamento criarAgendamento(Agendamento agendamento) {
        // 1. Validar se o horário está realmente livre
        HorarioLivre slot = MockDataLoader.horariosLivres.stream()
                .filter(h -> h.getProfissionalId().equals(ID_PROFISSIONAL))
                .filter(h -> h.getInicio().equals(agendamento.getDataHoraInicio()))
                .filter(h -> !h.isAgendado())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Horário não disponível ou já agendado."));

        // 2. Marcar o slot como agendado (simulação de atualização no DB)
        dataLoader.updateHorario(slot.getId(), true);

        // 3. Persistir o novo agendamento
        agendamento.setProfissionalId(ID_PROFISSIONAL);
        return dataLoader.saveAgendamento(agendamento);
    }

    // ---------------------- Autenticação (Simples) ----------------------

    public Profissional autenticar(String email, String senha) {
        return MockDataLoader.profissionais.stream()
                .filter(p -> p.getEmail().equalsIgnoreCase(email) && p.getSenha().equals(senha))
                .findFirst()
                .orElse(null);
    }

    public List<Agendamento> getTodosAgendamentos() {
        // Em um projeto simples, o profissional verá todos os agendamentos.
        return MockDataLoader.agendamentos;
    }
}