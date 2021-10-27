package WebGallery.Gallery.repository;

import WebGallery.Gallery.entity.Author;
import WebGallery.Gallery.entity.Work;
import WebGallery.Gallery.entity.Work_tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Work_tagRepository extends JpaRepository<Work_tag, Long> {

    List<Work_tag> findByWork(Work work);

}
