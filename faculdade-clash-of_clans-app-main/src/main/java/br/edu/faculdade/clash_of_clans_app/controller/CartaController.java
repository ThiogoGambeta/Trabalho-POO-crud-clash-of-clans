package br.edu.faculdade.clash_of_clans_app.controller;

import br.edu.faculdade.clash_of_clans_app.entity.CartaEntity;
import br.edu.faculdade.clash_of_clans_app.service.CartaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/cartas")
@RequiredArgsConstructor
public class CartaController {

    private final CartaService cartaService;

    public CartaController(CartaService cartaService) {
        this.cartaService = cartaService;
    }

    @GetMapping
    public String listarCartas(Model model) {
        List<CartaEntity> cartas = cartaService.selecionarTodos();
        model.addAttribute("cartas", cartas);
        return "todas_cartas";
    }

    @GetMapping("/nova")
    public String novaCarta(Model model) {
        model.addAttribute("carta", new CartaEntity());
        return "nova_carta";
    }

    @PostMapping
    public String criarCarta(@Valid @ModelAttribute("carta") CartaEntity carta,
                            BindingResult result,
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "nova_carta";
        }

        try {
            cartaService.inserir(carta);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Carta criada com sucesso!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
        }

        return "redirect:/cartas";
    }

    @GetMapping("/{codigo}")
    public String visualizarCarta(@PathVariable Long codigo, Model model, RedirectAttributes redirectAttributes) {
        try {
            CartaEntity carta = cartaService.selecionarPorCodigo(codigo);
            model.addAttribute("carta", carta);
            return "visualizar_carta";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
            return "redirect:/cartas";
        }
    }

    @GetMapping("/{codigo}/editar")
    public String editarCarta(@PathVariable Long codigo, Model model, RedirectAttributes redirectAttributes) {
        try {
            CartaEntity carta = cartaService.selecionarPorCodigo(codigo);
            model.addAttribute("carta", carta);
            return "editar_carta";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
            return "redirect:/cartas";
        }
    }

    @PostMapping("/{codigo}")
    public String atualizarCarta(@PathVariable Long codigo,
                                @Valid @ModelAttribute("carta") CartaEntity carta,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            carta.setCodigo(codigo);
            model.addAttribute("carta", carta);
            return "editar_carta";
        }

        try {
            cartaService.atualizar(codigo, carta);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Carta atualizada com sucesso!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
        }

        return "redirect:/cartas";
    }

    @PostMapping("/excluir")
    public String excluirCartas(@RequestParam(required = false) List<Long> codigos,
                               RedirectAttributes redirectAttributes) {
        if (codigos == null || codigos.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensagemAviso", "Nenhuma carta foi selecionada para exclusão.");
            return "redirect:/cartas";
        }

        try {
            cartaService.deletarEmLote(codigos);
            redirectAttributes.addFlashAttribute("mensagemSucesso", 
                "Carta(s) excluída(s) com sucesso! Total: " + codigos.size());
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
        }

        return "redirect:/cartas";
    }
}
