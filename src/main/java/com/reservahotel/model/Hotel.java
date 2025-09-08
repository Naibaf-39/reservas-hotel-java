package com.reservashotel.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Hoteles")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String direccion;
    private String ciudad;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Habitacion> habitaciones;

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public List<Habitacion> getHabitaciones() { return habitaciones; }
    public void setHabitaciones(List<Habitacion> habitaciones) { this.habitaciones = habitaciones; }
}