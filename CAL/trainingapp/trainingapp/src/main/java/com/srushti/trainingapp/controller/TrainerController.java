package com.srushti.trainingapp.controller;

import com.srushti.trainingapp.model.Trainer;
import com.srushti.trainingapp.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TrainerController {

    @Autowired
    private TrainerService trainerService;

    @GetMapping("/trainers")
    public String viewAllTrainers(Model model) {
        model.addAttribute("trainers", trainerService.getAllTrainers());
        return "trainers"; // HTML file name
    }

    @GetMapping("/trainers/new")
    public String showCreateForm(Model model) {
        model.addAttribute("trainer", new Trainer());
        return "trainer_form";
    }

    @PostMapping("/trainers/save")
    public String saveTrainer(@ModelAttribute("trainer") Trainer trainer) {
        trainerService.saveTrainer(trainer);
        return "redirect:/trainers";
    }

    @GetMapping("/trainers/delete/{id}")
    public String deleteTrainer(@PathVariable Long id) {
        trainerService.deleteTrainer(id);
        return "redirect:/trainers";
    }
}
