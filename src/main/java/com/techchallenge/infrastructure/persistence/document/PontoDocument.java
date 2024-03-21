package com.techchallenge.infrastructure.persistence.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Document(collection = "ponto" )
public class PontoDocument {

    @Id
    private String id;
    private String matriculaUsuario;
    private LocalDate dataPonto;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSaidaAlmoco;

    private LocalDateTime horaVoldaAlmoco;
    private LocalDateTime horaSaida;
    private Integer mes;
    private Integer ano;
}
