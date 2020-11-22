package com.dungeoncrawler.Javiarenka.board;

import com.dungeoncrawler.Javiarenka.creature.Creature;
import com.dungeoncrawler.Javiarenka.creature.Hero;
import com.dungeoncrawler.Javiarenka.creature.Monster;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class BoardController {
    BoardService service = new BoardService();

    //todo: this functionality will be deprecated and replaced in future release
    @GetMapping("/fightBoard")
    public String fightGet(Model model) {
        model.addAttribute("heroes", service.getHeroes());
        model.addAttribute("monsters", service.getMonsters());
        model.addAttribute("consoleText", service.getMessageOutput());
        return "fightBoard";
    }

    @GetMapping("/encounterBoard")
    public String encounter(Model model) {
        if (!BoardService.isAlreadyLoaded) service.prepareTheBoard();
        BoardService.isAlreadyLoaded = true;
        model.addAttribute("heroes", service.getHeroes());
        model.addAttribute("monsters", service.getMonsters());
        model.addAttribute("consoleText", service.getMessageOutput());
        model.addAttribute("boardWidth", service.getBoardWidth());
        model.addAttribute("boardHeight", service.getBoardHeight());
        model.addAttribute("tiles", service.getTiles());
        model.addAttribute("tileImages", service.getImageSources());
        model.addAttribute("initiativeOrder", service.getInitiativeOrder());
        return "encounterBoard";
    }

    @PutMapping("/encounterBoard/heroesStatus")
    public ResponseEntity<List<Hero>> putNewHeroesStatus(@RequestBody List<Hero> heroList) {
        for (Hero h : heroList) {
            System.out.println(h.getName());
            System.out.println(h.getEncounterXPosition());
            System.out.println(h.getEncounterYPosition());
            System.out.println("ACTIVE: " + h.isActive());
        }
        System.out.println("Hero status update completed");
        service.setHeroes(heroList);
        return new ResponseEntity<>(heroList, HttpStatus.OK);
    }

    @PutMapping("/encounterBoard/initiativeOrder")
    public ResponseEntity<List<Creature>> putNewInitiativeStatus(@RequestBody List<Creature> initiativeOrder) {
        for (Creature h : initiativeOrder) {
            System.out.println(h.getName());
            System.out.println(h.getEncounterXPosition());
            System.out.println(h.getEncounterYPosition());
            System.out.println("ACTIVE: " + h.isActive());
        }
        System.out.println("Initiative update completed");
        service.setInitiativeOrder(initiativeOrder);
        return new ResponseEntity<>(initiativeOrder, HttpStatus.OK);
    }

    @PostMapping("/encounterBoard/monstersStatus")
    public String postNewMonstersStatus(Model model) {
        System.out.println("Monster status update");
        return "encounterBoard";
    }

    @GetMapping("/fightBoard/attackHero")
    public RedirectView attackHero(Hero heroFromForm, Monster monsterFromForm) {
        if (service.getSelectedHero() != null && service.getSelectedMonster() != null) {
            //todo: remove this later
            service.getMessageOutput().clear();
            service.getMessageOutput().add(service.getSelectedMonster().attack(service.getSelectedHero()));
            service.setSelectedHero(null);
            service.setSelectedMonster(null);
        } else {
            //todo: remove this later
            service.getMessageOutput().clear();
            service.getMessageOutput().add("Hero or Monster not selected!");
        }
        return new RedirectView("");
    }

    @GetMapping("/fightBoard/attackMonster")
    public RedirectView attackMonster(Hero heroFromForm, Monster monsterFromForm) {
        if (service.getSelectedHero() != null && service.getSelectedMonster() != null) {
            //todo: remove this later
            service.getMessageOutput().clear();
            service.getMessageOutput().add(service.getSelectedHero().attack(service.getSelectedMonster()));
            service.setSelectedHero(null);
            service.setSelectedMonster(null);
        } else {
            //todo: remove this later
            service.getMessageOutput().clear();
            service.getMessageOutput().add("Hero or Monster not selected!");
        }
        return new RedirectView("");
    }

    @GetMapping("/fightBoard/selectHero")
    public RedirectView selectHero(Model model, @RequestParam(value = "heroName") String heroName) {
        service.setSelectedHero(service.getCurrentHeroByName(heroName));
        return new RedirectView("");
    }

    @GetMapping("/fightBoard/selectMonster")
    public RedirectView selectMonster(Model model, @RequestParam(value = "monsterName") String monsterName) {
        service.setSelectedMonster(service.getCurrentMonsterByName(monsterName));
        return new RedirectView("");
    }
}
