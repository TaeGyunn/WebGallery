package WebGallery.Gallery.repository;

import WebGallery.Gallery.entity.A_work;
import WebGallery.Gallery.entity.Album;
import WebGallery.Gallery.entity.Guest;
import WebGallery.Gallery.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface A_workRepository extends JpaRepository<A_work, Long> {

    boolean existsByWorkAndAlbum(Work work, Album album);

    List<A_work> findByAlbum(Album album);

}
