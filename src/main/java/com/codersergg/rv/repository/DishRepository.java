package com.codersergg.rv.repository;

import com.codersergg.rv.model.Dish;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Tag(name = "Dish Controller")
public interface DishRepository extends JpaRepository<Dish, Integer> {

    @Query("SELECT d FROM Dish d WHERE d.id = ?1")
    Dish getOneById(int id);

    @Query("SELECT d FROM Dish d WHERE d.created = current_date ")
    List<Dish> findAllCreatedToday();

    @Override
    @Modifying
    @Transactional
    @CacheEvict(value = "dish", allEntries = true)
    void deleteById(Integer integer);

    @Override
    @Modifying
    @Transactional
    @CachePut(value = "dish")
    Dish save(Dish dish);

}
