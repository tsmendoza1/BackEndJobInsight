package com.jobinsight.proyectoretojobinsight.Controlller;

import com.jobinsight.proyectoretojobinsight.Service.NlqService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/nlq")
public class NlqController {
    private final NlqService servicio;

    public NlqController(NlqService servicio) {
        this.servicio = servicio;
    }

    @PostMapping(
            path = "/pregunta",
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Object consulta(@RequestBody String pregunta) {
        return servicio.answer(pregunta);
    }
}