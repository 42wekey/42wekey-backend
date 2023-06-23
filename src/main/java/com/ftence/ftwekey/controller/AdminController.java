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

    @PostMapping("/subjects/create")
    public void createSubject(@RequestBody SubjectRequestDTO subjectRequestDTO){

        adminService.createSubject(subjectRequestDTO);
    }

    @PostMapping("/subjects/{subjectName}/edit")
    public void editSubject(@PathVariable String subjectName, @RequestBody SubjectRequestDTO subjectRequestDTO) {

        adminService.editSubject(subjectName, subjectRequestDTO);
    }

    @DeleteMapping("/subjects/{subjectName}/delete")
    public void deleteSubject(@PathVariable String subjectName) {

        adminService.deleteSubject(subjectName);
    }
}
