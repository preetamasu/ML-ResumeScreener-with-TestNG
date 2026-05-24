package com.example.resume.submission;

import java.time.LocalDateTime;

public record SubmissionResponse(Long id, String resumeText, String jobDescription, SubmissionStatus submissionStatus,
                                 LocalDateTime createdAt) {
}
