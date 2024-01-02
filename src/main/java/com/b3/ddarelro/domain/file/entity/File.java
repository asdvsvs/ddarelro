package com.b3.ddarelro.domain.file.entity;


import com.b3.ddarelro.domain.card.entity.Card;
import com.b3.ddarelro.domain.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_file")
public class File extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originFilename;  // 작성자가 업로드할 원본 파일명
    @Column(nullable = false)
    private String storeFilename;   // 서버에 저장될 파일명
    @Column(nullable = false)
    private String filePath; //  파일경로 (이 경로로 파일이 열림)

    private Boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    private Card card;


    @Builder
    public File(String originFilename, String storeFilename, String filePath, Card card) {
        this.originFilename = originFilename;
        this.storeFilename = storeFilename;
        this.filePath = filePath;
        this.card = card;
        this.deleted = false;
    }

    public void updateState(Boolean bool) {
        this.deleted = bool;
    }


}
