package org.lld.stackoverflow;


import java.time.LocalDateTime;
import java.util.*;


class User{
    private String id;
    private String name;
    private String email;
    private int reputationScore;

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.reputationScore = 0;
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

    public void incrementReputation(int score){
        reputationScore += score;
    }

}

// @FunctionalInterface we can make it becaue it has only one abstract method
interface  Commentable{
    void addComment(Comment comment);
}

//  But this we can not make it functional interface because it has more then one abstract method
interface Votable{

    void addVote(Vote vote);
    List<Vote> getVotes();
    User getUser();
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

    public String getTagName() {
        return tagName;
    }

}

class Question implements Commentable, Votable{
    private String id;
    private String title;
    private String content;
    private User user;
    private List<Tag> tags;
    private List<Answer> answers;
    private List<Comment> questionsComments;
    private List<Vote> votes;
    private LocalDateTime creationDate;

    public Question(String id,
                    String title,
                    String content,
                    User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.tags = new ArrayList<>();
        this.answers = new ArrayList<>();
        this.questionsComments = new ArrayList<>();
        this.creationDate = LocalDateTime.now();
        this.votes = new ArrayList<>();
    }


    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public User getUser() {
        return user;
    }

    public void setTag(Tag tag){
        tags.add(tag);
    }

    @Override
    public List<Vote> getVotes() {
        return votes;
    }


    @Override
    public void addComment(Comment comment) {
        questionsComments.add(comment);
    }

    public void addAnswer(Answer answer){
        answers.add(answer);
    }

    @Override
    public void addVote(Vote vote){
        votes.add(vote);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", user=" + user +
                ", tags=" + tags +
                ", answers=" + answers +
                ", questionsComments=" + questionsComments +
                ", votes=" + votes +
                ", creationDate=" + creationDate +
                '}';
    }
}

class Answer implements Commentable, Votable{
     private String id;
     private String content;
    private User user;
    private Question question;
    private List<Comment> answerComments;
    private List<Vote> votes;
    private LocalDateTime creationDate;

    public Answer(String id,
                  String content,
                  User user,
                  Question question) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.question = question;
        this.answerComments = new ArrayList<>();
        this.votes = new ArrayList<>();
        this.creationDate = LocalDateTime.now();
    }


    public String getContent() {
        return content;
    }

    @Override
    public User getUser() {
        return user;
    }

//    public Question getQuestion() {
//        return question;
//    }
//
//    public List<Comment> getAnswerComments() {
//        return answerComments;
//    }

    @Override
    public List<Vote> getVotes() {
        return votes;
    }

    @Override
    public void addComment(Comment comment) {
        answerComments.add(comment);
    }

    @Override
    public void addVote(Vote vote) {
        votes.add(vote);
    }
}

class Comment {
   private String id;
   private String content;
   private User user;
   private LocalDateTime creationDate;

    public Comment(String id,
                   String content,
                   User user) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.creationDate = LocalDateTime.now();
    }

    public String getContent() {
        return content;
    }

}

class Vote{
    private String id;
//    private VoteType voteType;
    private User user;
    private Votable target;

    public Vote(String id,
                User user,
                Votable target) {
        this.id = id;
//        this.voteType = voteType;
        this.user = user;
        this.target = target;
    }

//    public VoteType getVoteType() {
//        return voteType;
//    }

    public User getUser() {
        return user;
    }
    public Votable getTarget(){
        return target;
    }
}

enum VoteType{
    UPVOTE, DOWNVOTE
}

class SystemManager{

    private  List<Question> questions;

    public SystemManager() {
        this.questions = new ArrayList<>();
    }

   public Question postQuestion(String title, String content, User user){
        Question question = new Question(UUID.randomUUID().toString(), title,content,user);
        questions.add(question);
        return question;
    }

    public Answer postAnswer(User user, String content, Question question){
        Answer answer = new Answer(UUID.randomUUID().toString(), content, user,question);
       question.addAnswer(answer);
       return answer;

    }

    public Comment postComment(String content, User user, Commentable target){
        synchronized (target) {
            Comment comment = new Comment(UUID.randomUUID().toString(), content, user);
            target.addComment(comment);
            return comment;
        }
    }

    public void upvoteQuestion(User user , Votable target){
        for(Vote vote :target.getVotes()){
            if(vote.getUser().getId().equals(user.getId())){
                System.out.println("This question is already voted by user "+ user.getName());
                return;
            }
        }
        Vote vote = new Vote(UUID.randomUUID().toString(),user,target);
        target.addVote(vote);
        target.getUser().incrementReputation(10);
    }

    public void upvoteAnswer(User user , Votable target){
        for(Vote vote :target.getVotes()){
            if(vote.getUser().getId().equals(user.getId())){
                System.out.println("This Answer is already voted by user "+ user.getName());
                return;
            }
        }
        Vote vote = new Vote(UUID.randomUUID().toString(),user,target);
        target.addVote(vote);
        target.getUser().incrementReputation(10);
    }

    public void addTag(Question question,Tag tag){
        question.setTag(tag);
    }

    public List<Question> searchByKeyword(String query){
       return questions.stream().filter(question->question.getContent().contains(query)).toList();
    }

    List<Question> getAllQuestions(){
        return questions;
    }
}

public class StackoverflowMain {
    public static void main(String[] args) {
        SystemManager system = new SystemManager();
        User krishna = new User("krish1", "krishna", "abc@gmail.com");
        User manoj = new User("manoj1","Manoj", "xyz@gmail.com");

        Question question = system.postQuestion("what is java","can somebody help me in java programing", krishna);
        System.out.println("Question posted :"+ question.getTitle() +" Posted by : "+krishna.getName());

        Tag tag = new Tag(1,"Programing","Questions related to program");
        system.addTag(question,tag);
        System.out.println("Tag added to the question :" + question.getTitle() + " : " +tag.getTagName());

        Answer answer = system.postAnswer(manoj,"java is object oriented programing", question);
        System.out.println("Answer posted by :" + manoj.getName() +" : "+ answer.getContent());

        system.upvoteQuestion(manoj, question);
        System.out.println("Upvote by :"+ manoj.getName() +" Reputation of krishna: "+ krishna.getReputationScore());

        Comment comment = system.postComment(" Thanks for the question ", manoj, question);
        System.out.println("Commented added to question by : "+manoj.getName() +" : "+ comment.getContent());

        system.postComment("Thanks for your answer", krishna, answer);
        System.out.println("Comment added to answer by :"+ krishna.getName());

        system.upvoteAnswer(manoj, answer);
        System.out.println("Upvote by :"+ manoj.getName() +" Reputation of Manoj: "+ manoj.getReputationScore());

        System.out.println("Votes on question :"+question.getVotes().size());
        System.out.println("Votes on Answer :"+answer.getVotes().size());

        System.out.println("all questions :-> ");
        system.getAllQuestions().forEach(question1-> System.out.println(question1.getContent()));

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Keyword :-> ");
        String keyword = scanner.nextLine();
        System.out.println("Search by keyword : "+ system.searchByKeyword(keyword));
    }
}
