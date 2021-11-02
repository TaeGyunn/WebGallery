package WebGallery.Gallery.repository;

import WebGallery.Gallery.entity.Author;
import WebGallery.Gallery.entity.Guest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Author findByGno(long gno);

    Page<Author> findByGuest(Guest guest, Pageable pageable);
}
