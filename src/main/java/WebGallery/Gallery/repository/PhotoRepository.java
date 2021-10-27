package WebGallery.Gallery.repository;

import WebGallery.Gallery.entity.Author;
import WebGallery.Gallery.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    Photo findByPno(long pno);


}
