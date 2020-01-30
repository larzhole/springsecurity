package com.example.demo.fish;

import com.example.demo.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "fish_reference")
@Where(clause = "is_active = 1")
public class Fish { // extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fish_ref_sys_id")
    private Long fishRefSysId;

    @Column(name = "fish_ref_name")
    private String fishRefName;
}
