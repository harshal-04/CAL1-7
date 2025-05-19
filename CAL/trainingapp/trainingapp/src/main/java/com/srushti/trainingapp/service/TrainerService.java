package com.srushti.trainingapp.service;

import com.srushti.trainingapp.model.Trainer;
import com.srushti.trainingapp.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService {

    @Autowired
    private TrainerRepository trainerRepository;

    // Fetch all trainers
    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }

    // Save or update a trainer
    public Trainer saveTrainer(Trainer trainer) {
        return trainerRepository.save(trainer);
    }

    // Get trainer by ID
    public Trainer getTrainerById(Long id) {
        return trainerRepository.findById(id).orElse(null);
    }

    // Delete trainer by ID
    public void deleteTrainer(Long id) {
        trainerRepository.deleteById(id);
    }
}
