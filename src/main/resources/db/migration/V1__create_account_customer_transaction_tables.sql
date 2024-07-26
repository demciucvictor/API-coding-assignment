CREATE TABLE IF NOT EXISTS customers (
                           customer_id BIGINT PRIMARY KEY,
                           name VARCHAR(255),
                           surname VARCHAR(255),
                           balance DOUBLE
);

CREATE TABLE IF NOT EXISTS accounts (
                          account_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          initial_credit DOUBLE,
                          customer_id BIGINT,
                          CONSTRAINT fk_customer
                              FOREIGN KEY (customer_id)
                                  REFERENCES customers(customer_id)
);

CREATE TABLE IF NOT EXISTS transactions (
                              transaction_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              amount DOUBLE,
                              is_success BOOLEAN,
                              description VARCHAR(255),
                              account_id BIGINT,
                              CONSTRAINT fk_account
                                  FOREIGN KEY (account_id)
                                      REFERENCES accounts(account_id)
);
