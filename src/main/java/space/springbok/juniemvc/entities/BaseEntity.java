package space.springbok.juniemvc.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    public BaseEntity() {
    }

    public BaseEntity(Integer id, Integer version, LocalDateTime createdDate, LocalDateTime updateDate) {
        this.id = id;
        this.version = version;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Version
    private Integer version;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;
}
