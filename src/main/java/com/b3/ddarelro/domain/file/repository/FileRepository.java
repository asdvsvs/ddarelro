package com.b3.ddarelro.domain.file.repository;

import com.b3.ddarelro.domain.card.entity.Card;
import com.b3.ddarelro.domain.file.entity.File;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FileRepository extends JpaRepository<File, Long> {


    List<File> findAllByCardAndDeletedFalse(Card card);


    @Modifying
    @Query(value = "update UploadFile f set f.deleted = true where f.card.id in (:fileIds)")
    void SoftDelete(List<Long> fileIds);

    @Modifying
    @Query(value = "update UploadFile f set f.deleted = false where f.card.id in (:fileIds)")
    void restoreAll(List<Long> fileIds);
}
