package org.noint.gathering.domain.gathering.repository.comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.noint.gathering.domain.gathering.dto.response.CommentsResDto;
import org.noint.gathering.domain.gathering.dto.response.QCommentsResDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.noint.gathering.entity.QComment.comment;
import static org.noint.gathering.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Slice<CommentsResDto> getComments(Long gatheringId, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        List<CommentsResDto> content = queryFactory
                .select(new QCommentsResDto(
                        member.Id,
                        member.name,
                        comment.body,
                        comment.createDate,
                        comment.lastModifiedDate
                ))
                .from(comment)
                .innerJoin(comment.member, member)
                .where(comment.gathering.id.eq(gatheringId))
                .orderBy(comment.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageSize + 1)
                .fetch();

        boolean hasNext = false;
        if (content.size() > pageSize) {
            hasNext = true;
        }
       return new SliceImpl<>(content, pageable, hasNext);
    }
}
