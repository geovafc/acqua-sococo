ALTER TABLE movimentacao ADD CONSTRAINT avatar_id
FOREIGN KEY (avatar_id) REFERENCES avatares(id);