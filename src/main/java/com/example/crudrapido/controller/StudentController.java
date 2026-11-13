package com.example.crudrapido.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.crudrapido.dto.StudentDTO;
import com.example.crudrapido.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // LEER con paginacion: /api/v1/students?page=0&size=5
    @GetMapping
    public ResponseEntity<Page<StudentDTO>> getAll(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(studentService.getStudents(page, size));
    }

    // LEER uno
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getById(@PathVariable("id") Long id) {
        return studentService.getStudent(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // CREAR
    @PostMapping
    public ResponseEntity<StudentDTO> create(@Valid @RequestBody StudentDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.create(dto));
    }

    // ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> update(@PathVariable("id") Long id, @Valid @RequestBody StudentDTO dto) {
        return studentService.update(id, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        if (!studentService.delete(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}