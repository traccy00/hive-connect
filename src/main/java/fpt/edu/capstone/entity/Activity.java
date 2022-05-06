package fpt.edu.capstone.entity;
//Vote, upvote, downvote, comment.... xử lý các thao tác liên quan đến bài viết của candidate , company đối với 1 post,
public class Activity {
    private long id;
    private long postId;
    private long candidateId;
    private long companyId;
    private String action;
    // còn trường hợp candidate follow company, company follow candidate, candidatef follow candidate ???
}
