package co.edu.uniandes.dse.parcial1.services;

import co.edu.uniandes.dse.parcial1.entities.EstacionEntity;
import co.edu.uniandes.dse.parcial1.entities.RutaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.repositories.EstacionRepository;
import co.edu.uniandes.dse.parcial1.repositories.RutaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class RutaEstacionService {

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private EstacionRepository estacionRepository;

    @Transactional
    public void addEstacionRuta(Long estacionId, Long rutaId) throws EntityNotFoundException {
        log.info("Inicia proceso de agregar una ruta a la estación con id = {}", estacionId);

        Optional<EstacionEntity> estacionEntity = estacionRepository.findById(estacionId);
        if (estacionEntity.isEmpty()) {
            throw new EntityNotFoundException("La estación proporcionada no existe");
        }

        Optional<RutaEntity> rutaEntity = rutaRepository.findById(rutaId);
        if (rutaEntity.isEmpty()) {
            throw new EntityNotFoundException("La ruta proporcionada no existe");
        }

        if (estacionEntity.get().getCapacidadPasajeros() < 100 && 
            estacionEntity.get().getRutas().stream().filter(r -> "circular".equalsIgnoreCase(r.getTipo())).count() >= 2) {
            throw new IllegalArgumentException("Una estación con capacidad menor a 100 pasajeros no puede tener más de 2 rutas circulares");
        }

        estacionEntity.get().getRutas().add(rutaEntity.get());
        estacionRepository.save(estacionEntity.get());
        log.info("Finaliza proceso de agregar una ruta a la estación con id = {}", estacionId);
    }

    @Transactional
    public void removeEstacionRuta(Long estacionId, Long rutaId) throws EntityNotFoundException {
        log.info("Inicia proceso de eliminar una ruta de la estación con id = {}", estacionId);

        Optional<EstacionEntity> estacionEntity = estacionRepository.findById(estacionId);
        if (estacionEntity.isEmpty()) {
            throw new EntityNotFoundException("La estación proporcionada no existe");
        }

        Optional<RutaEntity> rutaEntity = rutaRepository.findById(rutaId);
        if (rutaEntity.isEmpty()) {
            throw new EntityNotFoundException("La ruta proporcionada no existe");
        }

        if (rutaEntity.get().getTipo().equalsIgnoreCase("nocturna") && 
            estacionEntity.get().getRutas().stream().filter(r -> "nocturna".equalsIgnoreCase(r.getTipo())).count() <= 1) {
            throw new IllegalArgumentException("No se puede eliminar la única ruta nocturna de la estación");
        }

        estacionEntity.get().getRutas().remove(rutaEntity.get());
        estacionRepository.save(estacionEntity.get());
        log.info("Finaliza proceso de eliminar una ruta de la estación con id = {}", estacionId);
    }
}
