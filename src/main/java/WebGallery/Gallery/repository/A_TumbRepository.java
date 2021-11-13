package WebGallery.Gallery.repository;

import WebGallery.Gallery.entity.A_thumb;
import WebGallery.Gallery.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface A_TumbRepository extends JpaRepository<A_thumb, Long> {

    A_thumb findByStodname(String stodname);
    A_thumb findByAuthor(Author author);


}
