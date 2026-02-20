package com.fitness.aiService.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.aiService.models.Activity;
import com.fitness.aiService.models.Recommendation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAiService {

    private final GeminiService geminiService;

    private final ObjectMapper objectMapper;

    public Recommendation generateRecommendation(Activity activity) {
        String prompt = createPromptForActivity(activity);

        String aiResponse = geminiService.getRecommendation(prompt);

        return processAiResponse(activity, aiResponse);
    }

    private Recommendation processAiResponse(Activity activity, String aiResponse) {

        try {

            JsonNode rootNode = objectMapper.readTree(aiResponse);

            JsonNode candidates = rootNode.path("candidates");

            if (!candidates.isArray() || candidates.isEmpty()) {
                log.error("No candidates found in AI response");
                return null;
            }

            JsonNode content = candidates.get(0).path("content");
            JsonNode parts = content.path("parts");

            if (!parts.isArray() || parts.isEmpty()) {
                log.error("No parts found in AI response");
                return null;
            }

            String rawText = parts.get(0).path("text").asText();

            // Clean markdown safely
            String cleanedJson = rawText.replaceAll("```json", "").replaceAll("```", "").trim();

            log.info("CLEANED AI RESPONSE: {}", cleanedJson);

            JsonNode analysisJson = objectMapper.readTree(cleanedJson);
            JsonNode analysisNode = analysisJson.path("analysis");
            StringBuffer fullAnalysis = new StringBuffer();
            addAnalysisSection(fullAnalysis, analysisNode, "overall", "Overall:");
            addAnalysisSection(fullAnalysis, analysisNode, "pace", "Pace:");
            addAnalysisSection(fullAnalysis, analysisNode, "heartRate", "Heart Rate:");
            addAnalysisSection(fullAnalysis, analysisNode, "caloriesBurned", "Calories Burned:");


            List<String> improvements = extractImprovements(analysisJson.path("improvements"));
            List<String> suggestions = extractSuggestions(analysisJson.path("suggestions"));
            List<String> safety = extractSafety(analysisJson.path("safety"));
            return Recommendation.builder().activityId(activity.getId())
                    .userId(activity.getUserId())
                    .type(activity.getType())
                    .improvements(improvements)
                    .recommendation(fullAnalysis.toString().trim())
                    .suggestions(suggestions)
                    .safety(safety)
                    .createdAt(LocalDateTime.now())
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return createDefaultRecommendation(activity);
        }
    }

    private Recommendation createDefaultRecommendation(Activity activity) {
        return Recommendation.builder().activityId(activity.getId())
                .userId(activity.getUserId())
                .type(activity.getType())
                .improvements(Collections.singletonList("Continue with your current routine"))
                .recommendation("Unable to generate detailed analysis")
                .suggestions(Collections.singletonList("Consider consulting a fitness consultant"))
                .safety(Arrays.asList(
                        "Always warm up before exercise",
                        "Stay hydrated",
                        "Listen to your body"
                ))
                .createdAt(LocalDateTime.now())
                .build();
    }

    private List<String> extractSafety(JsonNode safety) {

        List<String> safetys = new ArrayList<>();

        if (safety.isArray()) {
            safety.forEach(safe -> safetys.add(safe.asText()));
        }
        return safetys.isEmpty() ? Collections.singletonList("Follow general safety guidelines") : safetys;
    }


    private List<String> extractSuggestions(JsonNode suggestionsNode) {
        List<String> suggestions = new ArrayList<>();

        if (suggestionsNode.isArray()) {
            suggestionsNode.forEach(suggestion -> {
                String workout = suggestion.path("workout").asText();
                String description = suggestion.path("description").asText();
                suggestions.add(String.format("%s: %s", workout, description));
            });
        }
        return suggestions.isEmpty() ? Collections.singletonList("No Specific suggestions provided") : suggestions;
    }

    private List<String> extractImprovements(JsonNode improvementsNode) {

        List<String> improvements = new ArrayList<>();
        if (improvementsNode.isArray()) {
            improvementsNode.forEach(improvement -> {
                String area = improvement.path("area").asText();
                String details = improvement.path("recommendation").asText();
                improvements.add(String.format("%s: %s", area, details));
            });
        }
        return improvements.isEmpty() ? Collections.singletonList("No Specific Improvements provided") : improvements;
    }

    private void addAnalysisSection(StringBuffer fullAnalysis, JsonNode analysisNode, String key, String prefix) {

        if (!analysisNode.path(key).isMissingNode()) {
            fullAnalysis.append(prefix).append(analysisNode.path(key).asText()).append("\n\n");
        }
    }


    private String createPromptForActivity(Activity activity) {

        return String.format("""
                 Analyze this fitness activity and provide detailed recommendations in the following Exact JSON format:
                 {
                 "analysis":{
                 "overall":"Overall analysis here",
                 "pace":"pace analysis here",
                 "heartRate":"Heart rate analysis here"
                 "caloriesBurned":"Calories analysis here"
                 },
                
                 "improvements":[
                 {
                  "area":"Area name",
                  "recommendation":"Detailed recommendation"
                 }
                 ],
                 "suggestions":[
                 {
                 "workout":"Workout name",
                 "description":"Detailed workout description"
                 }
                 ],
                 "safety":[
                 "Safety point 1",
                 "Safety point  2"
                 ]
                 }
                
                
                 Analyze this activity:
                 Activity Type: %s
                 Duration: %d minutes
                 Calories Burned: %d
                 Additional; Metrics: %s
                
                
                 Provide detailed analysis focusing on performance, improvements, next workout suggestions, and safety guidelines 
                Ensure the response follows the EXACT JSON format shown above.
                """, activity.getType(), activity.getDuration(), activity.getCaloriesBurned(), activity.getAdditionalMatrix());


    }
}
