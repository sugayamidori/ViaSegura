package com.github.sugayamidori.viaseguraapi.validator;

import com.github.sugayamidori.viaseguraapi.exceptions.RegistroDuplicadoException;
import com.github.sugayamidori.viaseguraapi.model.Usuario;
import com.github.sugayamidori.viaseguraapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioValidator {

    private final UsuarioRepository repository;

    public void validar(Usuario usuario) {
        if(existeUsuarioComEmail(usuario)) {
            throw new RegistroDuplicadoException("Usuário já cadastrado");
        }
    }

    private boolean existeUsuarioComEmail(Usuario usuario) {
        Optional<Usuario> usuarioEncontrado = Optional.ofNullable(repository.findByEmail(usuario.getEmail()));

        if(usuario.getId() == null) {
            return usuarioEncontrado.isPresent();
        }

        return usuarioEncontrado
                .map(Usuario::getId)
                .stream()
                .anyMatch( id -> !id.equals(usuario.getId()));
    }
}
