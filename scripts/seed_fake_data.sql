BEGIN;

-- Month-end markers used by reporting endpoints
INSERT INTO pms_month_end (monthendid, extraction_date)
VALUES
  (202512, DATE '2025-12-31'),
  (202601, DATE '2026-01-31')
ON CONFLICT (monthendid) DO UPDATE
SET extraction_date = EXCLUDED.extraction_date;

-- 1500 fake clients
INSERT INTO bkcli (cli, nom, nomrest, qua, tcli, age, ges, catn)
SELECT
  'C' || LPAD(gs::text, 5, '0') AS cli,
  'CLIENT_' || gs,
  'CLIENT NAME ' || gs,
  CASE
    WHEN gs % 7 = 0 THEN '8'
    WHEN gs % 5 = 0 THEN '7'
    WHEN gs % 3 = 0 THEN '6'
    WHEN gs % 2 = 0 THEN '5'
    ELSE '4'
  END AS qua,
  CASE WHEN gs % 2 = 0 THEN 'ENT' ELSE 'RET' END AS tcli,
  LPAD(((gs % 999) + 1)::text, 3, '0') AS age,
  LPAD(((gs % 99) + 1)::text, 3, '0') AS ges,
  'CAT' || (gs % 10) AS catn
FROM generate_series(1, 1500) AS gs
ON CONFLICT (cli) DO NOTHING;

-- 1500 overdraft account rows
INSERT INTO bkcom (ncp, suf, dev, age, cli, cpro, sde, daut, cha)
SELECT
  LPAD(gs::text, 11, '0') AS ncp,
  '00' AS suf,
  CASE WHEN gs % 3 = 0 THEN 'USD' ELSE 'XAF' END AS dev,
  LPAD(((gs % 999) + 1)::text, 3, '0') AS age,
  'C' || LPAD(gs::text, 5, '0') AS cli,
  'PR' || LPAD((gs % 50)::text, 2, '0') AS cpro,
  CASE WHEN gs % 4 = 0 THEN -1 * (500 + gs) ELSE (200 + gs) END::numeric(19,4) AS sde,
  CURRENT_DATE - (gs % 120),
  CASE WHEN gs % 2 = 0 THEN '371' ELSE '372' END AS cha
FROM generate_series(1, 1500) AS gs
ON CONFLICT (ncp, suf, dev, age) DO NOTHING;

-- 1500 authorization rows
INSERT INTO bkautc (naut, ncp, age, dev, sit, eta, typ, debut, fin, maut)
SELECT
  gs::bigint AS naut,
  LPAD(gs::text, 11, '0') AS ncp,
  LPAD(((gs % 999) + 1)::text, 3, '0') AS age,
  CASE WHEN gs % 3 = 0 THEN 'USD' ELSE 'XAF' END AS dev,
  CASE WHEN gs % 10 = 0 THEN 'M' ELSE 'O' END AS sit,
  'VA' AS eta,
  CASE WHEN gs % 2 = 0 THEN 'N' ELSE 'O' END AS typ,
  CURRENT_DATE - (gs % 180),
  CURRENT_DATE + ((gs % 90) + 1),
  (1000 + gs * 3)::numeric(19,4) AS maut
FROM generate_series(1, 1500) AS gs
ON CONFLICT (naut) DO NOTHING;

-- 1200 loan dossier rows
INSERT INTO bkdosprt (age, eve, ord, ave, cli, dev, eta, ctr, typ, mon, mimp, dimp, nbe, tech, dmep, map, cum_amo)
SELECT
  LPAD(((gs % 999) + 1)::text, 3, '0') AS age,
  'E' || LPAD(gs::text, 5, '0') AS eve,
  '001' AS ord,
  1::bigint AS ave,
  'C' || LPAD(gs::text, 5, '0') AS cli,
  CASE WHEN gs % 3 = 0 THEN 'USD' ELSE 'XAF' END AS dev,
  CASE WHEN gs % 11 = 0 THEN 'DE' ELSE 'VA' END AS eta,
  CASE WHEN gs % 5 = 0 THEN '2' ELSE '1' END AS ctr,
  'PRT' AS typ,
  (10000 + gs * 7)::numeric(19,4) AS mon,
  (gs % 3000)::numeric(19,4) AS mimp,
  CURRENT_DATE - (gs % 60),
  12 + (gs % 24),
  24 + (gs % 24),
  CURRENT_DATE - (300 + gs % 600),
  (500 + gs)::numeric(19,4) AS map,
  (gs % 7000)::numeric(19,4) AS cum_amo
FROM generate_series(1, 1200) AS gs
ON CONFLICT (age, eve, ord, ave) DO NOTHING;

-- 2400 schedule detail rows (2 lines by dossier)
INSERT INTO bkechprt (age, eve, ord, ave, num, dev, ctr, dva, tot_ech)
SELECT
  LPAD(((gs % 999) + 1)::text, 3, '0') AS age,
  'E' || LPAD(gs::text, 5, '0') AS eve,
  '001' AS ord,
  1 AS ave,
  num,
  CASE WHEN gs % 3 = 0 THEN 'USD' ELSE 'XAF' END AS dev,
  '1' AS ctr,
  CURRENT_DATE + (num * 30),
  (200 + gs + num)::numeric(19,4) AS tot_ech
FROM generate_series(1, 1200) AS gs
CROSS JOIN generate_series(1, 2) AS num
ON CONFLICT (age, eve, ord, ave, num) DO NOTHING;

COMMIT;
