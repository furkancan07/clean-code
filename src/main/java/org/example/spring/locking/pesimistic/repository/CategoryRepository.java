package org.example.spring.locking.pesimistic.repository;

import jakarta.persistence.LockModeType;
import org.example.spring.locking.pesimistic.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * PESSIMISTIC_READ: başkası okuyabilir ama güncelleyemez |
 * PESSIMISTIC_WRITE: tam kilit, kimse kilitleyemez/güncelleyemez
 * FORCE_INCREMENT: WRITE gibi kilitler + @Version'ı artırır.*/
 @Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Lock(value = LockModeType.PESSIMISTIC_READ)
    Optional<Category> findById(Long id);

}
