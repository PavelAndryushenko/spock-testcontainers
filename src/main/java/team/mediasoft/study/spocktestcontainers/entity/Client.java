package team.mediasoft.study.spocktestcontainers.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "clients")
@Getter
@Setter
public class Client {

    @Id
    private Long id;

    @Column
    private String fullname;
}
