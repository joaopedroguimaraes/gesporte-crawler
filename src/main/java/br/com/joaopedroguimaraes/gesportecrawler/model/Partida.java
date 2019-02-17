package br.com.joaopedroguimaraes.gesportecrawler.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="partidas")
public class Partida implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String championship;
    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduled_to;
    private String teams;

    protected Partida() {}

    public Partida(String championship, Date schedule_to, String teams) {
        this.championship = championship;
        this.scheduled_to = schedule_to;
        this.teams = teams;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChampionship() {
        return championship;
    }

    public void setChampionship(String championship) {
        this.championship = championship;
    }

    public Date getScheduled_to() {
        return scheduled_to;
    }

    public void setScheduled_to(Date scheduled_to) {
        this.scheduled_to = scheduled_to;
    }

    public String getTeams() {
        return teams;
    }

    public void setTeams(String teams) {
        this.teams = teams;
    }

    @Override
    public String toString() {
        return "Partida{" +
                "championship='" + championship + '\'' +
                ", scheduled_to=" + scheduled_to +
                ", teams='" + teams + '\'' +
                '}';
    }
}

