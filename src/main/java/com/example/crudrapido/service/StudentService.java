package com.example.crudrapido.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.crudrapido.dto.StudentDTO;
import com.example.crudrapido.entity.Student;
import com.example.crudrapido.repository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public Page<StudentDTO> getStudents(int page, int size) {
        return studentRepository.findAll(PageRequest.of(page, size)).map(this::toDTO);
    }

    public Optional<StudentDTO> getStudent(Long id) {
        return studentRepository.findById(id).map(this::toDTO);
    }

    public StudentDTO create(StudentDTO dto) {
        Student student = new Student();
        copiar(dto, student);
        return toDTO(studentRepository.save(student));
    }

    public Optional<StudentDTO> update(Long id, StudentDTO dto) {
        return studentRepository.findById(id).map(student -> {
            copiar(dto, student);
            return toDTO(studentRepository.save(student));
        });
    }

    public boolean delete(Long id) {
        if (!studentRepository.existsById(id)) {
            return false;
        }
        studentRepository.deleteById(id);
        return true;
    }

    private void copiar(StudentDTO dto, Student student) {
        student.setFirstName(dto.firstName());
        student.setLastName(dto.lastName());
        student.setEmail(dto.email());
    }

    private StudentDTO toDTO(Student s) {
        return new StudentDTO(s.getStudentId(), s.getFirstName(), s.getLastName(), s.getEmail());
    }
}