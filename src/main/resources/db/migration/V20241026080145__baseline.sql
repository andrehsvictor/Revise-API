CREATE TABLE IF NOT EXISTS users (
    id serial PRIMARY KEY,
    first_name varchar(255) NOT NULL,
    last_name varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    username varchar(255) NOT NULL,
    avatar_url varchar(255),
    oauth_id varchar(255) NOT NULL,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp,

    UNIQUE (email),
    UNIQUE (username),
    UNIQUE (oauth_id)
);

CREATE TABLE IF NOT EXISTS decks (
    id serial PRIMARY KEY,
    name varchar(255) NOT NULL,
    description text,
    user_id integer NOT NULL,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp,

    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS cards (
    id serial PRIMARY KEY,
    front text NOT NULL,
    back text NOT NULL,
    deck_id integer NOT NULL,
    easiness_factor float NOT NULL DEFAULT 2.5,
    repetitions integer NOT NULL DEFAULT 0,
    interval integer NOT NULL DEFAULT 1,
    next_repetition date NOT NULL DEFAULT CURRENT_DATE,
    last_studied date,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp,

    FOREIGN KEY (deck_id) REFERENCES decks (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS reviews (
    id serial PRIMARY KEY,
    card_id integer NOT NULL,
    user_id integer NOT NULL,
    rating integer NOT NULL,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (card_id) REFERENCES cards (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);