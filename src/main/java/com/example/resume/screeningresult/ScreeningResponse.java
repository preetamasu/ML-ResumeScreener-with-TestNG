package com.example.resume.screeningresult;

import java.time.LocalDateTime;

public record ScreeningResponse(Long id, Long submissionId, Double interviewProbabilty, String matchScore, Double modelVersion, String explanation,
                                LocalDateTime createdAt) {
}
