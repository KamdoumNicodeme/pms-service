CREATE TABLE IF NOT EXISTS bkcli (
    cli VARCHAR(15) PRIMARY KEY,
    nom VARCHAR(200),
    nomrest VARCHAR(200),
    qua VARCHAR(10),
    tcli VARCHAR(4),
    age VARCHAR(5),
    ges VARCHAR(3),
    catn VARCHAR(6)
);

CREATE TABLE IF NOT EXISTS bkcom (
    ncp VARCHAR(11) NOT NULL,
    suf VARCHAR(10) DEFAULT '',
    dev VARCHAR(10) NOT NULL,
    age VARCHAR(5) NOT NULL,
    cli VARCHAR(15),
    cpro VARCHAR(10),
    sde NUMERIC(19,4),
    daut DATE,
    cha VARCHAR(10),
    PRIMARY KEY (ncp, suf, dev, age)
);

CREATE TABLE IF NOT EXISTS bkautc (
    naut BIGINT PRIMARY KEY,
    ncp VARCHAR(11) NOT NULL,
    age VARCHAR(5) NOT NULL,
    dev VARCHAR(10) NOT NULL,
    sit VARCHAR(2),
    eta VARCHAR(2),
    typ VARCHAR(2),
    debut DATE,
    fin DATE,
    maut NUMERIC(19,4)
);

CREATE TABLE IF NOT EXISTS bkdosprt (
    age VARCHAR(5) NOT NULL,
    eve VARCHAR(6) NOT NULL,
    ord VARCHAR(3) NOT NULL,
    ave BIGINT NOT NULL,
    cli VARCHAR(15),
    dev VARCHAR(10),
    eta VARCHAR(2),
    ctr VARCHAR(2),
    typ VARCHAR(3),
    mon NUMERIC(19,4),
    mimp NUMERIC(19,4),
    dimp DATE,
    nbe INTEGER,
    tech INTEGER,
    dmep DATE,
    map NUMERIC(19,4),
    cum_amo NUMERIC(19,4),
    PRIMARY KEY (age, eve, ord, ave)
);

CREATE TABLE IF NOT EXISTS bkechprt (
    age VARCHAR(5) NOT NULL,
    eve VARCHAR(6) NOT NULL,
    ord VARCHAR(3) NOT NULL,
    ave INTEGER NOT NULL,
    num INTEGER NOT NULL,
    dev VARCHAR(10),
    ctr VARCHAR(2),
    dva DATE,
    tot_ech NUMERIC(19,4),
    PRIMARY KEY (age, eve, ord, ave, num)
);

CREATE TABLE IF NOT EXISTS pms_month_end (
    monthendid BIGINT PRIMARY KEY,
    extraction_date DATE
);

CREATE TABLE IF NOT EXISTS pms_commitment (
    id BIGSERIAL PRIMARY KEY,
    monthendid BIGINT NOT NULL,
    commitmenttype VARCHAR(3),
    file_number VARCHAR(11),
    client_number VARCHAR(15),
    client_name VARCHAR(100),
    account_ref VARCHAR(11),
    bad_debt_ref VARCHAR(10),
    crr INTEGER,
    client_crr VARCHAR(3),
    product_code VARCHAR(10),
    branch_code VARCHAR(10),
    branch_name VARCHAR(100),
    accountofficercode VARCHAR(10),
    accountofficename VARCHAR(100),
    ln_principal NUMERIC(19,4),
    ln_unpaid_principal NUMERIC(19,4),
    od_balance NUMERIC(19,4),
    od_unpaid_interest NUMERIC(19,4),
    baseamount NUMERIC(19,4),
    currencycode VARCHAR(10),
    provision_rate NUMERIC(20,4),
    provision_amount NUMERIC(19,4),
    cbc VARCHAR(10),
    num_overdue_days BIGINT,
    num_pastdue_days BIGINT,
    interest_in_suspense NUMERIC(19,4),
    cha VARCHAR(10),
    currency_rate NUMERIC(19,4),
    collateral_amount NUMERIC(19,4)
);

CREATE INDEX IF NOT EXISTS idx_pms_commitment_monthendid ON pms_commitment (monthendid);
