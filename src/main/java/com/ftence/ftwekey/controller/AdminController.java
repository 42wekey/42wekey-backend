package com.ftence.ftwekey.controller;

import com.ftence.ftwekey.dto.request.SubjectRequestDTO;
import com.ftence.ftwekey.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/subjects/list")
    public List<SubjectRequestDTO> getSubjects() {

        return adminService.getSubjectList();
    }

    @GetMapping("/subjects/{subjectId}")
    public SubjectRequestDTO getSubjectInfo(@PathVariable Long subjectId) {

        log.info("Received a request to get Subject Info by ID : {}", subjectId);

        return adminService.getSubjectInfo(subjectId);
    }

    @PostMapping("/subjects/create")
    public void createSubject(@RequestBody SubjectRequestDTO subjectRequestDTO) {

        log.info("Received a request to create Subject : {}", subjectRequestDTO);

        adminService.createSubject(subjectRequestDTO);
    }

    @PostMapping("/subjects/{subjectId}/edit")
    public void editSubject(@PathVariable Long subjectId,
                            @RequestBody SubjectRequestDTO subjectRequestDTO) {

        log.info("Received a request to edit Subject Info by ID: {}, {}", subjectId, subjectRequestDTO);

        adminService.editSubject(subjectId, subjectRequestDTO);
    }

    @DeleteMapping("/subjects/{subjectId}/delete")
    public void deleteSubject(@PathVariable Long subjectId) {

        log.info("Received a request to delete Subject by ID : {}", subjectId);

        adminService.deleteSubject(subjectId);
    }
}
