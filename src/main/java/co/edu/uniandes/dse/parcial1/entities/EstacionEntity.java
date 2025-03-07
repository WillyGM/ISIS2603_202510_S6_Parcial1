package co.edu.uniandes.dse.parcial1.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class EstacionEntity extends BaseEntity {
    private String name;
    private String direccion;
    private Integer capacidad;
    
    @ManyToMany(mappedBy = "estaciones")
    private List<RutaEntity> rutas;

    private Integer capacidadPasajeros;

    public Integer getCapacidadPasajeros() {
        return capacidadPasajeros;
    }
    
    public void setCapacidadPasajeros(Integer capacidadPasajeros) {
        this.capacidadPasajeros = capacidadPasajeros;
    }

}
