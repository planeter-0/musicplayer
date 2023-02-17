package com.starnet.musicplayer.service;

import com.starnet.musicplayer.model.entity.Music;

import java.util.List;

public interface MusicService {
    List<Music> getMusicList();
    boolean saveMusicList(List<Music> list);
    boolean deleteMusicByIdList(List<Long> list);
}
