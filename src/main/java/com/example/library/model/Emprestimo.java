package com.example.library.model;

import com.example.library.model.Livro;
import com.example.library.model.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.AssertTrue;
import java.time.LocalDate;

@Entity
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Livro livro;

    @ManyToOne(optional = false)
    private Usuario usuario;

    @NotNull(message = "Data de retirada é obrigatória")
    private LocalDate dataRetirada;

    @NotNull(message = "Data prevista de devolução é obrigatória")
    private LocalDate dataPrevistaDevolucao;

    private boolean ativo = true;

    public Emprestimo() {}

    @AssertTrue(message = "Data prevista de devolução deve ser posterior à data de retirada")
    public boolean isDataValida() {
        if (dataRetirada == null || dataPrevistaDevolucao == null) return true;
        return dataPrevistaDevolucao.isAfter(dataRetirada);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id;}
    public Livro getLivro() { return livro; }
    public void setLivro(Livro livro) { this.livro = livro; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public LocalDate getDataRetirada() { return dataRetirada; }
    public void setDataRetirada(LocalDate dataRetirada) { this.dataRetirada = dataRetirada; }
    public LocalDate getDataPrevistaDevolucao() { return dataPrevistaDevolucao; }
    public void setDataPrevistaDevolucao(LocalDate dataPrevistaDevolucao) { this.dataPrevistaDevolucao = dataPrevistaDevolucao; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
