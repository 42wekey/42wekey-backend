package com.ftence.ftwekey.controller;

import com.ftence.ftwekey.dto.request.SubjectRequestDTO;
import com.ftence.ftwekey.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/subject/create")
    public void createSubject(@RequestBody SubjectRequestDTO subjectRequestDTO){

        adminService.createSubject(subjectRequestDTO);
    }

    @PostMapping("/subject/{subjectName}/edit")
    public void editSubject(@PathVariable String subjectName, @RequestBody SubjectRequestDTO subjectRequestDTO) {

        adminService.editSubject(subjectName, subjectRequestDTO);
    }

    @DeleteMapping("/subject/{subjectName}/delete")
    public void deleteSubject(@PathVariable String subjectName) {

        adminService.deleteSubject(subjectName);
    }
}
