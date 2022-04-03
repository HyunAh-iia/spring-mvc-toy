CREATE TABLE `post`
(
    `id`         INT          NOT NULL AUTO_INCREMENT,
    `title`      VARCHAR(100) NOT NULL,
    `content`    TEXT         NOT NULL,
    `deleted`    TINYINT(1)   NOT NULL DEFAULT 0,
    `created_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  charset = utf8;

CREATE TABLE `post_files`
(
    id  INT NOT NULL,
    url VARCHAR(255),
    FOREIGN KEY (id) REFERENCES post (id)
) ENGINE = InnoDB
  charset = utf8;

CREATE TABLE `comment`
(
    `id`         INT          NOT NULL AUTO_INCREMENT,
    `content`    VARCHAR(255) NOT NULL,
    `post_id`    INT          NOT NULL,
    `created_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (post_id) REFERENCES post (id)
) ENGINE = InnoDB
  charset = utf8;