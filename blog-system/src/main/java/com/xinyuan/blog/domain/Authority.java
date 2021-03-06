package com.xinyuan.blog.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * Authority entity
 * @since 2018.2.21
 * @author Xinyuan Gui
 */
@Entity
public class Authority implements GrantedAuthority {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
