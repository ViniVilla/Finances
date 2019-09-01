CREATE TABLE users (
	id SERIAL PRIMARY KEY,
	email VARCHAR(255) NULL,
	"name" VARCHAR(255) NULL,
	"password" VARCHAR(255) NULL,
	username VARCHAR(255) NULL
);

CREATE TABLE IF NOT EXISTS account_type (
	id SERIAL PRIMARY KEY,
	name VARCHAR(255) NULL,
	user_id INTEGER NULL,
	FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE account (
	id SERIAL PRIMARY KEY,
	"name" VARCHAR(255) NULL,
	type_id INTEGER NULL,
	user_id INTEGER NULL,
	FOREIGN KEY (type_id) REFERENCES account_type(id),
	FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE transaction_category (
	id SERIAL PRIMARY KEY,
	"name" varchar(255) NULL,
	user_id INTEGER NULL,
	FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE "transaction" (
	id SERIAL PRIMARY KEY,
	"name" VARCHAR(255) NULL,
	account_id INTEGER NULL,
	category_id INTEGER NULL,
	user_id INTEGER NULL,
	amount NUMERIC(19,2) NULL,
	FOREIGN KEY (account_id) REFERENCES account(id),
	FOREIGN KEY (user_id) REFERENCES users(id),
	FOREIGN KEY (category_id) REFERENCES transaction_category(id)
);