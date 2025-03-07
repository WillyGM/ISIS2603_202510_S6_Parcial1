package co.edu.uniandes.dse.parcial1.services;

import co.edu.uniandes.dse.parcial1.entities.RutaEntity;
import co.edu.uniandes.dse.parcial1.entities.EstacionEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.repositories.RutaRepository;
import co.edu.uniandes.dse.parcial1.repositories.EstacionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class RutaService {

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private EstacionRepository estacionRepository;

    @Transactional
    public RutaEntity createRuta(RutaEntity ruta) {
        log.info("Creando ruta: {}", ruta.getNombre());
        return rutaRepository.save(ruta);
    }

    @Transactional
    public List<RutaEntity> getRutas() {
        return rutaRepository.findAll();
    }

    @Transactional
    public RutaEntity getRuta(Long id) throws EntityNotFoundException {
        return rutaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Ruta no encontrada"));
    }

    @Transactional
    public RutaEntity updateRuta(Long id, RutaEntity ruta) throws EntityNotFoundException {
        RutaEntity existingRuta = getRuta(id);
        existingRuta.setNombre(ruta.getNombre());
        existingRuta.setColor(ruta.getColor());
        existingRuta.setTipo(ruta.getTipo());
        return rutaRepository.save(existingRuta);
    }

    @Transactional
    public void deleteRuta(Long id) throws EntityNotFoundException {
        RutaEntity ruta = getRuta(id);
        rutaRepository.delete(ruta);
    }

    @Transactional
    public RutaEntity addEstacionToRuta(Long rutaId, Long estacionId) throws EntityNotFoundException {
        RutaEntity ruta = getRuta(rutaId);
        EstacionEntity estacion = estacionRepository.findById(estacionId)
            .orElseThrow(() -> new EntityNotFoundException("Estación no encontrada"));
        ruta.getEstaciones().add(estacion);
        return rutaRepository.save(ruta);
    }

}