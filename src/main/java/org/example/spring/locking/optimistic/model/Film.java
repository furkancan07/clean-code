package org.example.spring.locking.optimistic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.time.OffsetDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE Film SET deleted = true WHERE id = ?")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Version
    @Column(nullable = false)
    private Long version;
    @Column( nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column( nullable = false)
    private OffsetDateTime updatedAt;

    private boolean deleted=false;


    @PrePersist
    public void prePersist() {
        createdAt = OffsetDateTime.now();
        updatedAt=OffsetDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt=OffsetDateTime.now();
    }


}
