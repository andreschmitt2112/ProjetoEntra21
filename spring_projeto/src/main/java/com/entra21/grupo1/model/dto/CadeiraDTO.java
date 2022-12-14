package com.entra21.grupo1.model.dto;
import com.entra21.grupo1.model.entity.CadeiraEntity;
import com.entra21.grupo1.model.entity.SalaEntity;
import lombok.Data;

@Data
public class CadeiraDTO {
    private Long id;
    private String codigo;
    private String tipoCadeira;
    private Integer fileira;
    private Integer ordemFileira;
}
