package springframework.springschool.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springframework.springschool.DTOs.StudentDTO;
import springframework.springschool.services.StudentService;
import springframework.springschool.services.request.CreateStudentRequest;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/student")
@AllArgsConstructor
public class StudentController {

private final StudentService studentService;

    @GetMapping()
    public List<StudentDTO> getStudents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) Integer ageL,
            @RequestParam(required = false) Integer ageH) {
        boolean hasName = Objects.nonNull(name);
        boolean hasSurname = Objects.nonNull(surname);
        boolean hasAgeL = Objects.nonNull(ageL);
        boolean hasAgeH = Objects.nonNull(ageH);

        if(hasAgeH && hasAgeL)
            return studentService.getStudentsByAgeHigherAndLower(ageL, ageH);

        if (hasName)
            return studentService.getStudentsByName(name); //Marko Mar
        if (hasSurname)
            return studentService.getStudentsBySurname(surname);

    //localhost:8080/student?name=Marko&surname=Filipovic
        return studentService.getStudents();
    }

    @PostMapping
    public void addNewStudent(@RequestBody CreateStudentRequest request){
        studentService.addNewStudent(request);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable(value = "id") Long id){
        studentService.deleteStudent(id);
    }

    @PutMapping("/{id}/{subjectType}")
    public void addSubjectToStudent(@PathVariable(value = "id") Long id,
                                    @PathVariable(value = "subjectType") String subjectType){

        studentService.addSubjectToStudent(id, subjectType);


    }
}
