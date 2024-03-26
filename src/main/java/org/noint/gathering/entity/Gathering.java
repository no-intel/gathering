package org.noint.gathering.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gathering extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "gathering_id")
    private Long id;

    private String subject;

    private String description;

    private int max;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Gathering(String subject, String description, int max, Member member) {
        this.subject = subject;
        this.description = description;
        this.max = max;
        this.member = member;
    }
}
