package WebGallery.Gallery.repository;

import WebGallery.Gallery.entity.Album;
import WebGallery.Gallery.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    @Query("select count(a.ano) > 0 " +
            "from Album a " +
            "where a.name = :name and a.guest = :guest ")
    boolean exists(@Param(value="name") String name, @Param(value="guest") Guest guest);

    Album findByAno(Long ano);

    @Query("select a from Album a join fetch a.a_works")
    List<Album> findByGuest(Guest guest);


}
