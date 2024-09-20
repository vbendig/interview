use maverick_db;
CREATE TABLE IF NOT EXISTS movies(
    id INT AUTO_INCREMENT,
    imdbID    VARCHAR(50),
    title     VARCHAR(100),
    year      VARCHAR(20),
    rated     VARCHAR(100),
    released   VARCHAR(100),
    runtime    VARCHAR(100),
    genre      VARCHAR(100),
    director   VARCHAR(100),
    actors     VARCHAR(100),
    plot       VARCHAR(100),
    language   VARCHAR(100),
    country    VARCHAR(100),
    poster     VARCHAR(100),
    imdbRating VARCHAR(100),
    owner     VARCHAR(100)
    PRIMARY KEY(id))
ENGINE = InnoDB;
