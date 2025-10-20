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
public class HorarioLivre {

    private Long id; // ID único do slot de horário
    private Long profissionalId;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private boolean agendado; // Para saber se o slot já foi ocupado
}