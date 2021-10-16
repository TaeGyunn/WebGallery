package WebGallery.Gallery.repository;

import WebGallery.Gallery.entity.Guest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

        boolean existsByEmail(String email);

}
