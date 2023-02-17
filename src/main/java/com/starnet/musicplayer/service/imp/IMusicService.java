package com.starnet.musicplayer.service.imp;

import com.starnet.musicplayer.model.entity.Music;
import com.starnet.musicplayer.repository.MusicRepository;
import com.starnet.musicplayer.service.MusicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Planeter
 * @description: TODO
 * @date 2023/2/15 14:19
 * @status dev
 */
@Slf4j
@Service
public class IMusicService implements MusicService {
    @Resource
    MusicRepository musicRepository;

    @Override
    public List<Music> getMusicList() {
        return musicRepository.findAll();
    }

    @Override
    public boolean saveMusicList(List<Music> list) {
        try {
            musicRepository.saveAll(list);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        log.info("保存音乐列表成功");
        return true;
    }

    @Override
    public boolean deleteMusicByIdList(List<Long> list) {
        try {
            musicRepository.deleteAllById(list);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        log.info("删除音乐列表成功");
        return true;
    }

}
