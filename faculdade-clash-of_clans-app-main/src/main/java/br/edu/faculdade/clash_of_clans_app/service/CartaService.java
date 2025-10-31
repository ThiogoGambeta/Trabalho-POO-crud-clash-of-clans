package br.edu.faculdade.clash_of_clans_app.service;

import br.edu.faculdade.clash_of_clans_app.entity.CartaEntity;
import br.edu.faculdade.clash_of_clans_app.repository.CartaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartaService {

    private final CartaRepository cartaRepository;

    public CartaService(CartaRepository cartaRepository) {
        this.cartaRepository = cartaRepository;
    }

    public List<CartaEntity> selecionarTodos() {
        return cartaRepository.findAll();
    }

    public CartaEntity selecionarPorCodigo(Long codigo) {
        return cartaRepository.findById(codigo)
                .orElseThrow(() -> new RuntimeException("Carta com código " + codigo + " não encontrada"));
    }

    @Transactional
    public void inserir(CartaEntity carta) {
        if (carta.setNome() != null && cartaRepository.existsByNomeIgnoreCase(carta.setNome())) {
            throw new RuntimeException("Já existe uma carta com o nome: " + carta.setNome());
        }
        
        carta.setCodigo(null);
        cartaRepository.save(carta);
    }

    @Transactional
    public CartaEntity atualizar(Long codigo, CartaEntity cartaAtualizada) {
        CartaEntity cartaExistente = selecionarPorCodigo(codigo);
        
        if (cartaAtualizada.getNome() != null && 
            !cartaExistente.getNome().equalsIgnoreCase(cartaAtualizada.getNome()) &&
            cartaRepository.existsByNomeIgnoreCase(cartaAtualizada.getNome())) {
            throw new RuntimeException("Já existe uma carta com o nome: " + cartaAtualizada.getNome());
        }
        
        cartaExistente.setNome(cartaAtualizada.getNome());
        cartaExistente.setDano(cartaAtualizada.getDano());
        cartaExistente.setVelocidadeArena(cartaAtualizada.getVelocidadeAtaque());
        cartaExistente.setVelocidadeArena(cartaAtualizada.getVelocidadeArena());
        cartaExistente.setIndicadorDadoArea(cartaAtualizada.getIndicadorDadoArea());
        
        return cartaRepository.save(cartaExistente);
    }

    @Transactional
    public void deletarPorCodigo(Long codigo) {
        if (!cartaRepository.existsById(codigo)) {
            throw new RuntimeException("Carta com código " + codigo + " não encontrada");
        }
        cartaRepository.deleteById(codigo);
    }

    @Transactional
    public void deletarEmLote(List<Long> codigos) {
        if (codigos == null || codigos.isEmpty()) {
            return;
        }
        
        for (Long codigo : codigos) {
            if (cartaRepository.existsById(codigo)) {
                cartaRepository.deleteById(codigo);
            }
        }
    }
}
