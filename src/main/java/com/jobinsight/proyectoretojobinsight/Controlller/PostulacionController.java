package com.jobinsight.proyectoretojobinsight.Controlller;

import com.jobinsight.proyectoretojobinsight.Modell.EstadoPostulacion;
import com.jobinsight.proyectoretojobinsight.Modell.Postulacion;
import com.jobinsight.proyectoretojobinsight.Modell.Usuario;
import com.jobinsight.proyectoretojobinsight.Service.PostulacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/postulaciones")
public class PostulacionController {
    @Autowired
    private PostulacionService postulacionService;

    @PostMapping
    public ResponseEntity<Postulacion> crearPostulacion(@RequestBody Postulacion postulacion) {
        Postulacion nuevaPostulacion = postulacionService.crearPostulacion(postulacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPostulacion);  // Creamos la postulación
    }

    @GetMapping("/mias")
    public ResponseEntity<List<Postulacion>> obtenerMisPostulaciones(@AuthenticationPrincipal(expression = "usuario") Usuario usuario) {
        List<Postulacion> postulaciones = postulacionService.obtenerPostulacionesPorPerfil(usuario.getPerfil());
        return ResponseEntity.ok(postulaciones);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Postulacion> cambiarEstadoPostulacion(
            @PathVariable Long id, @RequestBody EstadoPostulacion estado) {
        Postulacion postulacion = postulacionService.cambiarEstadoPostulacion(id, estado);
        return ResponseEntity.ok(postulacion);  // Cambiamos el estado de la postulación
    }
}
