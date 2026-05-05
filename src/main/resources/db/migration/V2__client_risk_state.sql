CREATE TABLE IF NOT EXISTS pms_client_risk_state (
    cli VARCHAR(15) PRIMARY KEY,
    last_default_date DATE,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_pms_client_risk_state_last_default_date
    ON pms_client_risk_state (last_default_date);
