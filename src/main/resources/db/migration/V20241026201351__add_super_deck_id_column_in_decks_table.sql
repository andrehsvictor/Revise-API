ALTER TABLE decks ADD COLUMN parent_id int;
ALTER TABLE decks ADD CONSTRAINT fk_decks_parent_id FOREIGN KEY (parent_id) REFERENCES decks(id) ON DELETE CASCADE;