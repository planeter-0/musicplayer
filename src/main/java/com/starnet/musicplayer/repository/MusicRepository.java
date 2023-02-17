package com.starnet.musicplayer.repository;

import com.starnet.musicplayer.model.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Long> {
}
