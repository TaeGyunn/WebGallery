package WebGallery.Gallery.repository;


import WebGallery.Gallery.entity.Admin;
import WebGallery.Gallery.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {


    Admin findByNick(String nick);
    Admin findById(String id);


}
