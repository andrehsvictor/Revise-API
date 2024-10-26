CREATE TABLE IF NOT EXISTS attachments (
    id serial PRIMARY KEY,
    name varchar(255) NOT NULL,
    mime_type varchar(255) NOT NULL,
    size int NOT NULL,
    card_id int NOT NULL,
    data bytea NOT NULL,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (card_id) REFERENCES cards(id) ON DELETE CASCADE,
    UNIQUE (card_id, name)
);