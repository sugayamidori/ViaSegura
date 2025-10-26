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
import java.util.Optional;
import java.util.UUID;

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

    @Test
    void deveSalvarByAdminComSenhaPadrao() {
        usuario.setRoles(List.of("ADMIN")); // roles inicial para garantir que será sobrescrito
        // prefixo esperado: "usuario" -> senha gerada: "usuario123"
        when(encoder.encode("usuario123")).thenReturn("senhaEncodedByAdmin");

        service.salvarByAdmin(usuario);

        verify(validator).validar(usuario);
        verify(encoder).encode("usuario123");

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(repository).save(captor.capture());
        Usuario salvo = captor.getValue();

        assertEquals("senhaEncodedByAdmin", salvo.getSenha());
        assertEquals("usuario@exemplo.com", salvo.getEmail());
        assertNotNull(usuario.getRoles());
    }

    @Test
    void deveAtualizarUsuarioCodificandoSenhaEChamandoRepositorio() {
        when(encoder.encode("senhaPlain")).thenReturn("senhaEncodedAtualizar");

        service.atualizar(usuario);

        verify(validator).validar(usuario);
        verify(encoder).encode("senhaPlain");

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(repository).save(captor.capture());
        Usuario salvo = captor.getValue();

        assertEquals("senhaEncodedAtualizar", salvo.getSenha());
    }

    @Test
    void naoDeveAtualizarELancarExcecao() {
        doThrow(new RegistroDuplicadoException("Usuário já cadastrado")).when(validator).validar(usuario);

        assertThrows(RegistroDuplicadoException.class, () -> service.atualizar(usuario));
        verify(encoder, never()).encode(anyString());
        verify(repository, never()).save(any());
    }

    @Test
    void deveDeletarUsuarioDelegandoAoRepositorio() {
        service.deletar(usuario);
        verify(repository).delete(usuario);
    }

    @Test
    void deveObterPorIdDelegandoAoRepositorio() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = service.obterPorId(id);

        verify(repository).findById(id);
        assertTrue(resultado.isPresent());
        assertSame(usuario, resultado.get());
    }

}