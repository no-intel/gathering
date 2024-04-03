package org.noint.gathering.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;
import org.noint.gathering.domain.gathering.exception.GatheringException;

import static jakarta.persistence.FetchType.LAZY;
import static org.noint.gathering.domain.gathering.enums.GatheringExceptionBody.CAPACITY_EXCEEDED;

@Slf4j
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

    @Version
    @ColumnDefault(value = "0")
    private int version;

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
        this.version = 0;
    }

    public void entryGathering() {
        if (this.currentMembers < this.maxMembers) {
            this.currentMembers++;
        }else {
            log.warn("참가 인원이 초과 되었습니다");
            throw new GatheringException(CAPACITY_EXCEEDED);
        }
    }
}
