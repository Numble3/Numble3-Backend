package com.numble.team3.comment.infra;

import com.numble.team3.comment.domain.Comment;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCommentRepository extends JpaRepository<Comment, Long> {
  @Query("SELECT c FROM Comment c WHERE c.account.id = :accountId and c.id = :commentId")
  Optional<Comment> findCommentByAccountIdWithId(
      @Param("accountId") Long accountId, @Param("commentId") Long commentId);

  @Query("SELECT c FROM Comment c WHERE c.deleteYn = false and c.video.id = :videoId")
  Slice<Comment> findAllByVideoId(@Param("videoId") Long videoId, Pageable pageable);
}
