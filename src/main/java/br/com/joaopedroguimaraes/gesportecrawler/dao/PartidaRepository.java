package br.com.joaopedroguimaraes.gesportecrawler.dao;

import br.com.joaopedroguimaraes.gesportecrawler.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartidaRepository extends JpaRepository<Partida, Long> {

}