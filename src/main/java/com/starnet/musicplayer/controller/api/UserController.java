package com.starnet.musicplayer.controller.api;



import com.starnet.musicplayer.common.enums.ResultCode;
import com.starnet.musicplayer.model.entity.UserEntity;
import com.starnet.musicplayer.model.param.LoginParam;
import com.starnet.musicplayer.model.param.RegisterParam;
import com.starnet.musicplayer.model.vo.ResultVO;
import com.starnet.musicplayer.model.vo.UserVO;
import com.starnet.musicplayer.service.UserService;
import com.starnet.musicplayer.utils.JwtUtils;
import com.starnet.musicplayer.utils.QiniuUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


@Slf4j
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/register/verify")
    public ResultVO<String> sendEmail(@RequestParam String email) {
        userService.verifyEmail(email);
        log.info("验证码已发送，email: "+email);
        return new ResultVO<>("验证码发送成功");
    }

    @PostMapping("/register")
    public ResultVO<String> register(@RequestBody @Validated RegisterParam param) {
        userService.register(param);
        log.info("账号注册，email: "+param.getUsername());
        return new ResultVO<>("注册成功");
    }

    @PostMapping("/login")
    public UserEntity login(@RequestBody @Validated LoginParam param, HttpServletResponse response) {
        String username = param.getUsername();
        String password = param.getPassword();
        Subject subject = SecurityUtils.getSubject();
        UserEntity user = null;
        // 登录
        subject.login(new UsernamePasswordToken(username, password));
        user = (UserEntity) subject.getPrincipal();
        // 签发Jwt,存入Redis
        String jwt = JwtUtils.sign(username, 100000);
        redisTemplate.opsForValue().set("Jwt-" + param.getUsername(), jwt, 100000, TimeUnit.SECONDS);
        // 设置token
        response.setHeader("Set-Token", jwt);
        log.info("用户登录，username: "+username);
        return user;
    }
    /**此后方法都需要登录后的token*/
    @PutMapping("/user/update")
    public UserEntity update(@RequestBody UserVO userVO) {
        return userService.update(userVO);
    }

    @GetMapping("/user/detail")
    public UserEntity detail() {
        return (UserEntity) SecurityUtils.getSubject().getPrincipal();
    }

    @PostMapping(value = "/icon/upload")
    public ResultVO<UserEntity> uploadIcon(@RequestParam("image") MultipartFile uploadFile) {
        String url = null;
        try {
            //重命名
            String newFilename = QiniuUtils.renamePic(Objects.requireNonNull(uploadFile.getOriginalFilename()));
            url = QiniuUtils.InputStreamUpload((FileInputStream) uploadFile.getInputStream(), newFilename);
            if (url.contains("error")) {
                return new ResultVO<>(ResultCode.FAILED);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("上传头像成功");
        UserVO user = new UserVO();
        user.setIcon(url);
        return new ResultVO<>(userService.update(user));
    }
}