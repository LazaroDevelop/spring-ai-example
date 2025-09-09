package net.developer.space.chat_assistance_ai_rag.repository;

import org.springframework.data.repository.ListCrudRepository;

import net.developer.space.chat_assistance_ai_rag.entity.Animals;

/**
 * Repository interface for managing Animals entities.
 * This interface extends ListCrudRepository to provide CRUD operations.
 * 
 * @autor Lazaro Noel Guerra Medina
 * @since 2025-09-08
 */
public interface AnimalsRepository extends ListCrudRepository<Animals, Integer>{}
