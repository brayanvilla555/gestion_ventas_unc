package org.msvc_venta.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.msvc_venta.util.EstadoVenta;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fechaVenta;

    @Embedded
    private DireccionEntrega direccion;

    @Column(nullable = false)
    private Long clieteId;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemVenta> itemsVenta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoVenta estado;

    @Column
    private Double total;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public void calcularTotal() {
        this.total = itemsVenta.stream().mapToDouble(ItemVenta::getSubTotal).sum();
    }

    public void agregarItem(ItemVenta item) {
        this.itemsVenta.add(item);
        item.setVenta(this); // Set the back reference
        calcularTotal();
    }

    public void cancelarVenta() {
        this.estado = EstadoVenta.CANCELADA;
    }
}