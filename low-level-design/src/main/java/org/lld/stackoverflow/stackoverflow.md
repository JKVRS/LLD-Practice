## Requirements Stackoverflow:
1. Users can post questions, answer questions, and comment on questions and answers.
2. Users can vote on questions and answers.
3. Questions should have tags associated with them.
4. Users can search for questions based on keywords,or tags, or user profiles.
5. The system should assign reputation score to users based on their activity and the quality of their contributions.
6. The system should handle concurrent access and ensure data consistency.

### Entities :

1. User
2. Question
3. Answer
4. Comment
5.   Tag
6. Activity
7.  Vote

#### Actors :  User, System

### Attributes :

### User Attributes
 
   * int id
   * String name
   * String email
   * int reputationScore
### Methods
* postQuestion(Question question)
* postAnswer(Answer answer)
* postComment(Comment comment)
* vote(Vote vote)

### SearchEngine
* List<Question> search(String queryType, List<Tag> tag, User userProfile)

### Question Attributes
* String id
* String title
* String content
* User user
* List<Tag> tags
* List<Answers> answer
* List<Comment> questionsComments
* List<Vote> votes
* LocalDateTime creationDate

### Answer Attributes
* String id
* String content
* User user
* Question question
* List<Comment> answerComments
* List<Vote> votes
* LocalDateTime creationDate

### Comment Attributes
* int id
* String content
* User user
* Question question
* Answer answer
* LocalDateTime creationDate;

### Tag Attributes
* int id
* String tagName
* String description

### Vote Attributes
* int id
* VoteType voteType(enum)
* User user
* Question questions
* Answer answer


### Activity Attributes
* int id
* User user
* ActivityType activityType
* LocalDateTime creationAt


###  Vote
* UPVOTE 
* DOWNVOTE

## interface
### interface votable - 
     * addVote(Vote vote)
     # Question, Answer
### interface commentable
     * addCommet(Comment comment)
     # Question, Answer
### interface SearchStrategy - 
    * List<Question> search(String keyword, List<Tag> tag, User user)

### Mapping 

* User 1...* Question (1:m)
* User 1...* Answer (1:m)
* User 1...* Comment (1:m)
* User 1...* Vote (1:m)
* Question  *...* Tag (m:m)
* Question  1...* Answer (1:m)
* Question 1...* Comment (1:m)
* Question 1...* vote (1:m)
* Answer 1...* Comment (1:m)
* Answer 1...* vote (1:m)

### Services

* PostService (factory pattern)
* QuestionRepo (Repo pattern)
* SearchEngine (strategy pattern) :  KeywordSearchStrategy  TagSearchStrategy --> SearchStrategy
* 




