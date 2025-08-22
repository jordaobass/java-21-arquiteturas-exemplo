package com.nexttag.agendacqrs.api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventDto(UUID id, String title, String description, LocalDateTime date) {}