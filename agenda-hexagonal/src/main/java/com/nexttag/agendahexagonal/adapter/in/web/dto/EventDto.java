package com.nexttag.agendahexagonal.adapter.in.web.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventDto(UUID id, String title, String description, LocalDateTime date) {
}