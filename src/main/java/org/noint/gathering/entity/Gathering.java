package org.noint.gathering.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gathering extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gathering_id")
    private Long id;

    private String subject;

    private String description;

    private int currentMembers;

    private int maxMembers;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Gathering(String subject, String description, int maxMembers, Member member) {
        this.subject = subject;
        this.description = description;
        this.maxMembers = maxMembers;
        this.member = member;
        this.currentMembers = 1;
    }
}
