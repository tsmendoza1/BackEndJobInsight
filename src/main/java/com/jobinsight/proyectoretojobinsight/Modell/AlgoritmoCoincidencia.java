package com.jobinsight.proyectoretojobinsight.Modell;


import java.util.ArrayList;
import java.util.List;

public class AlgoritmoCoincidencia {

    /**
     * Método para calcular la puntuación de coincidencia entre un candidato y una oferta laboral.
     * @param usuario El usuario (candidato) que está buscando trabajo.
     * @param ofertaLaboral La oferta laboral a la que el candidato se va a aplicar.
     * @return La puntuación de coincidencia entre el candidato y la oferta laboral (de 0 a 100).
     */
    public static double calcularCoincidencia(Usuario usuario, OfertaLaboral ofertaLaboral) {
        double puntuacionTotal = 0;

        // Ponderaciones (estos valores se pueden ajustar según la importancia que le quieras dar a cada criterio)
        double ponderacionHabilidades = 0.4;
        double ponderacionExperiencia = 0.3;
        double ponderacionUbicacion = 0.2;
        double ponderacionTipoTrabajo = 0.1;

        // 1. Coincidencia en habilidades
        double puntuacionHabilidades = calcularCoincidenciaHabilidades(usuario.getPerfil().getHabilidades(), ofertaLaboral.getRequisitos());
        puntuacionTotal += puntuacionHabilidades * ponderacionHabilidades;

        // 2. Coincidencia en experiencia
        double puntuacionExperiencia = calcularCoincidenciaExperiencia(usuario.getPerfil().getExperienciaLaboral(), ofertaLaboral.getRequisitosExperiencia());
        puntuacionTotal += puntuacionExperiencia * ponderacionExperiencia;

        // 3. Coincidencia en direccion
        double puntuacionUbicacion = calcularCoincidenciaUbicacion(usuario.getDireccion(), ofertaLaboral.getUbicacion(), ofertaLaboral.getTipoTrabajo());
        puntuacionTotal += puntuacionUbicacion * ponderacionUbicacion;

        // 4. Coincidencia en tipo de trabajo (Remoto, Presencial, Mixto)
        double puntuacionTipoTrabajo = calcularCoincidenciaTipoTrabajo(usuario.getTipo(), ofertaLaboral.getTipoTrabajo());
        puntuacionTotal += puntuacionTipoTrabajo * ponderacionTipoTrabajo;

        return puntuacionTotal;
    }

    /**
     * Método para calcular la coincidencia de habilidades entre el usuario y la oferta laboral.
     * @param habilidadesUsuario Las habilidades del usuario.
     * @param requisitosOferta Las habilidades requeridas en la oferta laboral.
     * @return La puntuación de coincidencia en habilidades (de 0 a 100).
     */
    private static double calcularCoincidenciaHabilidades(List<String> habilidadesUsuario, List<String> requisitosOferta) {
        int habilidadesCoincidentes = 0;

        for (String habilidad : habilidadesUsuario) {
            if (requisitosOferta.contains(habilidad)) {
                habilidadesCoincidentes++;
            }
        }

        // La puntuación será un porcentaje de habilidades coincidentes sobre el total de habilidades requeridas
        return (habilidadesCoincidentes / (double) requisitosOferta.size()) * 100;
    }

    /**
     * Método para calcular la coincidencia en experiencia laboral entre el usuario y la oferta laboral.
     * @param experienciaUsuario La experiencia laboral del usuario.
     * @param requisitosExperiencia La experiencia requerida por la oferta laboral.
     * @return La puntuación de coincidencia en experiencia (de 0 a 100).
     */
    private static double calcularCoincidenciaExperiencia(List<Experiencia> experienciaUsuario, List<String> requisitosExperiencia) {
        int experienciaCoincidente = 0;

        for (Experiencia exp : experienciaUsuario) {
            if (requisitosExperiencia.contains(exp.getDescripcion())) {
                experienciaCoincidente++;
            }
        }

        return (experienciaCoincidente / (double) requisitosExperiencia.size()) * 100;
    }

    /**
     * Método para calcular la coincidencia en ubicación entre el usuario y la oferta laboral.
     * @param ubicacionUsuario La ubicación del usuario.
     * @param ubicacionOferta La ubicación de la oferta laboral.
     * @param tipoTrabajo El tipo de trabajo de la oferta (presencial, remoto, mixto).
     * @return La puntuación de coincidencia en ubicación (de 0 a 100).
     */
    private static double calcularCoincidenciaUbicacion(String ubicacionUsuario, String ubicacionOferta, TipoTrabajo tipoTrabajo) {
        if (tipoTrabajo == TipoTrabajo.Remoto) {
            // Si la oferta es remota, la ubicación no importa
            return 100;
        }

        // Para ofertas presenciales o mixtas, comparamos la ubicación
        if (ubicacionUsuario.equalsIgnoreCase(ubicacionOferta)) {
            return 100;
        } else {
            // Si no coinciden exactamente, la puntuación será baja (por ejemplo, 30%).
            return 30;
        }
    }

    /**
     * Método para calcular la coincidencia en tipo de trabajo (remoto, presencial o mixto).
     * @param tipoUsuario El tipo de trabajo preferido por el usuario.
     * @param tipoOferta El tipo de trabajo de la oferta laboral.
     * @return La puntuación de coincidencia en tipo de trabajo (de 0 a 100).
     */
    private static double calcularCoincidenciaTipoTrabajo(TipoUsuario tipoUsuario, TipoTrabajo tipoOferta) {
        if (tipoOferta == TipoTrabajo.Remoto && tipoUsuario == TipoUsuario.CANDIDATO) {
            return 100; // El candidato prefiere trabajo remoto
        } else if (tipoOferta == TipoTrabajo.Presencial && tipoUsuario == TipoUsuario.EMPLEADOR) {
            return 100; // El empleador prefiere trabajo presencial
        } else if (tipoOferta == TipoTrabajo.Mixto && tipoUsuario == TipoUsuario.CANDIDATO) {
            return 70; // El trabajo mixto podría ser más flexible
        } else {
            return 0; // Si el tipo no coincide, la puntuación es 0
        }
    }

    /**
     * Método que calcula las coincidencias entre la oferta laboral y los candidatos
     * @param ofertaLaboral La oferta laboral.
     * @param candidatos Lista de candidatos.
     * @return Lista de coincidencias.
     */
    public static List<Coincidencia> calcularCoincidencias(OfertaLaboral ofertaLaboral, List<Usuario> candidatos) {
        List<Coincidencia> coincidencias = new ArrayList<>();

        for (Usuario candidato : candidatos) {
            // Calculamos la coincidencia de cada candidato con la oferta laboral
            double puntuacion = calcularCoincidencia(candidato, ofertaLaboral);

            // Si la puntuación es suficientemente alta, agregamos la coincidencia
            if (puntuacion >= 50) {
                Coincidencia coincidencia = new Coincidencia();
                coincidencia.setOfertaId(ofertaLaboral.getId());
                coincidencia.setPuntuacion(puntuacion);
                coincidencias.add(coincidencia);
            }
        }
        return coincidencias;
    }
}