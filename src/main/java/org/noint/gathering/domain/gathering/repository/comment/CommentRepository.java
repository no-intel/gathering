package org.noint.gathering.domain.gathering.repository.comment;

import org.noint.gathering.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
