package WebGallery.Gallery.service;

import WebGallery.Gallery.dto.AddWorkToAlbumDTO;
import WebGallery.Gallery.dto.CreateAlbumDTO;
import WebGallery.Gallery.entity.A_work;
import WebGallery.Gallery.entity.Album;
import WebGallery.Gallery.entity.Work;
import WebGallery.Gallery.repository.A_workRepository;
import WebGallery.Gallery.repository.AlbumRepository;
import WebGallery.Gallery.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AlbumService {

    private AlbumRepository albumRepository;
    private A_workRepository a_workRepository;
    private WorkRepository workRepository;

    public Integer createAlbum(CreateAlbumDTO createAlbum){

        int check = 0;

        if(!albumRepository.existsByName(createAlbum.getName())){
            Album album = new Album(createAlbum.getName());
            albumRepository.save(album);
            check = 1;
            return check;
        }else{
            log.info("Album Name Exist");
        }
        return check;
    }

    public Integer addWorkToAlbum(AddWorkToAlbumDTO addWorkToAlbumDTO){

        int check = 0;
        Album album = albumRepository.findByAno(addWorkToAlbumDTO.getAno());
        for(int i=0; i<addWorkToAlbumDTO.getWnos().size(); i++) {
            Work work = workRepository.findByWno(addWorkToAlbumDTO.getWnos().get(i));
            if(!a_workRepository.existsByWorkAndAlbum(work,album)){
                A_work a_work = new A_work(album, work);
                a_workRepository.save(a_work);
                check = 1;
            }
        }
        return check;
    }



}
