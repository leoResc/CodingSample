package com.leo.project.decide;

import com.leo.project.communication.Repository;
import com.leo.project.io.SystemOutput;
import com.leo.project.io.UserAnswer;
import com.leo.project.objects.Question;
import com.leo.project.util.OutputMessages;
import kotlin.Pair;
import org.jetbrains.annotations.TestOnly;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main logic class to have the console conversation
 */
public class Discussion {

    private final SystemOutput systemOutput;
    private final List<Question> questions; //Not a set, because we want to keep the order

    public Discussion() {
        this(new SystemOutput(), new Repository().getAllQuestions()); //In a SpringBoot app Repository would be autowired
    }

    @TestOnly
    Discussion(SystemOutput output, List<Question> questions) {
        this.systemOutput = output;
        this.questions = questions;
    }

    /**
     * Starts a process which goes as long as the user doesn't want to exit. <br>
     * The only valid way the exit this function and shut down, is by typing {@link OutputMessages#BYBY_MESSAGE}
     */
    public void initiateUntilOver() {
        if (questions.isEmpty()) {
            systemOutput.write("No questions available. Return immediately!");
            return;
        }

        systemOutput.write("This application will ask you some questions...");

        Iterator<Question> questionIterator = questions.iterator();
        discussInDialog(questionIterator);
        writePostScripture(questionIterator);
    }

    private void discussInDialog(Iterator<Question> questionIterator) {
        List<Pair<Question, UserAnswer>> discussionOutcomes = new ArrayList<>();
        UserAnswer userAnswer;

        do {
            Question question = questionIterator.next();
            userAnswer = new DiscussionTopic(question).reachAgreementAndReturnAnswer();

            discussionOutcomes.add(new Pair<>(question, userAnswer));
        }
        while (userAnswer.canContinue() && questionIterator.hasNext());

        writeOutcome(discussionOutcomes);
    }

    private void writePostScripture(Iterator<Question> questionIterator) {
        if (questionIterator.hasNext()) {
            systemOutput.write("I have some questions left, but I won't hold you down..");
        } else {
            systemOutput.write("No questions left. The purpose of the application was reached!");
        }
    }

    private void writeOutcome(List<Pair<Question, UserAnswer>> discussionOutcomes) {
        String outcomeAsString = discussionOutcomes.stream()
                .map(pair -> pair.getFirst().getMessage() + " -> " + pair.getSecond().userFriendlyValue())
                .collect(Collectors.joining(", ", "[", "]"));

        systemOutput.write("The (saved) outcome of our conversation:\n" + outcomeAsString);
    }
}