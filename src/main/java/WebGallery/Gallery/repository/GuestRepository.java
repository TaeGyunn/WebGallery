package WebGallery.Gallery.repository;

import WebGallery.Gallery.entity.Guest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

        // 중복확인
        boolean existsByEmail(String email);
        boolean existsById(Long id);
        boolean existsByNick(String nick);

        Guest findById(String id);
        Guest findByNick(String nick);
        Guest findByGno(Long gno);




}
