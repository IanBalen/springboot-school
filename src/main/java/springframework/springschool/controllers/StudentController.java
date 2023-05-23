package springframework.springschool.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springframework.springschool.DTOs.StudentDTO;
import springframework.springschool.services.StudentService;
import springframework.springschool.services.request.CreateStudentRequest;
import springframework.springschool.services.request.UpdateStudentRequest;
import springframework.springschool.services.results.ActionResult;
import springframework.springschool.services.results.DataResult;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;


    @GetMapping()
    public ResponseEntity<DataResult<List<StudentDTO>>> getStudents(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) Integer ageL,
            @RequestParam(required = false) Integer ageH,
            @RequestParam(required = false) Integer year) {
        boolean hasName = Objects.nonNull(firstName);
        boolean hasSurname = Objects.nonNull(lastName);
        boolean hasAgeL = Objects.nonNull(ageL);
        boolean hasAgeH = Objects.nonNull(ageH);
        boolean hasYear = Objects.nonNull(year);

        if(hasYear)
            return ResponseEntity.ok(studentService.getStudentsByAcademicYear(year));

        if(hasAgeH && hasAgeL)
            return ResponseEntity.ok(studentService.getStudentsByAgeHigherAndLower(ageL, ageH));

        if (hasName)
            return ResponseEntity.ok(studentService.getStudentsByName(firstName)); //Marko Mar
        if (hasSurname)
            return  ResponseEntity.ok(studentService.getStudentsBySurname(lastName));

    //localhost:8080/student?name=Marko&surname=Filipovic
        return  ResponseEntity.ok(studentService.getStudents());
    }

    @PostMapping
    public ResponseEntity<ActionResult> createStudent(@RequestBody @Valid CreateStudentRequest request){
        return ResponseEntity.ok(studentService.createStudent(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ActionResult> deleteStudent(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(studentService.deleteStudent(id));
    }

    @PutMapping("/{id}/{subjectType}")
    public ResponseEntity<ActionResult> addSubjectToStudent(@PathVariable(value = "id") Long id,
                                    @PathVariable(value = "subjectType") String subjectType){
        return ResponseEntity.ok(studentService.addSubjectToStudent(id, subjectType));
    }

    @PutMapping("/{id}/{bookName}")
    public ResponseEntity<ActionResult> assignBookToStudent(@PathVariable(value = "id") Long id,
                                                            @PathVariable(value = "bookName") String bookName){
        return ResponseEntity.ok(studentService.assignBookToStudent(id, bookName));
    }

    @PutMapping("/{studentId}/{bookId}")
    public ResponseEntity<ActionResult> unassignBookFromStudent(@PathVariable(value = "studentId") Long studentId,
                                                                @PathVariable(value = "bookId") Long bookId){
        return ResponseEntity.ok(studentService.unassignBookFromStudent(studentId, bookId));
    }

    @PutMapping
    public ResponseEntity<ActionResult> updateStudent(@RequestBody @Valid UpdateStudentRequest request){
        return ResponseEntity.ok(studentService.updateStudent(request));
    }


}
