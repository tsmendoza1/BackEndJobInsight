package com.jobinsight.proyectoretojobinsight.Repository;

import com.jobinsight.proyectoretojobinsight.Modell.Perfil;
import com.jobinsight.proyectoretojobinsight.Modell.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerfilRepository extends JpaRepository<Perfil, Integer> {
    Optional<Perfil> findByUsuario(Usuario usuario);
}
