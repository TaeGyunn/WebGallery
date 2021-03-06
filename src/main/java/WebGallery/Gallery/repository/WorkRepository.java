package WebGallery.Gallery.repository;

import WebGallery.Gallery.entity.Author;
import WebGallery.Gallery.entity.Work;
import WebGallery.Gallery.entity.Work_tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkRepository extends JpaRepository<Work, Long> {

    Work findByWno(long wno);

    Page<Work> findAll(Pageable pageable);

    @Query(value = "select w from Work w join fetch w.author")
    List<Work> findAllWithAuthor();

    @Query(value = "select w from Work w join fetch w.author where w.thema = ?1")
    List<Work> findByThemaWithAuthor(String thema);

    List<Work> findByAuthor(Author author);



}
