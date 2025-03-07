package co.edu.uniandes.dse.parcial1.services;

import co.edu.uniandes.dse.parcial1.entities.EstacionEntity;
import co.edu.uniandes.dse.parcial1.entities.RutaEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
@Import(RutaEstacionService.class)
class RutaEstacionServiceTest {

    @Autowired
    private RutaEstacionService rutaEstacionService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<RutaEntity> rutaList = new ArrayList<>();
    private List<EstacionEntity> estacionList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from RutaEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from EstacionEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            RutaEntity ruta = factory.manufacturePojo(RutaEntity.class);
            entityManager.persist(ruta);
            rutaList.add(ruta);

            EstacionEntity estacion = factory.manufacturePojo(EstacionEntity.class);
            entityManager.persist(estacion);
            estacionList.add(estacion);
        }
    }

    @Test
    void testAddEstacionRuta() throws EntityNotFoundException {
        RutaEntity ruta = rutaList.get(0);
        EstacionEntity estacion = estacionList.get(0);

        rutaEstacionService.addEstacionRuta(estacion.getId(), ruta.getId());
        assertTrue(ruta.getEstaciones().contains(estacion));
    }

    @Test
    void testAddEstacionRuta_InvalidEstacion() {
        assertThrows(EntityNotFoundException.class, () -> {
            RutaEntity ruta = rutaList.get(0);
            rutaEstacionService.addEstacionRuta(0L, ruta.getId());
        });
    }

    @Test
    void testAddEstacionRuta_InvalidRuta() {
        assertThrows(EntityNotFoundException.class, () -> {
            EstacionEntity estacion = estacionList.get(0);
            rutaEstacionService.addEstacionRuta(estacion.getId(), 0L);
        });
    }

    @Test
    void testRemoveEstacionRuta() throws EntityNotFoundException {
        RutaEntity ruta = rutaList.get(0);
        EstacionEntity estacion = estacionList.get(0);

        rutaEstacionService.addEstacionRuta(estacion.getId(), ruta.getId());
        rutaEstacionService.removeEstacionRuta(estacion.getId(), ruta.getId());
        assertFalse(ruta.getEstaciones().contains(estacion));
    }

    @Test
    void testRemoveEstacionRuta_InvalidEstacion() {
        assertThrows(EntityNotFoundException.class, () -> {
            RutaEntity ruta = rutaList.get(0);
            rutaEstacionService.removeEstacionRuta(0L, ruta.getId());
        });
    }

    @Test
    void testRemoveEstacionRuta_InvalidRuta() {
        assertThrows(EntityNotFoundException.class, () -> {
            EstacionEntity estacion = estacionList.get(0);
            rutaEstacionService.removeEstacionRuta(estacion.getId(), 0L);
        });
    }
}

