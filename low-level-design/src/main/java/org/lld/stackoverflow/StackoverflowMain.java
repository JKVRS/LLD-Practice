package org.lld.stackoverflow;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class User{
    private String id;
    private String name;
    private String email;
    private int reputationScore;

    public User(String id, String name, String email, int reputationScore) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.reputationScore = reputationScore;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getReputationScore() {
        return reputationScore;
    }

}

class Tag{
    private int id;
    private String tagName;
    private String description;

    public Tag(int id, String tagName, String description) {
        this.id = id;
        this.tagName = tagName;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTagName() {
        return tagName;
    }

    public String getDescription() {
        return description;
    }
}

class Question{
    private String id;
    private String title;
    private String content;
    private User user;
    private List<Tag> tags;
    private List<Answer> answers;
    private List<Comment> questionsComments;
    private List<Vote> votes;
    private LocalDateTime creationDate;

    public Question(String id, String title,
                    String content,
                    User user, List<Tag> tags,
                    List<Answer> answers,
                    List<Comment> questionsComments,
                    LocalDateTime creationDate,
                    List<Vote> votes) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.tags = tags;
        this.answers = answers;
        this.questionsComments = questionsComments;
        this.creationDate = creationDate;
        this.votes = votes;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public User getUser() {
        return user;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public List<Comment> getQuestionsComments() {
        return questionsComments;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
}

class Answer{
     private String id;
     private String content;
    private User user;
    private Question question;
    private List<Comment> answerComments;
    private List<Vote> votes;
    private LocalDateTime creationDate;

    public Answer(String id, String content,
                  User user,
                  Question question,
                  List<Comment> answerComments,
                  List<Vote> votes,
                  LocalDateTime creationDate) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.question = question;
        this.answerComments = answerComments;
        this.votes = votes;
        this.creationDate = creationDate;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public User getUser() {
        return user;
    }

    public Question getQuestion() {
        return question;
    }

    public List<Comment> getAnswerComments() {
        return answerComments;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
}

class Comment {
   private String id;
   private String content;
   private User user;
   private Question question;
   private Answer answer;
   private LocalDateTime creationDate;

    public Comment(String id, String content,
                   User user,
                   Question question,
                   Answer answer,
                   LocalDateTime creationDate) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.question = question;
        this.answer = answer;
        this.creationDate = creationDate;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public User getUser() {
        return user;
    }

    public Question getQuestion() {
        return question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
}

class Vote{
    private String id;
    private VoteType voteType;
    private User user;
    private Question questions;
   private Answer answer;

    public Vote(String id,
                VoteType voteType,
                User user,
                Question questions,
                Answer answer) {
        this.id = id;
        this.voteType = voteType;
        this.user = user;
        this.questions = questions;
        this.answer = answer;
    }

    public VoteType getVoteType() {
        return voteType;
    }

    public User getUser() {
        return user;
    }
}

enum VoteType{
    UPVOTE, DOWNVOTE
}

class QuestionRepo{

   private Map<String,Question> questions;

   public QuestionRepo(){
       this.questions = new HashMap<>();
   }

   public void save(Question question){
       questions.put(question.getId(),question);
   }
   public Question findById(String id){
        synchronized (questions){
            return questions.get(id);
        }
   }

   public List<Question> findAll(){
       return new ArrayList<>(questions.values());
   }
}





public class StackoverflowMain {
    public static void main(String[] args) {

    }
}
