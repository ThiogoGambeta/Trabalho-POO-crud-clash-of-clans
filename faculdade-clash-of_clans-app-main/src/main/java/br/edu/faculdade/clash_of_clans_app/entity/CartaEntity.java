package br.edu.faculdade.clash_of_clans_app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "Carta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "Dano é obrigatório")
    @PositiveOrZero(message = "Dano deve ser maior ou igual a zero")
    @Column(nullable = false)
    private Integer dano;

    @NotNull(message = "Velocidade de ataque é obrigatória")
    @Positive(message = "Velocidade de ataque deve ser maior que zero")
    @Column(nullable = false)
    private BigDecimal velocidadeAtaque;

    @NotNull(message = "Velocidade na arena é obrigatória")
    @PositiveOrZero(message = "Velocidade na arena deve ser maior ou igual a zero")
    @Column(nullable = false)
    private Integer velocidadeArena;

    @NotNull(message = "Indicador de dano em área é obrigatório")
    @Column(nullable = false)
    private Boolean indicadorDadoArea;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String imagemUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartaEntity that = (CartaEntity) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    public void setNome(String nome) {

    }

    public void setDano(BigDecimal dano) {

    }

    public void setVelocidadeArena(Integer velocidadeArena) {

    }

    public void setIndicadorDadoArea(Boolean indicadorDadoArea) {

    }

    public void setImagemUrl(String imagemUrl) {

    }

    public void setCodigo(Long codigo) {

    }

    public String setNome() {

        return "";
    }

    public String getNome() {
        return "";
    }

    public BigDecimal getDano() {
        return null;
    }

    public Integer getVelocidadeAtaque() {
        return null;
    }

    public Integer getVelocidadeArena() {
        return null;
    }
    public Boolean getIndicadorDadoArea() {
        return null;
    }
}
