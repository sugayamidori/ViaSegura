package com.github.sugayamidori.viaseguraapi.repository;

import com.github.sugayamidori.viaseguraapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Usuario findByEmail(String email);
}
