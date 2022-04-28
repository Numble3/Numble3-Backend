package com.numble.team3.video.domain;

import com.numble.team3.account.domain.Account;
import com.numble.team3.video.domain.enums.VideoCategory;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert // 객체 생성 시 null값은 쿼리에 넣지 않기 위함
@EntityListeners(AuditingEntityListener.class)
public class Video {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long accountId;
  private Long videoDuration;
  private String title;
  private String content;

  @ColumnDefault(value = "0")
  @Column(name = "view_count")
  private Long view;

  @ColumnDefault(value = "0")
  @Column(name = "like_count")
  private Long like;

  @CreatedDate private LocalDateTime createAt;
  private String videoUrl;
  private String thumbnailUrl;

  @Column(columnDefinition = "tinyint(1)")
  private boolean deleteYn;

  @ManyToOne private Account account;

  @Enumerated(EnumType.STRING)
  private VideoCategory category;

  @Builder
  public Video(
      Long accountId,
      Long videoDuration,
      String title,
      String content,
      String videoUrl,
      String thumbnailUrl,
      Account account,
      VideoCategory category) {
    this.accountId = accountId;
    this.videoDuration = videoDuration;
    this.title = title;
    this.content = content;
    this.videoUrl = videoUrl;
    this.thumbnailUrl = thumbnailUrl;
    this.account = account;
    this.deleteYn = false;
    this.category = category;
  }

  public void deleteVideo() {
    this.deleteYn = true;
  }
}
