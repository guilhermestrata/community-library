package com.example.library.controller;

import com.example.library.model.Livro;
import com.example.library.model.StatusLivro;
import com.example.library.repository.interfaces.ILivroRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/livros")
public class LivroController {
    private final ILivroRepository livroRepository;
    public LivroController(ILivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("livros", livroRepository.findAll());
        return "livros/list";
    }

    @GetMapping("/disponiveis")
    public String listarDisponiveis(Model model) {
        model.addAttribute("livros", livroRepository.findByStatus(StatusLivro.DISPONIVEL));
        return "livros/list";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("livro", new Livro());
        return "livros/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Livro livro, BindingResult result) {
        if (result.hasErrors()) return "livros/form";
        livro.setStatus(StatusLivro.DISPONIVEL);
        livroRepository.save(livro);
        return "redirect:/livros";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        var l = livroRepository.findById(id).orElseThrow();
        model.addAttribute("livro", l);
        return "livros/form";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        livroRepository.deleteById(id);
        return "redirect:/livros";
    }
}
