package com.starnet.musicplayer.controller.api;

import com.starnet.musicplayer.common.enums.ResultCode;
import com.starnet.musicplayer.model.entity.Music;
import com.starnet.musicplayer.model.vo.ResultVO;
import com.starnet.musicplayer.service.MusicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Planeter
 * @description: TODO
 * @date 2023/2/15 8:36
 * @status dev
 */
@Slf4j
@RestController
public class MusicController {
    @Resource
    private MusicService musicService;

    @GetMapping("/music/getlist")
    public ResultVO<List<Music>> getMusicList() {
        log.info("获取音乐列表");
        return new ResultVO<>(musicService.getMusicList());
    }

    @PutMapping("/music/update")
    public ResultVO<String> updateMusicList(@RequestBody List<Music> list) {
        log.info("更新音乐列表");
        if (musicService.saveMusicList(list)) {
            return new ResultVO<>(ResultCode.SUCCESS);
        } else {
            return new ResultVO<>(ResultCode.FAILED);
        }
    }
}
