package springframework.springschool.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springframework.springschool.DTOs.ProfessorDTO;
import springframework.springschool.services.ProfessorService;
import springframework.springschool.services.request.CreateProfessorRequest;
import springframework.springschool.services.results.ActionResult;
import springframework.springschool.services.results.DataResult;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/professor")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;

    @GetMapping
    public ResponseEntity<DataResult<List<ProfessorDTO>>> getProfessors(
            @RequestParam(required = false) String subject
    ){
        boolean hasSubject = Objects.nonNull(subject);

        if(hasSubject)
            return ResponseEntity.ok(professorService.getProfessorsBySubject(subject));

        return ResponseEntity.ok(professorService.getProfessors());
    }

    @PostMapping
    public ResponseEntity<ActionResult> addNewProfessor(@RequestBody @Valid CreateProfessorRequest request) {
        return ResponseEntity.ok(professorService.addNewProfessor(request));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ActionResult> editSubject(
            @PathVariable(value = "id") Long id,
            @RequestParam String subjectType
    ){
        return ResponseEntity.ok(professorService.editSubject(id , subjectType));
        }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ActionResult> deleteProfessor(@PathVariable(value = "id") Long id){
         return ResponseEntity.ok(professorService.deleteProfessor(id));
    }

    @PutMapping(path = "/b{id}/{bookName}")
    public ResponseEntity<ActionResult> assignBookToProfessor(@PathVariable(value = "id") Long id,
                                              @PathVariable(value = "bookName") String bookName){
        return ResponseEntity.ok(professorService.assignBookToProfessor(id, bookName));
    }

    @PutMapping(path = "/r{professorId}/{bookId}")
    public ResponseEntity<ActionResult> unassignBookFromProfessor(@PathVariable(value = "professorId") Long professorId,
                                                              @PathVariable(value = "bookId") Long bookId){

        return ResponseEntity.ok(professorService.unassignBookFromProfessor(professorId, bookId));
    }


}
