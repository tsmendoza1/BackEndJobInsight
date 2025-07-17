package com.jobinsight.proyectoretojobinsight.Repository;

import com.jobinsight.proyectoretojobinsight.Modell.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
