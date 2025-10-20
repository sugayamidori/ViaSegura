package com.github.sugayamidori.viaseguraapi.validator;

import com.github.sugayamidori.viaseguraapi.exceptions.RegistroDuplicadoException;
import com.github.sugayamidori.viaseguraapi.model.Usuario;
import com.github.sugayamidori.viaseguraapi.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioValidatorTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioValidator validator;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setEmail("usuario@exemplo.com");
    }

    @Test
    void deveValidarComSucesso() {
        when(repository.findByEmail(anyString())).thenReturn(null);

        validator.validar(usuario);

        verify(repository).findByEmail(anyString());
        assertDoesNotThrow(() -> validator.validar(usuario));

    }

    @Test
    void deveLancarExcecaoUsuarioJaCadastrado() {
        when(repository.findByEmail(anyString())).thenReturn(usuario);

        assertThrows(RegistroDuplicadoException.class, () -> validator.validar(usuario));

        verify(repository).findByEmail(anyString());

    }
}