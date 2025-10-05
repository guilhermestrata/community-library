package com.example.library.repository;

import com.example.library.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IEmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    List<Emprestimo> findByAtivoTrue();
}
