package com.qualitydefectdetector.criteriaChecker;

import com.qualitydefectdetector.model.CriteriaCheckResult;
import com.qualitydefectdetector.nlpprocessor.ZemberekProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.qualitydefectdetector.model.CriteriaCheckResult.CriteriaCheckResultBuilder.aCriteriaCheckResultBuilder;

import java.util.regex.Pattern;


@Service
public class MinimalCriteriaChecker {
    private final ZemberekProcessor zemberekProcessor;

    @Autowired
    public MinimalCriteriaChecker(ZemberekProcessor zemberekProcessor){
        this.zemberekProcessor = zemberekProcessor;
    }

    public enum ExtraNote {

        EXTRA_NOTE_WITH_PUNCTUATION(".*[-(].*[-)].*");

        private String formatRegex;

        ExtraNote(String formatRegex) {
            this.formatRegex = formatRegex;
        }

        public String getFormatRegex() {
            return formatRegex;
        }
    }

    public CriteriaCheckResult checkIfThereExistExtraNote(String sentence){
        if(Pattern.matches(ExtraNote.EXTRA_NOTE_WITH_PUNCTUATION.getFormatRegex(),sentence)){
            return aCriteriaCheckResultBuilder()
                    .satisfiesThisCriteria(false)
                    .errorMessage("A user story should not contain any side notes")
                    .build();
        }
        return aCriteriaCheckResultBuilder()
                .satisfiesThisCriteria(true)
                .errorMessage("")
                .build();
    }

    public CriteriaCheckResult checkIfItIsOneSentence(String sentence){
        if(!zemberekProcessor.isOneSentence(sentence)){
            return aCriteriaCheckResultBuilder()
                    .satisfiesThisCriteria(false)
                    .errorMessage("A user story should not contain more than one sentence")
                    .build();
        }
        return aCriteriaCheckResultBuilder()
                .satisfiesThisCriteria(true)
                .errorMessage("")
                .build();
    }
}
