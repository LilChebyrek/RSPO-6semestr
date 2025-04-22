package com.example.lab4.repository;

import com.example.lab4.model.Pet;
import java.util.List;
import java.util.Optional;

public interface PetRepository {
    List<Pet> findAll();
    Optional<Pet> findById(Long id);
    Pet save(Pet pet);
    Pet update(Pet pet);
    void deleteById(Long id);
}
