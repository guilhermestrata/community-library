package com.example.library.repository.interfaces;

import com.example.library.model.Livro;
import com.example.library.model.StatusLivro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ILivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByStatus(StatusLivro status);
}
