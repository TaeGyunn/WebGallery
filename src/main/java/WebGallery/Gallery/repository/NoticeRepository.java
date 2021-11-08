package WebGallery.Gallery.repository;

import WebGallery.Gallery.entity.Admin;
import WebGallery.Gallery.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Notice findByAdmin(Admin admin);

    Page<Notice> findAll(Pageable pageable);
}
