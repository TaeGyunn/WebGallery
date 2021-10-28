package WebGallery.Gallery.repository;

import WebGallery.Gallery.entity.Work;
import WebGallery.Gallery.entity.Work_tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkRepository extends JpaRepository<Work, Long> {

    Work findByWno(long wno);

}