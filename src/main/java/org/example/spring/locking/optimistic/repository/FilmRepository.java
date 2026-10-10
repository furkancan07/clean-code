package org.example.spring.locking.optimistic.repository;

import jakarta.persistence.LockModeType;
import org.example.spring.locking.optimistic.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
/**
 *  OPTIMISTIC: kaydı sadece okusan bile commit anında @Version değişmiş mi kontrol eder, değiştiyse OptimisticLockException fırlatır.
 *  OPTIMISTIC_FORCE_INCREMENT: entity'yi değiştirmesen bile commit'te @Version'ı artırır, böylece child değişiklikleri parent üzerinde çakışma olarak yakalanır.
 *  NONE (varsayılan): ekstra kilit yok, @Version sadece entity güncellenirken kontrol edilir, çakışma varsa OptimisticLockException fırlatır.
 *  */
public interface FilmRepository extends JpaRepository<Film, Long> {
   @Modifying
   @Query("UPDATE Film f SET f.name = :name WHERE f.id = :id")
   @Lock(value = LockModeType.OPTIMISTIC)
   int updateFilmNameById(@Param("id") Long id, @Param("name") String name);
   @Lock(value = LockModeType.OPTIMISTIC)
   Optional<Film> findById(Long id);
}
