package com.demo.usuariosApi.dao;

import com.demo.usuariosApi.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    @Query("SELECT U FROM Usuario U WHERE U.email = ?1")
    Usuario consultarPorEmail(String email);

}
