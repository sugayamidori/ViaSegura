package com.github.sugayamidori.viaseguraapi.service;

import com.github.sugayamidori.viaseguraapi.exceptions.RegistroDuplicadoException;
import com.github.sugayamidori.viaseguraapi.model.Usuario;
import com.github.sugayamidori.viaseguraapi.repository.UsuarioRepository;
import com.github.sugayamidori.viaseguraapi.validator.UsuarioValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private UsuarioValidator validator;

    @InjectMocks
    private UsuarioService service;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        this.usuario = new Usuario();
        usuario.setEmail("usuario@exemplo.com");
        usuario.setSenha("senhaPlain");
    }

    @Test
    void deveValidarCodificarSenhaDefinirRolesESalvar() {
        when(encoder.encode("senhaPlain")).thenReturn("senhaEncoded");

        service.salvar(usuario);
        verify(validator).validar(usuario);
        verify(encoder).encode("senhaPlain");

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(repository).save(captor.capture());
        Usuario salvo = captor.getValue();

        assertEquals("senhaEncoded", salvo.getSenha());
        assertEquals(List.of("OPERADOR"), salvo.getRoles());
        assertEquals("usuario@exemplo.com", salvo.getEmail());
    }

    @Test
    void naoDeveSalvarELancarExcecao() {
        doThrow(new RegistroDuplicadoException("Usuário já cadastrado")).when(validator).validar(usuario);

        assertThrows(RegistroDuplicadoException.class, () -> service.salvar(usuario));
        verify(encoder, never()).encode(anyString());
        verify(repository, never()).save(any());
    }

    @Test
    void deveDelegarAoRepositorio() {
        when(repository.findByEmail("usuario@exemplo.com")).thenReturn(usuario);

        Usuario resultado = service.obterPorEmail("usuario@exemplo.com");

        verify(repository).findByEmail("usuario@exemplo.com");
        assertNotNull(resultado);
        assertSame(usuario, resultado);
    }

    @Test
    void deveRetornarValorNulo() {
        when(repository.findByEmail("usuario@exemplo.com")).thenReturn(null);

        Usuario resultado = service.obterPorEmail("usuario@exemplo.com");

        verify(repository).findByEmail("usuario@exemplo.com");
        assertNull(resultado);
    }
}