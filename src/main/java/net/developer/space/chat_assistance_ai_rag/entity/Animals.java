package net.developer.space.chat_assistance_ai_rag.entity;

/**
 * Entity representing animals. 
 * 
 * @author Lazaro Noel Guerra Medina
 * @since 2025-09-08
 */

public record Animals(
    int id,
    String name,
    String species,
    String habitat,
    String diet,
    int lifespan,
    float weight,
    String description
) {}
