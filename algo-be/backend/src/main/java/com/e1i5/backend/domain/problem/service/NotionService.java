package com.e1i5.backend.domain.problem.service;

import org.springframework.http.ResponseEntity;

public interface NotionService {

    ResponseEntity<String> saveNotion(String apiKey, String dbId, int solvedProblemId);
}
