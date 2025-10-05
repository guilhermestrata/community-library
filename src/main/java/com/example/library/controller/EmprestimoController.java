package com.example.library.controller;

import com.example.library.model.Emprestimo;
import com.example.library.model.StatusLivro;
import com.example.library.repository.IEmprestimoRepository;
import com.example.library.repository.interfaces.ILivroRepository;
import com.example.library.repository.interfaces.IUsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/emprestimos")
public class EmprestimoController {
    private final IEmprestimoRepository emprestimoRepository;
    private final ILivroRepository livroRepository;
    private final IUsuarioRepository usuarioRepository;

    public EmprestimoController(IEmprestimoRepository emprestimoRepository, ILivroRepository livroRepository, IUsuarioRepository usuarioRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroRepository = livroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String listarAtivos(Model model) {
        model.addAttribute("emprestimos", emprestimoRepository.findByAtivoTrue());
        return "emprestimos/list";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        var emprestimo = new Emprestimo();
        model.addAttribute("emprestimo", emprestimo);
        model.addAttribute("livros", livroRepository.findByStatus(StatusLivro.DISPONIVEL));
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "emprestimos/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Emprestimo emprestimo, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("livros", livroRepository.findByStatus(StatusLivro.DISPONIVEL));
            model.addAttribute("usuarios", usuarioRepository.findAll());
            return "emprestimos/form";
        }

        var livro = livroRepository.findById(emprestimo.getLivro().getId()).orElseThrow();
        livro.setStatus(StatusLivro.EMPRESTADO);
        livroRepository.save(livro);

        emprestimoRepository.save(emprestimo);
        return "redirect:/emprestimos";
    }

    @PostMapping("/devolver/{id}")
    public String devolver(@PathVariable Long id) {
        var e = emprestimoRepository.findById(id).orElseThrow();
        e.setAtivo(false);
        emprestimoRepository.save(e);

        var livro = e.getLivro();
        livro.setStatus(StatusLivro.DISPONIVEL);
        livroRepository.save(livro);

        return "redirect:/emprestimos";
    }
}
