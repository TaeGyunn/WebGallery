package WebGallery.Gallery.repository;

import WebGallery.Gallery.entity.Guest;
import WebGallery.Gallery.entity.Likes;
import WebGallery.Gallery.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Likes, Long> {

    boolean existsByGuestAndWork(Guest guest, Work work);

    Likes findByGuestAndWork(Guest guest, Work work);

}
