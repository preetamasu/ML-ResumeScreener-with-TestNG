package com.example.resume.ml;

public record MlPredictionResponse(Double interviewProbability,Double matchScore,String modelVersion,String explanation) {
}
