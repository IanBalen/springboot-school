package springframework.springschool.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springframework.springschool.DTOs.SubjectDTO;
import springframework.springschool.services.SubjectService;
import springframework.springschool.services.request.CreateSubjectRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/subject")
@AllArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping
    public List<SubjectDTO> getSubjects(
            @RequestParam(required = false) String classroom,
            @RequestParam(required = false) String subjectType
            ){

        boolean hasClassroom = Objects.nonNull(classroom);
        boolean hasSubjectType = Objects.nonNull(subjectType);

        if(hasClassroom) {
            return subjectService.findByClassroom(classroom);
        }
        if(hasSubjectType) {
            List<SubjectDTO> list = new ArrayList<>();
            list.add(subjectService.findBySubjectType(subjectType));
            return list;
        }

        return subjectService.getSubjects();
    }

    @PostMapping
    public void addNewSubject(@RequestBody CreateSubjectRequest request){
        subjectService.addNewSubject(request);
    }

    @DeleteMapping("/{id}")
    public void deleteSubject(@PathVariable(value = "id") Long id){
        subjectService.deleteSubject(id);
    }

}
