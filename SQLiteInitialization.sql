DROP TABLE GamePlayer;
DROP TABLE Player;
DROP TABLE Game;
DROP TABLE Match;
DROP TABLE Team;
DROP TABLE League;

CREATE TABLE League (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    shortcut TEXT NOT NULL,
    level INTEGER NOT NULL DEFAULT 1,
    division INTEGER NOT NULL DEFAULT 1
);

CREATE TABLE Team (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    shortcut TEXT NOT NULL,
    league_id INTEGER NULL DEFAULT NULL,
    FOREIGN KEY (league_id) REFERENCES League(id)
);

CREATE TABLE Match (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    league_id INTEGER NULL DEFAULT NULL,
    season INTEGER NOT NULL,
    round INTEGER NOT NULL,
    type INTEGER NOT NULL,
    date DATETIME NOT NULL,
    result_home INTEGER NULL DEFAULT NULL,
    result_away INTEGER NULL DEFAULT NULL,
    home_team_id INTEGER NULL DEFAULT NULL,
    away_team_id INTEGER NULL DEFAULT NULL,
    FOREIGN KEY (league_id) REFERENCES League(id),
    FOREIGN KEY (home_team_id) REFERENCES Team(id),
    FOREIGN KEY (away_team_id) REFERENCES Team(id)
);

CREATE TABLE Game (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    number INTEGER NOT NULL,
    type INTEGER NOT NULL,
    home_score INTEGER NULL DEFAULT NULL,
    away_score INTEGER NULL DEFAULT NULL,
    match_id INTEGER NOT NULL,
    FOREIGN KEY (match_id) REFERENCES Match(id)
);

CREATE TABLE Player (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    first_name TEXT NULL DEFAULT NULL,
    second_name TEXT NULL DEFAULT NULL,
    nick_name TEXT NULL DEFAULT NULL,
    team_id TEXT NULL DEFAULT NULL,
    FOREIGN KEY (team_id) REFERENCES Team(id)
);

CREATE TABLE GamePlayer (
    game_id INTEGER NOT NULL,
    player_id INTEGER NOT NULL,
    PRIMARY KEY (game_id, player_id)
);

# example data
INSERT INTO League (name, shortcut, level, division) VALUES ("1. liga", "1L", 1, 1);
INSERT INTO League (name, shortcut, level, division) VALUES ("2. liga", "2L", 2, 1);
INSERT INTO League (name, shortcut, level, division) VALUES ("3. liga, divize A", "3LA", 3, 1);
INSERT INTO League (name, shortcut, level, division) VALUES ("3. liga, divize A", "3LB", 3, 2);
INSERT INTO League (name, shortcut, level, division) VALUES ("Divize", "DIV", 4, 1);

INSERT INTO Team (name, shortcut, league_id) VALUES ("Mortal", "MOR", 3);
INSERT INTO Team (name, shortcut, league_id) VALUES ("Inarabu", "INA", 3);