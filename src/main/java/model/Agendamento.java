package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agendamento {

    private Long id; // ID único do agendamento
    private Long profissionalId;
    private String nomeCliente;
    private String cpfCliente;
    private String emailCliente;
    private String numeroCliente;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    // O status (confirmado, cancelado) pode ser adicionado
}