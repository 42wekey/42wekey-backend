package com.ftence.ftwekey.controller;

import com.ftence.ftwekey.dto.request.SubjectRequestDTO;
import com.ftence.ftwekey.service.AdminService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        return adminService.getSubjectInfo(subjectId);
    }

    @PostMapping("/subjects/create")
    public void createSubject(@RequestBody SubjectRequestDTO subjectRequestDTO) {

        adminService.createSubject(subjectRequestDTO);
    }

    @PostMapping("/subjects/{subjectId}/edit")
    public void editSubject(@PathVariable Long subjectId, @RequestBody SubjectRequestDTO subjectRequestDTO) {

        adminService.editSubject(subjectId, subjectRequestDTO);
    }

    @DeleteMapping("/subjects/{subjectId}/delete")
    public void deleteSubject(@PathVariable Long subjectId) {

        adminService.deleteSubject(subjectId);
    }
}
