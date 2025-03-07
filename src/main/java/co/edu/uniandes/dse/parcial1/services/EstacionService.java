package co.edu.uniandes.dse.parcial1.services;

import co.edu.uniandes.dse.parcial1.entities.EstacionEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.repositories.EstacionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class EstacionService {

    @Autowired
    private EstacionRepository estacionRepository;

    @Transactional
    public EstacionEntity createEstacion(EstacionEntity estacion) {
        log.info("Creando estación: {}", estacion.getName());
        return estacionRepository.save(estacion);
    }

    @Transactional
    public List<EstacionEntity> getEstaciones() {
        return estacionRepository.findAll();
    }

    @Transactional
    public EstacionEntity getEstacion(Long id) throws EntityNotFoundException {
        return estacionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Estación no encontrada"));
    }
}
