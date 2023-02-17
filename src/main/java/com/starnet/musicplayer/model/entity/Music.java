package com.starnet.musicplayer.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Planeter
 * @description: TODO
 * @date 2023/2/16 10:51
 * @status dev
 */
@Data
@Entity
public class Music implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String creator;
    private String length;
    private String cover;
    private String type;
    private String file;
    private Date createdAt;
}
