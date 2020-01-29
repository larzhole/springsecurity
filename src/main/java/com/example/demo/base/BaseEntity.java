package com.example.demo.base;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class BaseEntity {

    @Column(name = "is_active", columnDefinition = "BIT default 1", nullable = false)
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(
            name = "create_date_time",
            nullable = false,
            columnDefinition = "DATETIME default CURRENT_TIMESTAMP"
    )
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    @Column(
            name = "update_date_time",
            nullable = false,
            columnDefinition = "DATETIME default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP"
    )
    private LocalDateTime updateDateTime;
}
