package WebGallery.Gallery.repository;

import WebGallery.Gallery.entity.Author;
import WebGallery.Gallery.entity.Guest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Author findByGno(long gno);

    Author findById(String id);

    Page<Author> findByGno(Long gno, Pageable pageable);

    @Query(value = "select distinct a from Author a join fetch a.works")
    List<Author> findAllWithWork();
}
