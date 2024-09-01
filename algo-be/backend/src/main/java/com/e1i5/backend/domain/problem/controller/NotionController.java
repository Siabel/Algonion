package com.e1i5.backend.domain.problem.controller;

import com.e1i5.backend.domain.problem.request.NotionRequest;
import com.e1i5.backend.domain.problem.service.NotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/notion")
public class NotionController {

    @Autowired
    NotionService notionService;

    //사용자 키값을 어떻게 처리해야 할지
    //파라미터로 받기 or DB사용
    @PostMapping("/save")
    public ResponseEntity<String> notion(@RequestBody NotionRequest request) {
        System.out.println(request.getApiKey());
        System.out.println(request.getDbId());
        System.out.println(request.getSolvedProblemId());
        ResponseEntity<String> responseEntity = notionService.saveNotion("secret_h31SQQ4tJOmUXkpkujqSkyc0QiuFpZQnfT5QmN8chhs", "cb5fcf5432ec41a79601dec342d94017", request.getSolvedProblemId());
//        ResponseEntity<String> responseEntity = notionService.saveNotion(request.getApiKey(), request.getDbId());

        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
