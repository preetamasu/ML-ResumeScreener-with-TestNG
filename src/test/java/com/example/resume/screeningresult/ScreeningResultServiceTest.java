package com.example.resume.screeningresult;

import com.beust.ah.A;
import com.example.resume.exception.ResourceNotFoundException;
import com.example.resume.ml.MlPredictionResponse;
import com.example.resume.submission.Submission;
import com.example.resume.submission.SubmissionStatus;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.Test;
import org.mockito.Mock;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;


public class ScreeningResultServiceTest {

    @Mock
    private ScreeningResultRepository screeningResultRepository;

    private ScreeningResultService screeningResultService;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        screeningResultService = new ScreeningResultService(screeningResultRepository);

    }

    @Test
    public void createScreeningResult() {

    }

    @Test
    public void getScreeningResultBySubmissionIdIfItExists() {


        Submission submission = new Submission();
        submission.setId(1L);

        ScreeningResult screeningResult = new ScreeningResult();
        screeningResult.setId(1L);
        screeningResult.setSubmission(submission);
        screeningResult.setInterviewProbability(0.85);
        screeningResult.setMatchScore(85.0);
        screeningResult.setModelVersion("test-model-v1");
        screeningResult.setExplanation("Test explanation");
        screeningResult.setCreatedAt(LocalDateTime.now());

        when(screeningResultRepository.findBySubmissionId(1L)).thenReturn(Optional.of(screeningResult));

        ScreeningResponse result = screeningResultService.getScreeningResultBySubmissionId(1L);

        Assert.assertEquals(result.id(),screeningResult.getId());
        Assert.assertEquals(result.matchScore(),screeningResult.getMatchScore());
        Assert.assertEquals(result.explanation(),screeningResult.getExplanation());
        verify(screeningResultRepository,times(1)).findBySubmissionId(1L);

    }

    @Test
    public void getScreeningResultBySubmissionIdIfItNotExists(){
        when(screeningResultRepository.findBySubmissionId(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = Assert.expectThrows(
                ResourceNotFoundException.class,
                ()-> screeningResultService.getScreeningResultBySubmissionId(1L)
        );

        Assert.assertEquals(exception.getMessage(),"Couldn't find an result with that submission");

        verify(screeningResultRepository,times(1)).findBySubmissionId(1L);

    }

    @Test
    public void createScreeningResultWhenSubmissionIdDoesntExits(){
        Submission submission = new Submission();
        submission.setId(1L);
        submission.setResumeText("Java Spring Boot resume");
        submission.setJobDescription("Backend developer job");
        submission.setStatus(SubmissionStatus.SCORED);
        submission.setCreatedAt(LocalDateTime.now());

        MlPredictionResponse predictionResponse = new MlPredictionResponse(
                0.85,
                85.0,
                "test-model-v1",
                "Test prediction"
        );

        when(screeningResultRepository.existsBySubmissionId(1L)).thenReturn(false);
        when(screeningResultRepository.save(any(ScreeningResult.class))).thenAnswer(invocation->invocation.getArgument(0));

        ScreeningResponse response = screeningResultService.createScreeningResult(submission,predictionResponse);

        Assert.assertEquals(response.submissionId(), submission.getId());
        Assert.assertEquals(response.matchScore(),predictionResponse.matchScore());
        verify(screeningResultRepository,times(1)).existsBySubmissionId(1L);
        verify(screeningResultRepository,times(1)).save(any(ScreeningResult.class));


    }


}