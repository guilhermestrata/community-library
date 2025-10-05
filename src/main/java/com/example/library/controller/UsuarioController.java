package com.example.library.controller;

import com.example.library.model.Usuario;
import com.example.library.repository.interfaces.IUsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    private final IUsuarioRepository usuarioRepository;
    public UsuarioController(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "usuarios/list";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Usuario usuario, BindingResult result) {
        if (result.hasErrors()) return "usuarios/form";
        usuarioRepository.save(usuario);
        return "redirect:/usuarios";
    }
}
