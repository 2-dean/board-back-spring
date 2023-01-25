
CREATE TABLE `board` (
                         `idx` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '게시글번호',
                         `title` varchar(100) NOT NULL COMMENT '제목',
                         `content` text NOT NULL COMMENT '내용',
                         `name` varchar(20) NOT NULL COMMENT '작성자',
                         `save_date` datetime DEFAULT current_timestamp() COMMENT '작성일자',
                         `modify_date` datetime DEFAULT current_timestamp() COMMENT '수정일자',
                         `file_idx` bigint(20) DEFAULT NULL COMMENT '파일번호',
                         PRIMARY KEY (`idx`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `file` (
                        `idx` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '파일번호',
                        `ori_file_name` varchar(100) NOT NULL COMMENT '첨부파일명',
                        `save_file_name` varchar(1000) NOT NULL COMMENT '시스템저장명',
                        `save_path` varchar(1000) NOT NULL COMMENT '저장경로',
                        `board_idx` bigint(20) DEFAULT NULL,
                        PRIMARY KEY (`idx`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

CREATE TABLE `comment` (
                           `idx` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '댓글번호',
                           `board_idx` bigint(20) NOT NULL COMMENT '게시글번호',
                           `name` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '작성자',
                           `content` varchar(500) NOT NULL COMMENT '댓글내용',
                           `save_date` datetime DEFAULT current_timestamp() COMMENT '작성시간',
                           PRIMARY KEY (`idx`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;


CREATE TABLE `user` (
                        `idx` bigint(20) NOT NULL AUTO_INCREMENT,
                        `id` varchar(100) NOT NULL,
                        `password` varchar(100) NOT NULL,
                        `name` varchar(50) DEFAULT NULL,
                        `state` varchar(50) DEFAULT NULL,
                        PRIMARY KEY (`idx`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;