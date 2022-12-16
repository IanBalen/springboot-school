package springframework.springschool.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springframework.springschool.DTOs.ProfessorDTO;
import springframework.springschool.services.ProfessorService;
import springframework.springschool.services.request.CreateProfessorRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/professor")
@AllArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;

    @GetMapping
    public List<ProfessorDTO> getProfessors(
            @RequestParam(required = false) String subject
    ){
        boolean hasSubject = Objects.nonNull(subject);

        if(hasSubject)
            return professorService.getProfessorsBySubject(subject);

        return professorService.getProfessors();
    }

    @PostMapping
    public  void addNewProfessor(@RequestBody @Valid CreateProfessorRequest request) {
        professorService.addNewProfessor(request);
    }

    @PutMapping(path = "/{id}")
    public void editSubject(
            @PathVariable(value = "id") Long id,
            @RequestParam String subjectType
    ){
        professorService.editSubject(id , subjectType);
        }

    @DeleteMapping(path = "{id}")
    public void deleteProfessor(@PathVariable(value = "id") Long id){
         professorService.deleteProfessor(id);
    }


}
