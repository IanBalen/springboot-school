package springframework.springschool.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springframework.springschool.DTOs.StudentDTO;
import springframework.springschool.services.StudentService;
import springframework.springschool.services.request.CreateStudentRequest;
import springframework.springschool.services.results.ActionResult;
import springframework.springschool.services.results.DataResult;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/student")
@AllArgsConstructor
public class StudentController {

private final StudentService studentService;

    @GetMapping()
    public ResponseEntity<DataResult<List<StudentDTO>>> getStudents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) Integer ageL,
            @RequestParam(required = false) Integer ageH) {
        boolean hasName = Objects.nonNull(name);
        boolean hasSurname = Objects.nonNull(surname);
        boolean hasAgeL = Objects.nonNull(ageL);
        boolean hasAgeH = Objects.nonNull(ageH);

        if(hasAgeH && hasAgeL)
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(studentService.getStudentsByAgeHigherAndLower(ageL, ageH));

        if (hasName)
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(studentService.getStudentsByName(name)); //Marko Mar
        if (hasSurname)
            return  ResponseEntity.ok(studentService.getStudentsBySurname(surname));

    //localhost:8080/student?name=Marko&surname=Filipovic
        return  ResponseEntity.ok(studentService.getStudents());
    }

    @PostMapping
    public ResponseEntity<ActionResult> addNewStudent(@RequestBody @Valid CreateStudentRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.addNewStudent(request));
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
