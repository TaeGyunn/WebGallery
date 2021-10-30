package WebGallery.Gallery.repository;

import WebGallery.Gallery.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    boolean existsByName(String name);

    Album findByAno(Long ano);

}
