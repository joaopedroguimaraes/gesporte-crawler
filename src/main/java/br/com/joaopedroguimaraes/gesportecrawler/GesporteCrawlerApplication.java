package br.com.joaopedroguimaraes.gesportecrawler;

import br.com.joaopedroguimaraes.gesportecrawler.dao.PartidaRepository;
import br.com.joaopedroguimaraes.gesportecrawler.model.Partida;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class GesporteCrawlerApplication {

    private static final Logger log = LoggerFactory.getLogger(GesporteCrawlerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(GesporteCrawlerApplication.class, args);
	}

    @Bean
    public CommandLineRunner crawler(PartidaRepository repository) {
        return (args) -> {
            WebClient browser = new WebClient();
            browser.getOptions().setJavaScriptEnabled(false);
            browser.getOptions().setCssEnabled(false);
            log.info("WebClient is ready now");

            for (int i = 0; i <= 3; i++) {
                LocalDate date = LocalDate.now().plusDays(i);
                collectPartidas(repository, browser, date);
            }
            log.info("All matches saved");
        };
    }

	private static void collectPartidas(PartidaRepository repo, WebClient browser, LocalDate dateToCollect) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = dateToCollect.format(formatter);

        String baseUrl = "https://globoesporte.globo.com/placar-ge/%s/jogos.ghtml";
        String urlToLoad = String.format(baseUrl, formattedDate);

        List<Partida> partidas = new ArrayList<Partida>();

        try {
            log.info("Loading url = " + urlToLoad);
            HtmlPage page = browser.getPage(urlToLoad);
            log.info("Loaded " + urlToLoad);
            final List<HtmlElement> cards = page.getByXPath("//article");
            if (cards.isEmpty()) {
                log.info("Não há jogos agendados para " + formattedDate);
            } else {
                log.info("Jogos agendados para " + formattedDate + ": " + cards.size());
                for (HtmlElement card : cards) {
                    String championship = card.getElementsByTagName("span").get(0).getTextContent();
                    String horario = card.getElementsByTagName("time").get(0).getTextContent().trim();
                    Date scheduled_to = toDate(formattedDate, horario);
                    List<HtmlElement> timesSpan = card.getElementsByAttribute("span", "class",
                            "nome nome-completo"); // getElementsByTagName("span").get(0).getTextContent();
                    String timeUm = timesSpan.get(0).getTextContent().trim();
                    String timeDois = timesSpan.get(1).getTextContent().trim();
                    String teams_playing = timeUm + " x " + timeDois;
                    Partida partida = new Partida(championship, scheduled_to, teams_playing);
                    log.info(partida.toString());
                    partidas.add(partida);
                }
                log.info("All matches for " + formattedDate + " are ready! Saving to database...");
                repo.saveAll(partidas);
                log.info("> Saved all matches for " + formattedDate);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getStackTrace().toString());
        }
    }

    private static Date toDate(String date, String time) {
	    String sDateTime = date + " " + time;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date new_date;
        try {
            new_date = formatter.parse(sDateTime);
            return new_date;
        } catch (ParseException e) {
            e.printStackTrace();
            new_date = new Date();
            log.error("Parse date error, assuming date = " + new_date);
        }
        return new_date;
    }

}