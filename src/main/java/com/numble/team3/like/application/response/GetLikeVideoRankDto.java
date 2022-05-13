package com.numble.team3.like.application.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.numble.team3.video.domain.Video;
import com.numble.team3.video.domain.enums.VideoCategory;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetLikeVideoRankDto {
  private Long videoId;
  private String thumbnailPath;
  private String title;
  private String nickname;
  private VideoCategory videoCategory;
  private long view;
  private long like;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
  @JsonSerialize(using = LocalDateSerializer.class)
  private LocalDate createdAt;

  public static GetLikeVideoRankDto fromEntity(Video video) {
    return GetLikeVideoRankDto.builder()
      .videoId(video.getId())
      .thumbnailPath(video.getThumbnailUrl())
      .title(video.getTitle())
      .nickname(video.getAccount().getNickname())
      .view(video.getView())
      .like(video.getLike())
      .createdAt(video.getCreatedAt().toLocalDate())
      .videoCategory(video.getCategory())
      .build();
  }
}
