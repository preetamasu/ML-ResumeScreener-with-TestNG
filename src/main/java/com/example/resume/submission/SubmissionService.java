package com.example.resume.submission;

import com.example.resume.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubmissionService {

    private final SubmissionRepository submissionRepository;

    public SubmissionService(SubmissionRepository submissionRepository){
        this.submissionRepository = submissionRepository;
    }

    public List<SubmissionResponse> getAllSubmissions(){
        return submissionRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public SubmissionResponse createSubmission(SubmissionRequest submissionRequest){
          Submission submission = new Submission();
          submission.setResumeText(submissionRequest.resumeText());
          submission.setJobDescription(submissionRequest.jobDescription());
          submission.setStatus(SubmissionStatus.PENDING);
          submission.setCreatedAt(LocalDateTime.now());
          submissionRepository.save(submission);
          return toResponse(submission);
    }


    public SubmissionResponse getSubmissionById(Long id){
        Submission submission = submissionRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("We cannot find an submission with that id"+id));
        return toResponse(submission);
    }
    private SubmissionResponse toResponse(Submission submission){
        SubmissionResponse response = new SubmissionResponse(
                submission.getId(),
                submission.getResumeText(),
                submission.getJobDescription(),
                submission.getStatus(),
                submission.getCreatedAt()
        );
        return response;
    }

}
