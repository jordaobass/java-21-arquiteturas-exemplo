package com.nexttag.agendacqrs.query.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventQuery(UUID id, String title, String description, LocalDateTime date) {}