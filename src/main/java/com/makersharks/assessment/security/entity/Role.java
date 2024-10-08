package com.makersharks.assessment.security.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)

public class Role {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;


    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<AppUser> users;
    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;
}
