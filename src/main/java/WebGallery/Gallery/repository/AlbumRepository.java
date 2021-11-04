package WebGallery.Gallery.repository;

import WebGallery.Gallery.entity.Album;
import WebGallery.Gallery.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    boolean existsByName(String name);

    Album findByAno(Long ano);

    @Query("select a from Album a join fetch a.a_works")
    List<Album> findByGuest(Guest guest);


}
