package WebGallery.Gallery.repository;

import WebGallery.Gallery.entity.Author;
import WebGallery.Gallery.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

    boolean existsByName(String name);

    Tag findByName(String name);


}
