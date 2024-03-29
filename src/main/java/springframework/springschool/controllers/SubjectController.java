package springframework.springschool.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springframework.springschool.DTOs.SubjectDTO;
import springframework.springschool.services.SubjectService;
import springframework.springschool.services.request.CreateSubjectRequest;
import springframework.springschool.services.results.ActionResult;
import springframework.springschool.services.results.DataResult;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/subject")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping
    public ResponseEntity<DataResult<List<SubjectDTO>>> getSubjects(
            @RequestParam(required = false) String classroom,
            @RequestParam(required = false) String subjectType
            ){

        boolean hasClassroom = Objects.nonNull(classroom);
        boolean hasSubjectType = Objects.nonNull(subjectType);

        if(hasClassroom) {
            return ResponseEntity.ok(subjectService.findByClassroom(classroom));
        }
        if(hasSubjectType) {
            return ResponseEntity.ok(subjectService.findBySubjectType(subjectType));
        }

        return ResponseEntity.ok(subjectService.getSubjects());
    }

    @PostMapping
    public ResponseEntity<ActionResult> createSubject(@RequestBody @Valid CreateSubjectRequest request){
        return ResponseEntity.ok(subjectService.createSubject(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ActionResult> deleteSubject(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(subjectService.deleteSubject(id));
    }

}
