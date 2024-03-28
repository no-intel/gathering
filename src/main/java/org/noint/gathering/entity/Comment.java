package org.noint.gathering.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(name = "IX_gathering_member", columnList = "gathering_id, member_id")
})
public class Comment extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long Id;

    @Column(length = 255, nullable = false)
    private String body;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "gathering_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    private Gathering gathering;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    private Member member;

    public Comment(String body, Gathering gathering, Member member) {
        this.body = body;
        this.gathering = gathering;
        this.member = member;
    }
}
