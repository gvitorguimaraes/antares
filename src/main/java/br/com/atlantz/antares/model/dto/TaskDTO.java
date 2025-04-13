package br.com.atlantz.antares.model.dto;

import java.time.LocalDateTime;

public record TaskDTO (String id, String title, String description, String statusCode, LocalDateTime endDate)
{
}
