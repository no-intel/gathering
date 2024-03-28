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
        @Index(name = "IX_member", columnList = "member_id")
})
public class Gathering extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gathering_id")
    private Long id;

    @Column(length = 100, nullable = false)
    private String subject;

    @Column(length = 100, nullable = false)
    private String description;

    @Column(nullable = false)
    private int currentMembers;

    @Column(nullable = false)
    private int maxMembers;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    private Member member;

    public Gathering(String subject, String description, int maxMembers, Member member) {
        this.subject = subject;
        this.description = description;
        this.maxMembers = maxMembers;
        this.member = member;
        this.currentMembers = 1;
    }
}
