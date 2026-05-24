package com.example.resume.submission;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/submissions")
public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService){
        this.submissionService = submissionService;
    }

    @GetMapping
    public ResponseEntity<List<SubmissionResponse>> getAllSubmissions(){
        return new ResponseEntity<>(submissionService.getAllSubmissions(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubmissionResponse> getSubmissionById(@PathVariable Long id){
        return new ResponseEntity<>(submissionService.getSubmissionById(id),HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<SubmissionResponse> saveSubmission(@RequestBody SubmissionRequest submissionRequest){
        return new ResponseEntity<>(submissionService.createSubmission(submissionRequest),HttpStatus.CREATED);
    }
}
