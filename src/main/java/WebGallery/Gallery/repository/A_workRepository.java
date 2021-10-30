package WebGallery.Gallery.repository;

import WebGallery.Gallery.entity.A_work;
import WebGallery.Gallery.entity.Album;
import WebGallery.Gallery.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;

public interface A_workRepository extends JpaRepository<A_work, Long> {

    boolean existsByWorkAndAlbum(Work work, Album album);

}
