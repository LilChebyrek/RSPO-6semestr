package com.example.lab4.repository;

import com.example.lab4.model.Pet;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PetRepositoryImpl implements PetRepository {
    private final ConcurrentHashMap<Long, Pet> pets = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public List<Pet> findAll() {
        return List.copyOf(pets.values());
    }

    @Override
    public Optional<Pet> findById(Long id) {
        return Optional.ofNullable(pets.get(id));
    }

    @Override
    public Pet save(Pet pet) {
        if (pet.getId() == null) {
            pet.setId(idGenerator.getAndIncrement());
        }
        pets.put(pet.getId(), pet);
        return pet;
    }

    @Override
    public Pet update(Pet pet) {
        if (!pets.containsKey(pet.getId())) {
            throw new IllegalArgumentException("Pet not found");
        }
        pets.put(pet.getId(), pet);
        return pet;
    }

    @Override
    public void deleteById(Long id) {
        pets.remove(id);
    }
}
