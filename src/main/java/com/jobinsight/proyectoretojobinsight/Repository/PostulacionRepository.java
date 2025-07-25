package com.jobinsight.proyectoretojobinsight.Repository;

import com.jobinsight.proyectoretojobinsight.Modell.OfertaLaboral;
import com.jobinsight.proyectoretojobinsight.Modell.Perfil;
import com.jobinsight.proyectoretojobinsight.Modell.Postulacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostulacionRepository extends JpaRepository<Postulacion,Long> {
    List<Postulacion> findByPerfil(Perfil perfil);

    boolean existsByPerfilAndOferta(Perfil perfil, OfertaLaboral oferta);
}
