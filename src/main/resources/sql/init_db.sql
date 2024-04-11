CREATE TABLE IF NOT EXISTS schema_sdebras.contract (
    pk_contract_id SERIAL PRIMARY KEY,
    num_contract varchar(50) NOT NULL,
    label varchar(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS schema_sdebras.invoice (
    pk_invoice_id SERIAL PRIMARY KEY,
    num_invoice varchar(50) NOT NULL,
    fk_contract_id int NOT NULL,
    CONSTRAINT fk_contract_id_contract FOREIGN KEY (fk_contract_id) REFERENCES schema_sdebras.contract (pk_contract_id)
);